package org.artifactory.client;

import groovyx.net.http.HttpResponseException;
import org.artifactory.client.model.File;
import org.artifactory.client.model.Folder;
import org.testng.annotations.Test;

import java.util.*;

import static org.testng.Assert.*;

/**
 * @author jbaruch
 * @since 03/08/12
 */
public class ItemTest extends ArtifactoryTestBase {

    @Test
    public void testFolderInfo() {
        Folder folder = artifactory.repository(REPO1_CACHE).folder("junit").get();
        assertNotNull(folder);
        assertTrue(folder.isFolder());
        assertEquals(folder.getChildren().size(), 1);
        assertEquals(folder.getRepo(), REPO1_CACHE);
        assertEquals(folder.getPath(), "/junit");
    }

    @Test
    public void testFileInfo() {
        File file = artifactory.repository(REPO1_CACHE).file("junit/junit/4.10/junit-4.10-sources.jar").get();
        assertNotNull(file);
        assertFalse(file.isFolder());
        assertEquals(file.getSize(), 141185);
        assertEquals(file.getDownloadUri(),
                "http://clienttests.artifactoryonline.com/clienttests/repo1-cache/junit/junit/4.10/junit-4.10-sources.jar");
        assertEquals(file.getChecksums().getMd5(), "8f17d4271b86478a2731deebdab8c846");
        assertEquals(file.getChecksums().getSha1(), "6c98d6766e72d5575f96c9479d1c1d3b865c6e25");
    }

    @Test
    public void testSetItemProperties() {
        //Upload a clean file
        try {
            artifactory.repository(NEW_LOCAL).delete("x/y/z");
        } catch (Exception e) {
            //noinspection ConstantConditions
            if (!(e instanceof HttpResponseException) || ((HttpResponseException) e).getStatusCode() != 404) {
                throw e;
            }
        }
        artifactory.repository(NEW_LOCAL).upload("x/y/z", this.getClass().getResourceAsStream("/sample.txt"))
                .doUpload();
        ItemHandle file = artifactory.repository(NEW_LOCAL).file("x/y/z");
        Map<String, ?> resProps = file.getProps();
        assertTrue(resProps.isEmpty());
        Map<String, String> props = new HashMap<>();
        props.put("p1", "v1");
        file.setProps(props);
        resProps = file.getProps();
        assertEquals(((List) resProps.get("p1")).get(0), "v1");
        props.put("p2", "v2");
        props.put("p3", "v3");
        file.setProps(props);
        resProps = file.getProps();
        assertEquals(((List) resProps.get("p1")).get(0), "v1");
        assertEquals(((List) resProps.get("p2")).get(0), "v2");
        assertEquals(((List) resProps.get("p3")).get(0), "v3");
        Set<String> propNames = new HashSet<>();
        propNames.add("p1");
        propNames.add("p3");
        resProps = file.getProps(propNames);
        assertEquals(resProps.size(), 2);
        assertEquals(((List) resProps.get("p1")).get(0), "v1");
        assertEquals(((List) resProps.get("p3")).get(0), "v3");
        file.deleteProps(propNames);
        resProps = file.getProps();
        assertEquals(resProps.size(), 1);
        assertEquals(((List) resProps.get("p2")).get(0), "v2");
    }
}
