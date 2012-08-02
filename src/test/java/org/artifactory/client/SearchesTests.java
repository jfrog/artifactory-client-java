package org.artifactory.client;

import groovyx.net.http.ResponseParseException;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * @author jbaruch
 * @since 03/08/12
 */
public class SearchesTests extends ArtifactoryTests {

    @Test
    public void testLimitlessQuickSearch() {
        List<String> results = artifactory.searches().searchArtifact("junit");
        assertJUnits(results);
    }

    @Test(expectedExceptions = ResponseParseException.class, expectedExceptionsMessageRegExp = "Not Found")
    public void testQuickSearchWithWrongSingleLimit() {
        //should fail
        artifactory.searches().limitToRepository(LIBS_RELEASES_LOCAL).searchArtifact("junit");
    }

    @Test
    public void testQuickSearchWithRightSingleLimit() {
        List<String> results = artifactory.searches().limitToRepository(REPO1_CACHE).searchArtifact("junit");
        assertJUnits(results);
    }

    @Test
    public void testQuickSearchWithMultipleLimits() {
        List<String> results = artifactory.searches().limitToRepository(LIBS_RELEASES_LOCAL).limitToRepository(REPO1_CACHE).searchArtifact("junit");
        assertJUnits(results);
    }

    @Test(enabled = false)
    public void testSearchByProperty() {

    }

    private void assertJUnits(List<String> results) {
        assertEquals(results.size(), 5);
        for (String result : results) {
            assertTrue(result.contains("junit"));
        }
    }

}
