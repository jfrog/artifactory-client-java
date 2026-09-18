package org.jfrog.artifactory.client.aql;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.jfrog.filespecs.FileSpec;
import org.jfrog.filespecs.entities.Aql;
import org.jfrog.filespecs.entities.FilesGroup;

import java.util.Arrays;
import java.util.Collection;

/**
 * Fluent builder that constructs a {@link FileSpec} directly from AQL predicate
 * expressions for use with {@link org.jfrog.artifactory.client.Searches#artifactsByFileSpec}.
 *
 * <p>Each builder instance represents a single {@code FilesGroup} (one entry in the
 * {@code "files"} array).  Call {@link #buildFileSpec()} to wrap it in a new
 * {@link FileSpec}, or {@link #addToFileSpec(FileSpec)} to append it to an existing one
 * (multi-group / multi-query specs).
 *
 * <p>Predicate methods ({@link #item}, {@link #match}, {@link #eq}, …) populate the
 * JSON body passed to {@code items.find(…)}.  Suffix methods ({@link #limit},
 * {@link #offset}, {@link #sortAsc}, {@link #sortDesc}) are stored in the matching
 * {@link FilesGroup} fields and assembled by
 * {@code AqlConverter.convertFilesGroupToAql} at search time — they are <em>not</em>
 * baked into the find-body string.
 *
 * <p>Use {@link #include(String...)} to control which fields are returned by Artifactory.
 * When set, the library's default {@code .include(…)} is replaced with the caller-supplied
 * list.  Custom property fields (prefixed with {@code @}) are supported.
 *
 * <p>Example — produces the equivalent of:
 * <pre>{@code
 * {
 *   "files": [{
 *     "aql": {
 *       "items.find": {
 *         "type": "file",
 *         "repo": {"$match": "pnc-devel-*"},
 *         "property.key": {"$eq": "pnc.build-BQBPZZFPTRYAA"}
 *       }
 *     },
 *     "include": ["name","repo","path","size","actual_sha1","actual_md5","sha256","@jf.origin.remote.path"],
 *     "limit": 50000
 *   }]
 * }
 * }</pre>
 *
 * <pre>{@code
 * FileSpec spec = new FileSpecBuilder()
 *     .item("type", "file")
 *     .match("repo", "pnc-devel-*")
 *     .eq("property.key", "pnc.build-BQBPZZFPTRYAA")
 *     .include("name", "repo", "path", "size", "actual_sha1", "actual_md5", "sha256",
 *              "@jf.origin.remote.path")
 *     .limit(50000)
 *     .buildFileSpec();
 * }</pre>
 *
 * @see org.jfrog.artifactory.client.Searches#artifactsByFileSpec(FileSpec)
 * @see AqlQueryBuilder for building raw AQL strings for the direct POST path
 */
public class FileSpecBuilder {

    // ── find-body ─────────────────────────────────────────────────────────────
    private final AqlRootElement root = new AqlRootElement();

    // ── FilesGroup suffix fields ───────────────────────────────────────────────
    private String[] sortBy;
    private String   sortOrder;
    private Integer  limit;
    private Integer  offset;

    // ── explicit include fields (null = use library default) ──────────────────
    private String[] includeFields;

    // ── find-body predicate methods ───────────────────────────────────────────

    /**
     * Adds a literal equality field: {@code "key": value}.
     * Use {@link #eq(String, String)} when you need the explicit {@code {"$eq":"…"}} form
     * (e.g. for {@code property.key} comparisons).
     */
    public FileSpecBuilder item(String key, Object value) {
        root.putAll(AqlItem.aqlItem(key, value).value());
        return this;
    }

    /** {@code "key": {"$match": "pattern"}} — wildcard glob match. */
    public FileSpecBuilder match(String key, String pattern) {
        root.putAll(AqlItem.match(key, pattern).value());
        return this;
    }

    /** {@code "key": {"$nmatch": "pattern"}} — negated wildcard glob match. */
    public FileSpecBuilder notMatch(String key, String pattern) {
        root.putAll(AqlItem.notMatch(key, pattern).value());
        return this;
    }

    /**
     * {@code "key": {"$eq": "value"}} — explicit AQL equality operator.
     * Distinct from the bare-literal form emitted by {@link #item(String, Object)};
     * required for {@code property.key} / {@code property.value} predicates.
     */
    public FileSpecBuilder eq(String key, String value) {
        root.putAll(AqlItem.aqlItem(key, AqlItem.aqlItem("$eq", value)).value());
        return this;
    }

    /** {@code "key": {"$ne": "value"}} — AQL not-equal operator. */
    public FileSpecBuilder ne(String key, String value) {
        root.putAll(AqlItem.aqlItem(key, AqlItem.aqlItem("$ne", value)).value());
        return this;
    }

    /**
     * {@code "$and": [{…}, …]} — wraps multiple {@link AqlItem} conditions in a
     * logical AND.  Use the {@link AqlItem} factory methods to build the items.
     */
    public FileSpecBuilder and(AqlItem... items) {
        if (items.length > 0) {
            root.putAll(AqlItem.and((Object[]) items).value());
        }
        return this;
    }

    /** Convenience overload accepting a {@link Collection}. */
    public FileSpecBuilder and(Collection<AqlItem> items) {
        return and(items.toArray(new AqlItem[0]));
    }

    /**
     * {@code "$or": [{…}, …]} — wraps multiple {@link AqlItem} conditions in a
     * logical OR.
     */
    public FileSpecBuilder or(AqlItem... items) {
        if (items.length > 0) {
            root.putAll(AqlItem.or((Object[]) items).value());
        }
        return this;
    }

    /** Convenience overload accepting a {@link Collection}. */
    public FileSpecBuilder or(Collection<AqlItem> items) {
        return or(items.toArray(new AqlItem[0]));
    }

    // ── include method ────────────────────────────────────────────────────────

    /**
     * Overrides the default {@code .include(…)} clause that Artifactory appends to every
     * AQL query.  By default the library includes a fixed set of item fields; calling this
     * method replaces that set with exactly the fields you specify.
     *
     * <p>Standard item fields: {@code name}, {@code repo}, {@code path}, {@code size},
     * {@code actual_sha1}, {@code actual_md5}, {@code sha256}, {@code type},
     * {@code modified}, {@code created}.
     * Custom property fields use the {@code @} prefix, e.g. {@code "@jf.origin.remote.path"}.
     *
     * <p>When this method is called, {@link #buildFileSpec()} and {@link #addToFileSpec(FileSpec)}
     * return an {@link IncludeAwareFileSpec} whose {@code toAql()} bypasses the library's
     * hardcoded include and injects the caller-supplied fields instead.
     */
    public FileSpecBuilder include(String... fields) {
        this.includeFields = Arrays.copyOf(fields, fields.length);
        return this;
    }

    // ── suffix / pagination methods ───────────────────────────────────────────

    /**
     * Sort results ascending by the given fields.
     * Stored in {@link FilesGroup#setSortBy(String[])} and
     * {@link FilesGroup#setSortOrder(String)}; assembled by {@code AqlBuildingUtils}
     * at search time.
     */
    public FileSpecBuilder sortAsc(String... fields) {
        this.sortBy    = fields;
        this.sortOrder = "asc";
        return this;
    }

    /** Sort results descending by the given fields. */
    public FileSpecBuilder sortDesc(String... fields) {
        this.sortBy    = fields;
        this.sortOrder = "desc";
        return this;
    }

    /** Maximum number of items to return. Stored in {@link FilesGroup#setLimit(String)}. */
    public FileSpecBuilder limit(int limit) {
        this.limit = limit;
        return this;
    }

    /** Number of items to skip. Stored in {@link FilesGroup#setOffset(String)}. */
    public FileSpecBuilder offset(int offset) {
        this.offset = offset;
        return this;
    }

    // ── build methods ─────────────────────────────────────────────────────────

    /**
     * Builds the {@link FilesGroup} represented by this builder.
     * The group's spec-type is always {@link FilesGroup.SpecType#AQL}.
     */
    public FilesGroup buildGroup() {
        Aql aql = new Aql();
        aql.setFind(serializeRoot());

        FilesGroup group = new FilesGroup().setAql(aql);
        if (sortBy != null)    { group.setSortBy(Arrays.copyOf(sortBy, sortBy.length)); }
        if (sortOrder != null) { group.setSortOrder(sortOrder); }
        if (limit != null)     { group.setLimit(String.valueOf(limit)); }
        if (offset != null)    { group.setOffset(String.valueOf(offset)); }
        return group;
    }

    /**
     * Wraps the built {@link FilesGroup} in a new single-group {@link FileSpec}.
     *
     * <p>If {@link #include(String...)} was called, returns an {@link IncludeAwareFileSpec}
     * whose {@code toAql()} replaces the library's default {@code .include(…)} with the
     * caller-supplied field list.  Otherwise returns a plain {@link FileSpec}.
     */
    public FileSpec buildFileSpec() {
        FilesGroup group = buildGroup();
        if (includeFields != null) {
            IncludeAwareFileSpec spec = new IncludeAwareFileSpec();
            spec.addGroup(group, includeFields);
            return spec;
        }
        FileSpec spec = new FileSpec();
        spec.addFilesGroup(group);
        return spec;
    }

    /**
     * Appends the built {@link FilesGroup} to an existing {@link FileSpec} and
     * returns that same spec.  Use this to accumulate multiple groups (one POST
     * per group is issued by
     * {@link org.jfrog.artifactory.client.impl.SearchesImpl#artifactsByFileSpec}).
     *
     * <p>If {@link #include(String...)} was called and {@code spec} is an
     * {@link IncludeAwareFileSpec}, the include override is registered for this group.
     * If {@code spec} is a plain {@link FileSpec} and an include override is set, it is
     * promoted to an {@link IncludeAwareFileSpec} first.
     *
     * <pre>{@code
     * FileSpec spec = new FileSpec();
     * for (String buildId : buildIds) {
     *     new FileSpecBuilder()
     *         .item("type", "file")
     *         .match("repo", "pnc-devel-*")
     *         .eq("property.key", buildId)
     *         .include("name", "repo", "path", "size", "actual_sha1")
     *         .limit(50000)
     *         .addToFileSpec(spec);
     * }
     * List<AqlItem> results = artifactory.searches().artifactsByFileSpec(spec);
     * }</pre>
     */
    public FileSpec addToFileSpec(FileSpec spec) {
        FilesGroup group = buildGroup();
        if (includeFields != null) {
            if (spec instanceof IncludeAwareFileSpec) {
                ((IncludeAwareFileSpec) spec).addGroup(group, includeFields);
            } else {
                // Promote the existing plain FileSpec into an IncludeAwareFileSpec so that
                // previously-added groups (no override) still use the library default.
                IncludeAwareFileSpec promoted = new IncludeAwareFileSpec(spec);
                promoted.addGroup(group, includeFields);
                // Callers hold the original reference; we can't reassign it here.
                // Return the promoted instance so the caller can update their reference.
                return promoted;
            }
        } else {
            spec.addFilesGroup(group);
        }
        return spec;
    }

    // ── internals ─────────────────────────────────────────────────────────────

    /**
     * Plain mapper for AQL find-body structures — plain Map values only, no mix-ins needed.
     * Must not share {@code Util.CONFIGURED_MAPPER} which carries Repository mix-ins that are
     * inappropriate for AQL serialisation.
     */
    private static final ObjectMapper PLAIN_MAPPER = new ObjectMapper();

    private String serializeRoot() {
        try {
            return PLAIN_MAPPER.writeValueAsString(root);
        } catch (JsonProcessingException e) {
            throw new AqlBuilderException("Error serializing AQL find-body to JSON: ", e);
        }
    }
}
