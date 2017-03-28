package org.jfrog.artifactory.client;

import groovyx.net.http.RESTClient;
import org.jfrog.artifactory.client.impl.ArtifactoryImpl;
import org.testng.annotations.Test;

import java.io.IOException;

import static junit.framework.Assert.assertEquals;
import static org.jfrog.artifactory.client.ArtifactoryClient.create;

/**
 * @author yoavl
 * @since 30/07/12
 */
public class ArtifactoryTests extends ArtifactoryTestsBase {

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

        artifactory = create("http://myhost.com:80/", "", "", false, null, null, null, "testAgent");
        assertEquals(artifactory.getUri(), "http://myhost.com:80");
        assertEquals(artifactory.getContextName(), "");
        assertEquals(artifactory.getUserAgent(), "testAgent");
    }

    //TODO Testing the usage of apiKey is not possible at the moment. The headers can't be returned after a rest call so we can't test if the header is actually used.
    //This test should assert if the header is set in the call to Artifactory, until then this is a useless test
    @Test
    public void connectionTest() {
        ArtifactoryImplTest artifactory = (ArtifactoryImplTest) getArtifactoryClientWithApiKey();
        assertEquals(true,artifactory.getApiKey());
    }

    class ArtifactoryImplTest extends ArtifactoryImpl {

        public ArtifactoryImplTest(RESTClient client, String contextName) {
            super(client, contextName);
        }
        RESTClient myClient = getClient();

        public String getApiKey() {
            return (String) myClient.getHeaders().get("X-JFrog-Art-Api");
        }
    }
}
