package org.artifactory.client;

import junit.framework.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * @author yoavl
 * @since 30/07/12
 */
public abstract class ArtifactoryTest {

    @Test
    public void urlsTest() throws IOException {
        Artifactory artifactory;
        artifactory = Artifactory.create("http://myhost.com/clienttests", "", "");
        Assert.assertEquals(artifactory.getUri(), "http://myhost.com");
        Assert.assertEquals(artifactory.getContextName(), "clienttests");

        artifactory = Artifactory.create("http://myhost.com:80/clienttests", "", "");
        Assert.assertEquals(artifactory.getUri(), "http://myhost.com:80");
        Assert.assertEquals(artifactory.getContextName(), "clienttests");

        artifactory = Artifactory.create("http://myhost.com:80/clienttests/", "", "");
        Assert.assertEquals(artifactory.getUri(), "http://myhost.com:80");
        Assert.assertEquals(artifactory.getContextName(), "clienttests");

        artifactory = Artifactory.create("http://myhost.com", "", "");
        Assert.assertEquals(artifactory.getUri(), "http://myhost.com");
        Assert.assertEquals(artifactory.getContextName(), "");

        artifactory = Artifactory.create("http://myhost.com:80", "", "");
        Assert.assertEquals(artifactory.getUri(), "http://myhost.com:80");
        Assert.assertEquals(artifactory.getContextName(), "");

        artifactory = Artifactory.create("http://myhost.com:80/", "", "");
        Assert.assertEquals(artifactory.getUri(), "http://myhost.com:80");
        Assert.assertEquals(artifactory.getContextName(), "");
    }
}
