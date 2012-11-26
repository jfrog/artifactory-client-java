package org.artifactory.client;

import groovyx.net.http.HttpResponseException;
import org.artifactory.client.model.File;
import org.artifactory.client.model.Folder;
import org.artifactory.client.model.LocalRepository;
import org.testng.annotations.Test;

import java.util.*;

import static org.testng.Assert.*;

/**
 * @author jbaruch
 * @since 03/08/12
 */
public class ItemTests extends ArtifactoryTestsBase {

    @Test
    public void testFolderInfo() {
        Folder folder = artifactory.repository(REPO1_CACHE).folder("junit").info();
        assertNotNull(folder);
        assertTrue(folder.isFolder());
        assertEquals(folder.getChildren().size(), 1);
        assertEquals(folder.getRepo(), REPO1_CACHE);
        assertEquals(folder.getPath(), "/junit");
    }

    @Test
    public void testFileInfo() {
        File file = artifactory.repository(REPO1_CACHE).file("junit/junit/4.10/junit-4.10-sources.jar").info();
        assertNotNull(file);
        assertFalse(file.isFolder());
        assertEquals(file.getSize(), 141185);
        assertEquals(file.getDownloadUri(),
                url + "/repo1-cache/junit/junit/4.10/junit-4.10-sources.jar");
        assertEquals(file.getChecksums().getMd5(), "8f17d4271b86478a2731deebdab8c846");
        assertEquals(file.getChecksums().getSha1(), "6c98d6766e72d5575f96c9479d1c1d3b865c6e25");
    }

    @Test(groups = "items", dependsOnGroups = "repositoryBasics")
    public void testSetItemProperties() throws Exception {
        //Upload a clean file
        try {
            // Make sure the local repo exists
            LocalRepository localRepository = artifactory.repositories().builders().localRepositoryBuilder().key(NEW_LOCAL)
                    .description("new local repository").build();
            artifactory.repositories().create(2, localRepository);
            artifactory.repository(NEW_LOCAL).delete("x/y/z");
        } catch (Exception e) {
            //noinspection ConstantConditions
            if (!(e instanceof HttpResponseException) || !(((HttpResponseException) e).getStatusCode() == 404 || ((HttpResponseException) e).getStatusCode() == 405)) {
                throw e;
            }
        }
        artifactory.repository(NEW_LOCAL).upload("x/y/z", this.getClass().getResourceAsStream("/sample.txt"))
                .doUpload();
        ItemHandle file = artifactory.repository(NEW_LOCAL).file("x/y/z");
        Map<String, ?> resProps = file.getProperties();
        assertTrue(resProps.isEmpty());
        Map<String, String> props = new HashMap<>();
        props.put("p2", "v2");
        props.put("p3", "v3");
        file.properties().addProperty("p1", "v1").addProperties(props).doSet(false);
        resProps = file.getProperties();
        assertEquals(((List) resProps.get("p1")).get(0), "v1");
        assertEquals(((List) resProps.get("p2")).get(0), "v2");
        assertEquals(((List) resProps.get("p3")).get(0), "v3");
        resProps = file.getProperties("p1", "p3");
        assertEquals(resProps.size(), 2);
        assertEquals(((List) resProps.get("p1")).get(0), "v1");
        assertEquals(((List) resProps.get("p3")).get(0), "v3");
        file.deleteProperties("p1", "p3");
        List<String> p2 = file.getPropertyValues("p2");
        assertEquals(p2.size(), 1);
        assertEquals(p2.get(0), "v2");

        file.properties().addProperty("multi", "a", "b").doSet();
        List<String> multi = file.getPropertyValues("multi");
        assertEquals(multi.size(), 2);
        assertTrue(multi.contains("a") && multi.contains("b"));
    }
}
