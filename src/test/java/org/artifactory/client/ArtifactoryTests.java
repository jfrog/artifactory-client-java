package org.artifactory.client;

import org.testng.annotations.BeforeMethod;

/**
 * @author jbaruch
 * @since 30/07/12
 */
public class ArtifactoryTests {
    protected Artifactory artifactory;

    @BeforeMethod
    public void init() {
        artifactory =
                Artifactory.create("http://clienttests.artifactoryonline.com", "clienttests", "admin", "password");
    }
}
