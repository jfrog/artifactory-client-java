package org.jfrog.artifactory.client.aql;

import org.jfrog.filespecs.FileSpec;
import org.jfrog.filespecs.aql.AqlConverter;
import org.jfrog.filespecs.entities.FilesGroup;
import org.jfrog.filespecs.entities.InvalidFileSpecException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * A {@link FileSpec} subclass that allows individual {@link FilesGroup} entries to carry
 * an explicit {@code .include(…)} field list, overriding the fixed set that
 * {@code AqlBuildingUtils} always appends.
 *
 * <p>Groups registered via {@link #addGroup(FilesGroup, String[])} have their AQL string
 * built locally (bypassing {@code AqlConverter}) so that the caller-supplied include list
 * is emitted at the correct position:
 * <pre>
 *   items.find({…}).include("name","repo",…).sort(…).offset(…).limit(…)
 * </pre>
 *
 * <p>Groups added through the inherited {@link FileSpec#addFilesGroup(FilesGroup)} path
 * (no override) are converted by the standard {@code AqlConverter}, preserving existing
 * behaviour.
 *
 * <p>Instances are created by {@link FileSpecBuilder#buildFileSpec()} and
 * {@link FileSpecBuilder#addToFileSpec(FileSpec)} when {@link FileSpecBuilder#include}
 * has been called.
 */
class IncludeAwareFileSpec extends FileSpec {

    /**
     * Maps each group that carries an explicit include override to its field list.
     * Identity semantics are used so that two equal-valued {@link FilesGroup} objects
     * can coexist without colliding.
     */
    private final Map<FilesGroup, String[]> includeOverrides = new IdentityHashMap<>();

    /** Creates an empty spec. */
    IncludeAwareFileSpec() {
        super();
    }

    /**
     * Creates an {@link IncludeAwareFileSpec} pre-populated with all groups from an
     * existing {@link FileSpec}.  Those groups carry no include override and will
     * continue to use the library default.
     */
    IncludeAwareFileSpec(FileSpec existing) {
        super();
        if (existing.getFiles() != null) {
            for (FilesGroup g : existing.getFiles()) {
                addFilesGroup(g);
            }
        }
    }

    /**
     * Registers {@code group} with an explicit include override and adds it to the
     * group list.
     */
    void addGroup(FilesGroup group, String[] fields) {
        addFilesGroup(group);
        includeOverrides.put(group, Arrays.copyOf(fields, fields.length));
    }

    /**
     * Converts each group to its AQL string.
     *
     * <ul>
     *   <li>Groups with an include override are converted locally so the caller-supplied
     *       fields replace the library's hardcoded {@code .include(…)}.</li>
     *   <li>All other groups fall through to {@code AqlConverter}, preserving the
     *       library's default behaviour.</li>
     * </ul>
     */
    @Override
    public List<String> toAql() throws InvalidFileSpecException {
        List<String> aqls = new ArrayList<>();
        for (FilesGroup group : getFiles()) {
            String[] override = includeOverrides.get(group);
            if (override != null) {
                aqls.add(buildAql(group, override));
            } else {
                aqls.add(AqlConverter.convertFilesGroupToAql(group));
            }
        }
        return aqls;
    }

    // ── internals ─────────────────────────────────────────────────────────────

    /**
     * Assembles the AQL string for a group that carries an explicit include override.
     *
     * <p>The order of suffixes follows the AQL specification:
     * {@code .include(…).sort(…).offset(…).limit(…)}.
     */
    private static String buildAql(FilesGroup group, String[] includeFields) {
        StringBuilder sb = new StringBuilder();
        sb.append("items.find(").append(group.getAql()).append(")");
        sb.append(buildInclude(includeFields));
        sb.append(buildSort(group));
        if (isNotBlank(group.getOffset())) {
            sb.append(".offset(").append(group.getOffset()).append(")");
        }
        if (isNotBlank(group.getLimit())) {
            sb.append(".limit(").append(group.getLimit()).append(")");
        }
        return sb.toString();
    }

    private static String buildInclude(String[] fields) {
        StringJoiner joiner = new StringJoiner(",");
        for (String f : fields) {
            joiner.add("\"" + f + "\"");
        }
        return ".include(" + joiner + ")";
    }

    private static String buildSort(FilesGroup group) {
        String[] sortBy = group.getSortBy();
        if (sortBy == null || sortBy.length == 0) {
            return "";
        }
        String order = (group.getSortOrder() != null && !group.getSortOrder().isEmpty())
                ? group.getSortOrder() : "asc";
        StringJoiner joiner = new StringJoiner(",");
        for (String f : sortBy) {
            joiner.add("\"" + f + "\"");
        }
        return ".sort({\"$" + order + "\":[" + joiner + "]})";
    }

    private static boolean isNotBlank(String s) {
        return s != null && !s.trim().isEmpty();
    }
}
