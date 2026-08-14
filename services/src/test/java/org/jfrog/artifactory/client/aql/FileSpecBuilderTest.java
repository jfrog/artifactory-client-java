package org.jfrog.artifactory.client.aql;

import org.jfrog.filespecs.FileSpec;
import org.jfrog.filespecs.entities.FilesGroup;
import org.jfrog.filespecs.entities.InvalidFileSpecException;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.jfrog.artifactory.client.aql.AqlItem.aqlItem;
import static org.jfrog.artifactory.client.aql.AqlItem.match;
import static org.jfrog.artifactory.client.aql.AqlItem.or;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class FileSpecBuilderTest {

    // ── find-body round-trips ──────────────────────────────────────────────────

    @Test
    public void emptyBuilderProducesEmptyFindBody() throws InvalidFileSpecException {
        String aql = aqlFromSpec(new FileSpecBuilder().buildFileSpec());
        assertEquals(aql, "items.find({})" + defaultInclude());
    }

    @Test
    public void itemLiteralEquality() throws InvalidFileSpecException {
        String aql = aqlFromSpec(new FileSpecBuilder()
                .item("type", "file")
                .buildFileSpec());
        assertTrue(aql.startsWith("items.find({\"type\":\"file\"})"), aql);
    }

    @Test
    public void matchWildcard() throws InvalidFileSpecException {
        String aql = aqlFromSpec(new FileSpecBuilder()
                .match("repo", "pnc-devel-*")
                .buildFileSpec());
        assertTrue(aql.contains("\"repo\":{\"$match\":\"pnc-devel-*\"}"), aql);
    }

    @Test
    public void notMatch() throws InvalidFileSpecException {
        String aql = aqlFromSpec(new FileSpecBuilder()
                .notMatch("repo", "libs-*")
                .buildFileSpec());
        assertTrue(aql.contains("\"repo\":{\"$nmatch\":\"libs-*\"}"), aql);
    }

    @Test
    public void eqOperator() throws InvalidFileSpecException {
        String aql = aqlFromSpec(new FileSpecBuilder()
                .eq("property.key", "pnc.build-BQBPZZFPTRYAA")
                .buildFileSpec());
        assertTrue(aql.contains("\"property.key\":{\"$eq\":\"pnc.build-BQBPZZFPTRYAA\"}"), aql);
    }

    @Test
    public void neOperator() throws InvalidFileSpecException {
        String aql = aqlFromSpec(new FileSpecBuilder()
                .ne("property.key", "excluded-key")
                .buildFileSpec());
        assertTrue(aql.contains("\"property.key\":{\"$ne\":\"excluded-key\"}"), aql);
    }

    @Test
    public void motivatingExample() throws InvalidFileSpecException {
        // Equivalent to the JSON FileSpec in the design doc
        String aql = aqlFromSpec(new FileSpecBuilder()
                .item("type", "file")
                .match("repo", "pnc-devel-*")
                .eq("property.key", "pnc.build-BQBPZZFPTRYAA")
                .limit(50000)
                .buildFileSpec());

        assertTrue(aql.contains("\"type\":\"file\""), aql);
        assertTrue(aql.contains("\"repo\":{\"$match\":\"pnc-devel-*\"}"), aql);
        assertTrue(aql.contains("\"property.key\":{\"$eq\":\"pnc.build-BQBPZZFPTRYAA\"}"), aql);
        assertTrue(aql.endsWith(".limit(50000)"), aql);
    }

    // ── composite predicates ───────────────────────────────────────────────────

    @Test
    public void andPredicate() throws InvalidFileSpecException {
        String aql = aqlFromSpec(new FileSpecBuilder()
                .and(aqlItem("repo", "myrepo1"), aqlItem("repo", "myrepo2"))
                .buildFileSpec());
        assertTrue(aql.contains("\"$and\":[{\"repo\":\"myrepo1\"},{\"repo\":\"myrepo2\"}]"), aql);
    }

    @Test
    public void andPredicateCollection() throws InvalidFileSpecException {
        String aql = aqlFromSpec(new FileSpecBuilder()
                .and(Arrays.asList(aqlItem("repo", "myrepo1"), aqlItem("repo", "myrepo2")))
                .buildFileSpec());
        assertTrue(aql.contains("\"$and\":[{\"repo\":\"myrepo1\"},{\"repo\":\"myrepo2\"}]"), aql);
    }

    @Test
    public void orPredicate() throws InvalidFileSpecException {
        String aql = aqlFromSpec(new FileSpecBuilder()
                .or(aqlItem("repo", "myrepo1"), aqlItem("repo", "myrepo2"))
                .buildFileSpec());
        assertTrue(aql.contains("\"$or\":[{\"repo\":\"myrepo1\"},{\"repo\":\"myrepo2\"}]"), aql);
    }

    @Test
    public void orPredicateCollection() throws InvalidFileSpecException {
        String aql = aqlFromSpec(new FileSpecBuilder()
                .or(Arrays.asList(aqlItem("repo", "r1"), aqlItem("repo", "r2")))
                .buildFileSpec());
        assertTrue(aql.contains("\"$or\":[{\"repo\":\"r1\"},{\"repo\":\"r2\"}]"), aql);
    }

    @Test
    public void nestedAndOr() throws InvalidFileSpecException {
        String aql = aqlFromSpec(new FileSpecBuilder()
                .item("type", "file")
                .and(
                    or(aqlItem("repo", "libs-release"), aqlItem("repo", "libs-snapshot")),
                    match("name", "*.jar")
                )
                .buildFileSpec());
        assertTrue(aql.contains("\"type\":\"file\""), aql);
        assertTrue(aql.contains("\"$and\":"), aql);
        assertTrue(aql.contains("\"$or\":"), aql);
        assertTrue(aql.contains("\"name\":{\"$match\":\"*.jar\"}"), aql);
    }

    // ── suffix / pagination fields ─────────────────────────────────────────────

    @Test
    public void limit() throws InvalidFileSpecException {
        String aql = aqlFromSpec(new FileSpecBuilder().limit(100).buildFileSpec());
        assertTrue(aql.endsWith(".limit(100)"), aql);
    }

    @Test
    public void offset() throws InvalidFileSpecException {
        String aql = aqlFromSpec(new FileSpecBuilder().offset(20).buildFileSpec());
        assertTrue(aql.contains(".offset(20)"), aql);
    }

    @Test
    public void sortAsc() throws InvalidFileSpecException {
        String aql = aqlFromSpec(new FileSpecBuilder().sortAsc("name").buildFileSpec());
        assertTrue(aql.contains(".sort({\"$asc\":[\"name\"]})"), aql);
    }

    @Test
    public void sortDesc() throws InvalidFileSpecException {
        String aql = aqlFromSpec(new FileSpecBuilder().sortDesc("name", "repo").buildFileSpec());
        assertTrue(aql.contains(".sort({\"$desc\":[\"name\",\"repo\"]})"), aql);
    }

    @Test
    public void limitAndOffsetOrdering() throws InvalidFileSpecException {
        // AqlConverter emits: .sort(…).offset(…).limit(…)
        String aql = aqlFromSpec(new FileSpecBuilder()
                .sortAsc("name")
                .offset(10)
                .limit(50)
                .buildFileSpec());
        int sortIdx   = aql.indexOf(".sort(");
        int offsetIdx = aql.indexOf(".offset(");
        int limitIdx  = aql.indexOf(".limit(");
        assertTrue(sortIdx < offsetIdx && offsetIdx < limitIdx,
                "Expected sort < offset < limit in: " + aql);
    }

    // ── FileSpec structure ─────────────────────────────────────────────────────

    @Test
    public void buildFileSpecContainsOneGroup() {
        FileSpec spec = new FileSpecBuilder().item("type", "file").buildFileSpec();
        assertNotNull(spec.getFiles());
        assertEquals(spec.getFiles().size(), 1);
    }

    @Test
    public void groupSpecTypeIsAql() {
        FilesGroup group = new FileSpecBuilder().item("type", "file").buildGroup();
        assertEquals(group.getSpecType(), FilesGroup.SpecType.AQL);
    }

    @Test
    public void addToFileSpecAccumulatesGroups() throws InvalidFileSpecException {
        FileSpec spec = new FileSpec();
        new FileSpecBuilder().match("repo", "libs-release").addToFileSpec(spec);
        new FileSpecBuilder().match("repo", "libs-snapshot").addToFileSpec(spec);

        assertEquals(spec.getFiles().size(), 2);

        List<String> aqls = spec.toAql();
        assertEquals(aqls.size(), 2);
        assertTrue(aqls.get(0).contains("\"repo\":{\"$match\":\"libs-release\"}"), aqls.get(0));
        assertTrue(aqls.get(1).contains("\"repo\":{\"$match\":\"libs-snapshot\"}"), aqls.get(1));
    }

    @Test
    public void multiGroupMotivatingExample() throws InvalidFileSpecException {
        String[] buildIds = {"pnc.build-AAA", "pnc.build-BBB"};
        FileSpec spec = new FileSpec();
        for (String buildId : buildIds) {
            new FileSpecBuilder()
                    .item("type", "file")
                    .match("repo", "pnc-devel-*")
                    .eq("property.key", buildId)
                    .limit(50000)
                    .addToFileSpec(spec);
        }

        List<String> aqls = spec.toAql();
        assertEquals(aqls.size(), 2);
        assertTrue(aqls.get(0).contains("pnc.build-AAA"), aqls.get(0));
        assertTrue(aqls.get(1).contains("pnc.build-BBB"), aqls.get(1));
        assertTrue(aqls.get(0).endsWith(".limit(50000)"), aqls.get(0));
    }

    // ── include field tests ────────────────────────────────────────────────────

    @Test
    public void includeReplacesDefaultInclude() throws InvalidFileSpecException {
        String aql = aqlFromSpec(new FileSpecBuilder()
                .item("type", "file")
                .include("name", "repo", "path")
                .buildFileSpec());
        assertTrue(aql.contains(".include(\"name\",\"repo\",\"path\")"), aql);
        // Must NOT also contain the library's default include (which adds "actual_md5" etc.)
        // — only one .include() clause should be present.
        assertEquals(countOccurrences(aql, ".include("), 1, "Expected exactly one .include() in: " + aql);
    }

    @Test
    public void includeWithCustomPropertyField() throws InvalidFileSpecException {
        String aql = aqlFromSpec(new FileSpecBuilder()
                .item("type", "file")
                .include("name", "repo", "path", "size", "actual_sha1", "actual_md5",
                         "sha256", "@jf.origin.remote.path")
                .buildFileSpec());
        assertTrue(aql.contains("\"@jf.origin.remote.path\""), aql);
        assertTrue(aql.contains("\"sha256\""), aql);
        assertEquals(countOccurrences(aql, ".include("), 1, aql);
    }

    @Test
    public void includeWithLimitPreservesLimitSuffix() throws InvalidFileSpecException {
        String aql = aqlFromSpec(new FileSpecBuilder()
                .item("type", "file")
                .match("repo", "pnc-devel-*")
                .eq("property.key", "pnc.build-BQBPZZFPTRYAA")
                .include("name", "repo", "path", "size", "actual_sha1", "actual_md5",
                         "sha256", "@jf.origin.remote.path")
                .limit(50000)
                .buildFileSpec());
        // include comes before limit
        int includeIdx = aql.indexOf(".include(");
        int limitIdx   = aql.indexOf(".limit(");
        assertTrue(includeIdx < limitIdx, "Expected .include() before .limit() in: " + aql);
        assertTrue(aql.endsWith(".limit(50000)"), aql);
        assertEquals(countOccurrences(aql, ".include("), 1, aql);
    }

    @Test
    public void includeWithSortAndOffsetOrdering() throws InvalidFileSpecException {
        String aql = aqlFromSpec(new FileSpecBuilder()
                .item("type", "file")
                .include("name", "repo")
                .sortAsc("name")
                .offset(10)
                .limit(50)
                .buildFileSpec());
        // AQL order: .include(…).sort(…).offset(…).limit(…)
        int includeIdx = aql.indexOf(".include(");
        int sortIdx    = aql.indexOf(".sort(");
        int offsetIdx  = aql.indexOf(".offset(");
        int limitIdx   = aql.indexOf(".limit(");
        assertTrue(includeIdx < sortIdx,   "include before sort in: " + aql);
        assertTrue(sortIdx   < offsetIdx,  "sort before offset in: " + aql);
        assertTrue(offsetIdx < limitIdx,   "offset before limit in: " + aql);
        assertEquals(countOccurrences(aql, ".include("), 1, aql);
    }

    @Test
    public void noIncludeCallUsesLibraryDefault() throws InvalidFileSpecException {
        // Sanity: omitting .include() still produces the library's default .include()
        String aql = aqlFromSpec(new FileSpecBuilder()
                .item("type", "file")
                .buildFileSpec());
        assertTrue(aql.contains(".include("), aql);
        // Library default always contains "actual_md5"
        assertTrue(aql.contains("\"actual_md5\""), aql);
    }

    @Test
    public void buildFileSpecReturnsIncludeAwareFileSpecWhenIncludeSet() {
        FileSpec spec = new FileSpecBuilder()
                .item("type", "file")
                .include("name", "repo")
                .buildFileSpec();
        assertTrue(spec instanceof IncludeAwareFileSpec,
                "Expected IncludeAwareFileSpec when include() is called");
    }

    @Test
    public void buildFileSpecReturnsPlainFileSpecWhenNoInclude() {
        FileSpec spec = new FileSpecBuilder()
                .item("type", "file")
                .buildFileSpec();
        assertFalse(spec instanceof IncludeAwareFileSpec,
                "Expected plain FileSpec when include() is not called");
    }

    @Test
    public void addToFileSpecWithIncludeAccumulatesGroups() throws InvalidFileSpecException {
        FileSpec spec = new FileSpec();
        spec = new FileSpecBuilder()
                .match("repo", "libs-release")
                .include("name", "repo", "path")
                .addToFileSpec(spec);
        new FileSpecBuilder()
                .match("repo", "libs-snapshot")
                .include("name", "repo", "sha256")
                .addToFileSpec(spec);

        assertEquals(spec.getFiles().size(), 2);
        List<String> aqls = spec.toAql();
        assertEquals(aqls.size(), 2);
        assertTrue(aqls.get(0).contains("libs-release"), aqls.get(0));
        assertTrue(aqls.get(0).contains("\"path\""), aqls.get(0));
        assertEquals(countOccurrences(aqls.get(0), ".include("), 1, aqls.get(0));
        assertTrue(aqls.get(1).contains("libs-snapshot"), aqls.get(1));
        assertTrue(aqls.get(1).contains("\"sha256\""), aqls.get(1));
        assertEquals(countOccurrences(aqls.get(1), ".include("), 1, aqls.get(1));
    }

    @Test
    public void motivatingExampleWithInclude() throws InvalidFileSpecException {
        // The exact example from the user request / javadoc
        String aql = aqlFromSpec(new FileSpecBuilder()
                .item("type", "file")
                .match("repo", "pnc-devel-*")
                .eq("property.key", "pnc.build-BQBPZZFPTRYAA")
                .include("name", "repo", "path", "size", "actual_sha1", "actual_md5",
                         "sha256", "@jf.origin.remote.path")
                .limit(50000)
                .buildFileSpec());

        assertTrue(aql.contains("\"type\":\"file\""), aql);
        assertTrue(aql.contains("\"repo\":{\"$match\":\"pnc-devel-*\"}"), aql);
        assertTrue(aql.contains("\"property.key\":{\"$eq\":\"pnc.build-BQBPZZFPTRYAA\"}"), aql);
        assertTrue(aql.contains(".include(\"name\",\"repo\",\"path\",\"size\","
                + "\"actual_sha1\",\"actual_md5\",\"sha256\",\"@jf.origin.remote.path\")"), aql);
        assertTrue(aql.endsWith(".limit(50000)"), aql);
        assertEquals(countOccurrences(aql, ".include("), 1, aql);
    }

    // ── helpers ────────────────────────────────────────────────────────────────

    /** Converts a single-group FileSpec to the AQL string that would be POSTed. */
    private static String aqlFromSpec(FileSpec spec) throws InvalidFileSpecException {
        List<String> aqls = spec.toAql();
        assertEquals(aqls.size(), 1);
        return aqls.get(0);
    }

    /**
     * The default .include(…) that AqlBuildingUtils always appends when there is no
     * sort/suffix (sortBy is empty and suffix is blank → property is included).
     */
    private static String defaultInclude() {
        return ".include(\"name\",\"repo\",\"path\",\"actual_md5\",\"actual_sha1\","
                + "\"size\",\"type\",\"modified\",\"created\",\"property\")";
    }

    private static int countOccurrences(String text, String sub) {
        int count = 0;
        int idx = 0;
        while ((idx = text.indexOf(sub, idx)) != -1) {
            count++;
            idx += sub.length();
        }
        return count;
    }
}
