package org.artifactory.client;

import org.testng.annotations.Test;

/**
 * @author jbaruch
 * @since 25/07/12
 */
public class FolderInfoTest {
    @Test
    public void testGet() throws Exception {
        Artifactory artifactory = Artifactory.create("http://clienttests.artifactoryonline.com/clienttests", "deployer", "password");
        FolderInfo folderInfo = Storage.folderInfo(artifactory);
        FolderInfo repo1 = folderInfo.repoKey("repo1");
        FolderInfo junit = repo1.folderPath("junit");
        junit.get();
    }
}
