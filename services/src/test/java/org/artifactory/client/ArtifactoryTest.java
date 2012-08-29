package org.artifactory.client;

import org.testng.annotations.Test;

import java.io.IOException;

import static junit.framework.Assert.assertEquals;
import static org.artifactory.client.ArtifactoryConnector.create;

/**
 * @author yoavl
 * @since 30/07/12
 */
public abstract class ArtifactoryTest {

    @Test
    public void urlsTest() throws IOException {
        Artifactory artifactory;
        artifactory = create("http://myhost.com/clienttests", "", "");
        assertEquals(artifactory.getUri(), "http://myhost.com");
        assertEquals(artifactory.getContextName(), "clienttests");

        artifactory = create("http://myhost.com:80/clienttests", "", "");
        assertEquals(artifactory.getUri(), "http://myhost.com:80");
        assertEquals(artifactory.getContextName(), "clienttests");

        artifactory = create("http://myhost.com:80/clienttests/", "", "");
        assertEquals(artifactory.getUri(), "http://myhost.com:80");
        assertEquals(artifactory.getContextName(), "clienttests");

        artifactory = create("http://myhost.com", "", "");
        assertEquals(artifactory.getUri(), "http://myhost.com");
        assertEquals(artifactory.getContextName(), "");

        artifactory = create("http://myhost.com:80", "", "");
        assertEquals(artifactory.getUri(), "http://myhost.com:80");
        assertEquals(artifactory.getContextName(), "");

        artifactory = create("http://myhost.com:80/", "", "");
        assertEquals(artifactory.getUri(), "http://myhost.com:80");
        assertEquals(artifactory.getContextName(), "");
    }
}
