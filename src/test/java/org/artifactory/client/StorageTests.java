package org.artifactory.client;

import groovy.lang.Closure;
import org.apache.http.HttpResponse;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.Reader;

/**
 * @author jbaruch
 * @since 25/07/12
 */
public class StorageTests {

    private Artifactory artifactory;

    @BeforeMethod
    public void init() {
        artifactory = Artifactory.create("http://clienttests.artifactoryonline.com", "clienttests", "deployer", "password");
    }

    @Test
    public void testFolderInfo() throws Exception {
        Storage.folderInfo(artifactory).repoKey("repo1-cache").folderPath("junit").get();
    }

    @Test
    public void testFileInfo() throws Exception {
        Storage.fileInfo(artifactory).repoKey("repo1").filePath("junit/junit/4.10/junit-4.10.jar");

    }

    @Test
    public void testItemLastModified() throws Exception {

    }
}
