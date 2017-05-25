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
        assertEquals("http://myhost.com", artifactory.getUri());
        assertEquals("clienttests", artifactory.getContextName());

        artifactory = create("http://myhost.com:80/clienttests", "", "");
        assertEquals("http://myhost.com:80", artifactory.getUri());
        assertEquals("clienttests", artifactory.getContextName());

        artifactory = create("http://myhost.com:80/clienttests/", "", "");
        assertEquals("http://myhost.com:80", artifactory.getUri());
        assertEquals("clienttests", artifactory.getContextName());

        artifactory = create("http://myhost.com", "", "");
        assertEquals("http://myhost.com", artifactory.getUri());
        assertEquals("", artifactory.getContextName());

        artifactory = create("http://myhost.com:80", "", "");
        assertEquals("http://myhost.com:80", artifactory.getUri());
        assertEquals("", artifactory.getContextName());

        artifactory = create("http://myhost.com:80/", "", "");
        assertEquals("http://myhost.com:80", artifactory.getUri());
        assertEquals("", artifactory.getContextName());

        artifactory = create("http://abc.com:80/ab/artifactory/webapp/webapp", "", "");
        assertEquals("http://abc.com:80", artifactory.getUri());
        assertEquals("ab/artifactory/webapp/webapp", artifactory.getContextName());

        artifactory = create("http://myhost.com:80/", "", "", null, null, null, "testAgent");
        assertEquals(artifactory.getUri(), "http://myhost.com:80");
        assertEquals("", artifactory.getContextName());
        assertEquals("testAgent", artifactory.getUserAgent());
    }

    @Test
    public void urlsBuilderTest() throws IOException {
        Artifactory artifactory;
        artifactory = ArtifactoryClientBuilder.create().setUrl("http://myhost.com/clienttests").build();

        assertEquals("http://myhost.com", artifactory.getUri());
        assertEquals("clienttests", artifactory.getContextName());

        artifactory = ArtifactoryClientBuilder.create().setUrl("http://myhost.com:80/clienttests").build();
        assertEquals("http://myhost.com:80", artifactory.getUri());
        assertEquals("clienttests", artifactory.getContextName());

        artifactory = ArtifactoryClientBuilder.create().setUrl("http://myhost.com:80/clienttests/").build();
        assertEquals("http://myhost.com:80", artifactory.getUri());
        assertEquals("clienttests", artifactory.getContextName());

        artifactory = ArtifactoryClientBuilder.create().setUrl("http://myhost.com").build();
        assertEquals("http://myhost.com", artifactory.getUri());
        assertEquals("", artifactory.getContextName());

        artifactory = ArtifactoryClientBuilder.create().setUrl("http://myhost.com:80").build();
        assertEquals("http://myhost.com:80", artifactory.getUri());
        assertEquals("", artifactory.getContextName());

        artifactory = ArtifactoryClientBuilder.create().setUrl("http://myhost.com:80/").build();
        assertEquals("http://myhost.com:80", artifactory.getUri());
        assertEquals("", artifactory.getContextName());

        artifactory = ArtifactoryClientBuilder.create()
            .setUrl("http://abc.com:80/ab/artifactory/webapp/webapp").build();
        assertEquals("http://abc.com:80", artifactory.getUri());
        assertEquals("ab/artifactory/webapp/webapp", artifactory.getContextName());

        artifactory = ArtifactoryClientBuilder.create()
                .setUrl("http://myhost.com:80/")
                .setUserAgent("testAgent")
                .build();

        assertEquals("http://myhost.com:80", artifactory.getUri());
        assertEquals("", artifactory.getContextName());
        assertEquals("testAgent", artifactory.getUserAgent());
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
