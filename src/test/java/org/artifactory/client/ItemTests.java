package org.artifactory.client;

import org.artifactory.client.model.File;
import org.artifactory.client.model.Folder;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author jbaruch
 * @since 03/08/12
 */
public class ItemTests extends ArtifactoryTestBase {

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

}
