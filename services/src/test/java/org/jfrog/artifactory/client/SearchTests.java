package org.jfrog.artifactory.client;

import org.jfrog.artifactory.client.model.RepoPath;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.apache.commons.lang.StringUtils.countMatches;
import static org.testng.Assert.*;

/**
 * @author jbaruch
 * @author rnaegele
 * @since 03/08/12
 */
public class SearchTests extends ArtifactoryTestsBase {

    @Test
    public void testLimitlessQuickSearch() throws IOException {
        List<RepoPath> results = artifactory.searches().artifactsByName("junit").doSearch();
        assertEquals(results.size(), countMatches(curl("api/search/artifact?name=junit"), "{") - 1);
    }

    @Test
    public void testLatestVersionSearch() throws IOException {
        String results = artifactory.searches().artifactsLatestVersion()
                .groupId("junit")
                .artifactId("junit")
                .repositories("repo1-cache")
                .doRawSearch();
        assertEquals(results, "4.12");
    }

    @Test
    public void testQuickSearchWithWrongSingleLimit() throws IOException {
        List<RepoPath> list = artifactory.searches().artifactsByName("junit").repositories(NEW_LOCAL).doSearch();
        assertTrue(list.isEmpty());
    }

    @Test
    public void testQuickSearchWithRightSingleLimit() throws IOException {
        List<RepoPath> results = artifactory.searches().artifactsByName("junit").repositories(JCENTER_CACHE).doSearch();
        assertEquals(results.size(), countMatches(curl("api/search/artifact?name=junit&repos="+JCENTER_CACHE), "{") - 1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testQuickSearchWithoutSearchTerm() throws IOException {
        artifactory.searches().doSearch();
    }

    @Test
    public void testQuickSearchWithMultipleLimits() throws IOException {
        List<RepoPath> results =
                artifactory.searches().artifactsByName("junit").repositories(LIBS_RELEASE_LOCAL, JCENTER_CACHE)
                        .doSearch();
        assertEquals(results.size(),
                countMatches(curl("api/search/artifact?name=junit&repos="+ LIBS_RELEASE_LOCAL +","+JCENTER_CACHE), "{") - 1);
    }

    @Test
    public void testArtifactsCreatedSinceSearch() throws IOException {
        long startTime =  System.currentTimeMillis() - 86400000L;
        List<RepoPath> results = artifactory.searches().artifactsCreatedSince(startTime).doSearch();
        assertFalse(results.isEmpty());
    }

    @Test
    public void testArtifactsCreatedInDateRangeSearch() throws IOException {
        long now = System.currentTimeMillis();
        long startTime =  System.currentTimeMillis() - 86400000L;
        List<RepoPath> results = artifactory.searches().artifactsCreatedInDateRange(startTime, now).doSearch();
        assertFalse(results.isEmpty());
    }

    @Test(dependsOnGroups = "uploadBasics")
    public void testSearchByProperty() throws IOException {
        List<RepoPath> results = artifactory.searches().itemsByProperty().property("colors", "red").doSearch();
        assertEquals(results.size(), 1);
        assertTrue(results.get(0).getItemPath().contains("a/b/c.txt"));
    }

    @Test(dependsOnGroups = "uploadBasics")
    public void testSearchByPropertyWithMapNotation() throws IOException {
        Map<String, Boolean> properties = new HashMap<>();
        properties.put("released", false);
        List<RepoPath> results = artifactory.searches().itemsByProperty().properties(properties).doSearch();
        assertEquals(results.size(), 1);
        assertTrue(results.get(0).getItemPath().contains("a/b/c.txt"));
    }

    @Test(dependsOnGroups = "uploadBasics")
    public void testSearchByPropertyAndRepoFilter() throws IOException {
        List<RepoPath> results =
                artifactory.searches().itemsByProperty().property("released", false).repositories(NEW_LOCAL).doSearch();
        assertEquals(results.size(), 1);
        assertTrue(results.get(0).getItemPath().contains("a/b/c.txt"));
    }

    @Test(dependsOnGroups = "uploadBasics")
    public void testSearchByPropertyAndWrongRepoFilter() throws IOException {
        List<RepoPath> list = artifactory.searches().repositories(JCENTER).itemsByProperty().property("released",
                false).doSearch();
        assertTrue(list.isEmpty());
    }

    @Test(dependsOnGroups = "uploadBasics")
    public void testSearchByPropertyWithMulipleValue() throws IOException {
        List<RepoPath> list = artifactory.searches().itemsByProperty().property("colors", "red", "green",
                "blue").doSearch();
        assertTrue(list.isEmpty());
    }

    @Test(dependsOnGroups = "uploadBasics")
    public void testSearchByPropertyMapNotationWithMulipleValue() throws IOException {
        Map<String, List<String>> property = new HashMap<>();
        property.put("colors", asList("red", "green", "blue"));
        List<RepoPath> list = artifactory.searches().itemsByProperty().properties(property).doSearch();
        assertTrue(list.isEmpty());
    }

    @Test(dependsOnGroups = "uploadBasics")
    public void testSearchByPropertyWithoutValue() throws IOException {
        List<RepoPath> results =
                artifactory.searches().itemsByProperty().property("released").property("build", 28).doSearch();
        assertEquals(results.size(), 1);
        assertTrue(results.get(0).getItemPath().contains("a/b/c.txt"));
    }

    @Test(dependsOnGroups = "uploadBasics")
    public void testSearchByPropertyWithWildCards() throws IOException {
        List<RepoPath> results = artifactory.searches().itemsByProperty().property("colors", "r*?").doSearch();
        assertEquals(results.size(), 1);
        assertTrue(results.get(0).getItemPath().contains("a/b/c.txt"));
    }

    @Test(dependsOnGroups = "uploadBasics")
    public void testSearchByGavc() throws IOException {
        List<RepoPath> results = artifactory.searches().artifactsByGavc()
                .groupId("com.example")
                .artifactId("com.example.test")
                .version("1.0.0")
                .classifier("zip")
                .doSearch();
        assertEquals(results.size(), 1);
        assertTrue(results.get(0).getItemPath().contains("com.example.test-1.0.0-zip.jar"));
    }

    @Test(dependsOnGroups = "uploadBasics")
    public void testSearchByGavcAndRepository() throws IOException {
        List<RepoPath> results = artifactory.searches().artifactsByGavc()
                .groupId("com.example")
                .artifactId("com.example.test")
                .repositories(NEW_LOCAL)
                .doSearch();
        assertEquals(results.size(), 1);
        assertTrue(results.get(0).getItemPath().contains("com.example.test-1.0.0-zip.jar"));
    }
}
