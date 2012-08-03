package org.artifactory.client;

import groovyx.net.http.HttpResponseException;
import groovyx.net.http.ResponseParseException;
import org.testng.Assert;
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
    public void testLimitlessQuickSearch() throws HttpResponseException {
        List<String> results = artifactory.searches().search("junit");
        assertJUnits(results);
    }

    @Test(expectedExceptions = HttpResponseException.class, expectedExceptionsMessageRegExp = "Not Found")
    public void testQuickSearchWithWrongSingleLimit() throws HttpResponseException {
        //should fail
        artifactory.searches().limitToRepository(LIBS_RELEASES_LOCAL).search("junit");
    }

    @Test
    public void testQuickSearchWithRightSingleLimit() throws HttpResponseException {
        List<String> results = artifactory.searches().limitToRepository(REPO1_CACHE).search("junit");
        assertJUnits(results);
    }

    @Test
    public void testQuickSearchWithMultipleLimits() throws HttpResponseException {
        List<String> results = artifactory.searches().limitToRepository(LIBS_RELEASES_LOCAL).limitToRepository(REPO1_CACHE).search("junit");
        assertJUnits(results);
    }

    @Test(dependsOnGroups = "uploadBasics")
    public void testSearchByProperty() throws HttpResponseException {
        List<String> results = artifactory.searches().filterBy().property("released", false).search();
        assertEquals(results.size(), 1);
        assertTrue(results.get(0).contains("a/b/c.txt"));
    }

    @Test(dependsOnGroups = "uploadBasics")
    public void testSearchByPropertyAndRepoFilter() throws HttpResponseException {
        List<String> results = artifactory.searches().limitToRepository(NEW_LOCAL).filterBy().property("released", false).search();
        assertEquals(results.size(), 1);
        assertTrue(results.get(0).contains("a/b/c.txt"));
    }

    @Test(dependsOnGroups = "uploadBasics", expectedExceptions = HttpResponseException.class, expectedExceptionsMessageRegExp = "Not Found")
    public void testSearchByPropertyAndWrongRepoFilter() throws HttpResponseException {
        artifactory.searches().limitToRepository(REPO1).filterBy().property("released", false).search();
    }

    @Test(dependsOnGroups = "uploadBasics", expectedExceptions = HttpResponseException.class, expectedExceptionsMessageRegExp = "Not Found")
    public void testSearchByPropertyWithMulipleValue() throws HttpResponseException {
        artifactory.searches().filterBy().property("colors", "red", "green", "blue").search();
    }

    @Test(dependsOnGroups = "uploadBasics")
    public void testSearchByPropertyWithoutValue() throws HttpResponseException {
        List<String> results = artifactory.searches().filterBy().property("released").property("build", 28).search();
        assertEquals(results.size(), 1);
        assertTrue(results.get(0).contains("a/b/c.txt"));
    }

    private void assertJUnits(List<String> results) {
        assertEquals(results.size(), 5);
        for (String result : results) {
            assertTrue(result.contains("junit"));
        }
    }

}
