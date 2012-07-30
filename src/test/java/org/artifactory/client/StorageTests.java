package org.artifactory.client;

import junit.framework.Assert;
import org.artifactory.client.model.Folder;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author jbaruch
 * @since 25/07/12
 */
public class StorageTests extends ArtifactoryTests {

    @Test
    public void testFolderInfo() throws Exception {
        Folder folder = artifactory.storage().folderInfo().repoKey("repo1-cache").folderPath("junit").get();
        Assert.assertNotNull(folder);
    }

    @Test
    public void testFileInfo() throws Exception {
        artifactory.storage().fileInfo().repoKey("repo1").filePath("junit/junit/4.10/junit-4.10.jar");

    }

    @Test
    public void testItemLastModified() throws Exception {

    }
}
