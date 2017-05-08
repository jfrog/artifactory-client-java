package org.jfrog.artifactory.client;

import java.io.IOException;
import static junit.framework.Assert.assertEquals;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import static org.jfrog.artifactory.client.ArtifactoryClient.create;
import org.testng.annotations.Test;

/**
 * @author yoavl
 * @since 30/07/12
 */
public class ArtifactoryTests {

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

        artifactory = create("http://myhost.com:80/", "", "", null, null, null, "testAgent");
        assertEquals(artifactory.getUri(), "http://myhost.com:80");
        assertEquals(artifactory.getContextName(), "");
        assertEquals(artifactory.getUserAgent(), "testAgent");
    }

    @Test
    public void urlsBuilderTest() throws IOException {
        Artifactory artifactory;
        artifactory = ArtifactoryClientBuilder.create().setUrl("http://myhost.com/clienttests").build();

        assertEquals(artifactory.getUri(), "http://myhost.com");
        assertEquals(artifactory.getContextName(), "clienttests");

        artifactory = ArtifactoryClientBuilder.create().setUrl("http://myhost.com:80/clienttests").build();
        assertEquals(artifactory.getUri(), "http://myhost.com:80");
        assertEquals(artifactory.getContextName(), "clienttests");

        artifactory = ArtifactoryClientBuilder.create().setUrl("http://myhost.com:80/clienttests/").build();
        assertEquals(artifactory.getUri(), "http://myhost.com:80");
        assertEquals(artifactory.getContextName(), "clienttests");

        artifactory = ArtifactoryClientBuilder.create().setUrl("http://myhost.com").build();
        assertEquals(artifactory.getUri(), "http://myhost.com");
        assertEquals(artifactory.getContextName(), "");

        artifactory = ArtifactoryClientBuilder.create().setUrl("http://myhost.com:80").build();
        assertEquals(artifactory.getUri(), "http://myhost.com:80");
        assertEquals(artifactory.getContextName(), "");

        artifactory = ArtifactoryClientBuilder.create().setUrl("http://myhost.com:80/").build();
        assertEquals(artifactory.getUri(), "http://myhost.com:80");
        assertEquals(artifactory.getContextName(), "");

        artifactory = ArtifactoryClientBuilder.create()
                .setUrl("http://myhost.com:80/")
                .setUserAgent("testAgent")
                .build();

        assertEquals(artifactory.getUri(), "http://myhost.com:80");
        assertEquals(artifactory.getContextName(), "");
        assertEquals(artifactory.getUserAgent(), "testAgent");
    }

    @Test
    public void connectionPoolBuilderTest() throws IOException {
        PoolingHttpClientConnectionManager pool = new PoolingHttpClientConnectionManager();
        pool.setDefaultMaxPerRoute(2);
        pool.setMaxTotal(2);

        ArtifactoryClientBuilder builder = ArtifactoryClientBuilder.create();
        builder.setUrl("http://myhost.com:80/");
        builder.setConnectionManager(pool);

        assertEquals(builder.getCm(), pool);

        builder.build();
    }

    @Test
    public void proxyBuilderTest() {
        ArtifactoryClientBuilder builder = ArtifactoryClientBuilder.create();
        builder.setUrl("http://myhost.com:80/");
        ArtifactoryClient.ProxyConfig proxy = new ArtifactoryClient.ProxyConfig("", 9090, "http", "user", "password");
        builder.setProxy(proxy);

        assertEquals(builder.getProxy(), proxy);
        builder.build();
    }

    @Test
    public void connectionTimeoutBuilderTest() {
        ArtifactoryClientBuilder builder = ArtifactoryClientBuilder.create();
        builder.setUrl("http://myhost.com:80/")
                .setConnectionTimeout(100);

        assertEquals(builder.getConnectionTimeout(), new Integer(100));
        builder.build();
    }

    @Test
    public void socketTimeoutBuilderTest() {
        ArtifactoryClientBuilder builder = ArtifactoryClientBuilder.create();
        builder.setUrl("http://myhost.com:80/")
                .setSocketTimeout(100);

        assertEquals(builder.getSocketTimeout(), new Integer(100));
        builder.build();
    }
}
