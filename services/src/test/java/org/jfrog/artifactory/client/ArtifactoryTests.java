package org.jfrog.artifactory.client;

import org.testng.annotations.Test;

import java.io.IOException;

import static junit.framework.Assert.assertEquals;

/**
 * @author yoavl
 * @since 30/07/12
 */
public class ArtifactoryTests {

    @Test
    public void urlsTest() throws IOException {
        Artifactory artifactory;
        ArtifactoryClientBuilder artifactoryClientBuilder = ArtifactoryClientBuilder.create();

        artifactory = artifactoryClientBuilder.setUrl("http://myhost.com/clienttests").setUsername("").setPassword("").build();
        assertEquals("http://myhost.com", artifactory.getUri());
        assertEquals("clienttests", artifactory.getContextName());

        artifactory = artifactoryClientBuilder.setUrl("http://myhost.com:80/clienttests").setUsername("").setPassword("").build();
        assertEquals("http://myhost.com:80", artifactory.getUri());
        assertEquals("clienttests", artifactory.getContextName());

        artifactory = artifactoryClientBuilder.setUrl("http://myhost.com:80/clienttests/").setUsername("").setPassword("").build();
        assertEquals("http://myhost.com:80", artifactory.getUri());
        assertEquals("clienttests", artifactory.getContextName());

        artifactory = artifactoryClientBuilder.setUrl("http://myhost.com").setUsername("").setPassword("").build();
        assertEquals("http://myhost.com", artifactory.getUri());
        assertEquals("", artifactory.getContextName());

        artifactory = artifactoryClientBuilder.setUrl("http://myhost.com:80").setUsername("").setPassword("").build();
        assertEquals("http://myhost.com:80", artifactory.getUri());
        assertEquals("", artifactory.getContextName());

        artifactory = artifactoryClientBuilder.setUrl("http://myhost.com:80/").setUsername("").setPassword("").build();
        assertEquals("http://myhost.com:80", artifactory.getUri());
        assertEquals("", artifactory.getContextName());

        artifactory = artifactoryClientBuilder.setUrl("http://abc.com:80/ab/artifactory/webapp/webapp").setUsername("").setPassword("").build();
        assertEquals("http://abc.com:80", artifactory.getUri());
        assertEquals("ab/artifactory/webapp/webapp", artifactory.getContextName());

        artifactory = artifactoryClientBuilder.setUrl("http://myhost.com:80/").setUsername("").setPassword("").setUserAgent("testAgent").build();
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
    public void proxyBuilderTest() {
        ArtifactoryClientBuilder builder = ArtifactoryClientBuilder.create();
        builder.setUrl("http://myhost.com:80/");
        ProxyConfig proxy = new ProxyConfig("localhost", 9090, "http", "user", "password");
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
