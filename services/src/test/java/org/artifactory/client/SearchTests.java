package org.artifactory.client;

import org.artifactory.client.model.RepoPath;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.apache.commons.lang.StringUtils.countMatches;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * @author jbaruch
 * @since 03/08/12
 */
public class SearchTests extends ArtifactoryTestsBase {

    @Test
    public void testLimitlessQuickSearch() throws IOException {
        List<RepoPath> results = artifactory.searches().artifactsByName("junit").doSearch();
        assertEquals(results.size(), countMatches(curl("api/search/artifact?name=junit"), "{")-1);
    }

    @Test
    public void testQuickSearchWithWrongSingleLimit() throws IOException {
        List<RepoPath> list = artifactory.searches().artifactsByName("junit").repositories(NEW_LOCAL).doSearch();
        assertTrue(list.isEmpty());
    }

    @Test
    public void testQuickSearchWithRightSingleLimit() throws IOException {
        List<RepoPath> results = artifactory.searches().artifactsByName("junit").repositories(REPO1_CACHE).doSearch();
        assertEquals(results.size(), countMatches(curl("api/search/artifact?name=junit&repos=repo1-cache"), "{") - 1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testQuickSearchWithoutSearchTerm() throws IOException {
        artifactory.searches().doSearch();
    }

    @Test
    public void testQuickSearchWithMultipleLimits() throws IOException {
        List<RepoPath> results =
                artifactory.searches().artifactsByName("junit").repositories(LIBS_RELEASES_LOCAL, REPO1_CACHE)
                        .doSearch();
        assertEquals(results.size(), countMatches(curl("api/search/artifact?name=junit&repos=libs-releases-local,repo1-cache"), "{") - 1);
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
        List<RepoPath> list = artifactory.searches().repositories(REPO1).itemsByProperty().property("released", false).doSearch();
        assertTrue(list.isEmpty());
    }

    @Test(dependsOnGroups = "uploadBasics")
    public void testSearchByPropertyWithMulipleValue() throws IOException {
        List<RepoPath> list = artifactory.searches().itemsByProperty().property("colors", "red", "green", "blue").doSearch();
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
}
