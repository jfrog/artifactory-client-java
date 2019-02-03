package org.jfrog.artifactory.client;

import org.jfrog.artifactory.client.model.File;
import org.jfrog.artifactory.client.model.RepoPath;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
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
    private String colorsProp = "colors-";
    private String buildProp = "build-";
    private String releasedProp = "released-";
    private String artifactId = "com.example.searchtests-";

    @BeforeClass
    public void setup() {
        long now = System.currentTimeMillis();
        colorsProp += now;
        buildProp += now;
        releasedProp += now;
        artifactId += now;

        InputStream inputStream = this.getClass().getResourceAsStream("/sample.zip");
        assertNotNull(inputStream);
        final String targetPath = "com/example/" + artifactId + "/1.0.0/" + artifactId + "-1.0.0-zip.jar";
        File deployed = artifactory.repository(localRepository.getKey()).upload(targetPath, inputStream)
                .withProperty(colorsProp, "red", "green", "blue")
                .withProperty(buildProp, "28")
                .withProperty(releasedProp, true)
                .doUpload();
        assertNotNull(deployed);
    }

    @Test
    public void testLimitlessQuickSearch() throws IOException {
        List<RepoPath> results = artifactory.searches().artifactsByName("junit").doSearch();
        assertEquals(results.size(), countMatches(curl("api/search/artifact?name=junit"), "{") - 1);
    }

    @Test
    public void testLatestVersionSearch() throws IOException {
        String results = artifactory.searches().artifactsLatestVersion()
                .groupId("org.jfrog.maven.annomojo")
                .artifactId("maven-plugin-anno")
                .repositories(getJCenterRepoName())
                .doRawSearch();
        assertEquals(results, "1.4.1");
    }

    @Test
    public void testQuickSearchWithWrongSingleLimit() throws IOException {
        List<RepoPath> list = artifactory.searches().artifactsByName("junit").repositories(localRepository.getKey()).doSearch();
        assertTrue(list.isEmpty());
    }

    @Test
    public void testQuickSearchWithRightSingleLimit() throws IOException {
        List<RepoPath> results = artifactory.searches().artifactsByName("junit").repositories(getJcenterCacheName()).doSearch();
        assertEquals(results.size(), countMatches(curl("api/search/artifact?name=junit&repos=" + getJcenterCacheName()), "{") - 1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testQuickSearchWithoutSearchTerm() throws IOException {
        artifactory.searches().doSearch();
    }

    @Test
    public void testQuickSearchWithMultipleLimits() throws IOException {
        List<RepoPath> results =
                artifactory.searches().artifactsByName("junit").repositories(localRepository.getKey(), getJcenterCacheName())
                        .doSearch();
        assertEquals(results.size(),
                countMatches(curl("api/search/artifact?name=junit&repos=" + localRepository.getKey() + "," + getJcenterCacheName()), "{") - 1);
    }

    @Test
    public void testSearchByProperty() throws IOException {
        List<RepoPath> results = artifactory.searches().itemsByProperty().property(colorsProp, "red").doSearch();
        assertEquals(results.size(), 1);
        assertTrue(results.get(0).getItemPath().contains(artifactId + "-1.0.0-zip.jar"));
    }

    @Test
    public void testSearchByPropertyWithMapNotation() throws IOException {
        Map<String, Boolean> properties = new HashMap<>();
        properties.put(releasedProp, true);
        List<RepoPath> results = artifactory.searches().itemsByProperty().properties(properties).doSearch();
        assertEquals(results.size(), 1);
        assertTrue(results.get(0).getItemPath().contains(artifactId + "-1.0.0-zip.jar"));
    }

    @Test
    public void testSearchByPropertyAndRepoFilter() throws IOException {
        List<RepoPath> results =
                artifactory.searches().itemsByProperty().property(releasedProp, true).repositories(localRepository.getKey()).doSearch();
        assertEquals(results.size(), 1);
        assertTrue(results.get(0).getItemPath().contains(artifactId + "-1.0.0-zip.jar"));
    }

    @Test
    public void testSearchByPropertyAndWrongRepoFilter() throws IOException {
        List<RepoPath> list = artifactory.searches().repositories(getJCenterRepoName()).itemsByProperty().property(releasedProp, true).doSearch();
        assertTrue(list.isEmpty());
    }

    @Test
    public void testSearchByPropertyWithMulipleValue() throws IOException {
        List<RepoPath> list = artifactory.searches().itemsByProperty().property(colorsProp, "red", "green", "blue").doSearch();
        assertTrue(list.isEmpty());
    }

    @Test
    public void testSearchByPropertyMapNotationWithMulipleValue() throws IOException {
        Map<String, List<String>> property = new HashMap<>();
        property.put(colorsProp, asList("black", "white", "pink"));
        property.put(buildProp, asList("29"));
        List<RepoPath> list = artifactory.searches().itemsByProperty().properties(property).doSearch();
        assertTrue(list.isEmpty());
    }

    @Test
    public void testSearchByPropertyWithoutValue() throws IOException {
        List<RepoPath> results =
                artifactory.searches().itemsByProperty().property(releasedProp).property(buildProp, 28).doSearch();
        assertEquals(results.size(), 1);
        assertTrue(results.get(0).getItemPath().contains(artifactId + "-1.0.0-zip.jar"));
    }

    @Test
    public void testSearchByPropertyWithWildCards() throws IOException {
        List<RepoPath> results = artifactory.searches().itemsByProperty().property(colorsProp, "r*?").doSearch();
        assertEquals(results.size(), 1);
        assertTrue(results.get(0).getItemPath().contains(artifactId + "-1.0.0-zip.jar"));
    }

    @Test
    public void testSearchByGavc() throws IOException {
        List<RepoPath> results = artifactory.searches().artifactsByGavc()
                .groupId("com.example")
                .artifactId(artifactId)
                .version("1.0.0")
                .classifier("zip")
                .doSearch();
        assertEquals(results.size(), 1);
        assertTrue(results.get(0).getItemPath().contains(artifactId + "-1.0.0-zip.jar"));
    }

    @Test
    public void testSearchByGavcAndRepository() throws IOException {
        List<RepoPath> results = artifactory.searches().artifactsByGavc()
                .groupId("com.example")
                .artifactId(artifactId)
                .version("1.0.0")
                .classifier("zip")
                .repositories(localRepository.getKey())
                .doSearch();
        assertEquals(results.size(), 1);
        assertTrue(results.get(0).getItemPath().contains(artifactId + "-1.0.0-zip.jar"));
    }

    @Test
    public void testArtifactsCreatedSinceSearch() throws IOException {
        long startTime = System.currentTimeMillis() - 86400000L;
        List<RepoPath> results = artifactory.searches().artifactsCreatedSince(startTime).doSearch();
        assertFalse(results.isEmpty());
    }

    @Test
    public void testArtifactsCreatedInDateRangeSearch() throws IOException {
        long now = System.currentTimeMillis() + 60000L;
        long startTime = now - 86400000L;
        List<RepoPath> results = artifactory.searches().artifactsCreatedInDateRange(startTime, now).doSearch();
        assertFalse(results.isEmpty());
    }
}
