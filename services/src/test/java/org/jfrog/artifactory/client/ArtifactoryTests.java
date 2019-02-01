package org.jfrog.artifactory.client;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.apache.commons.io.IOUtils;
import org.jfrog.artifactory.client.impl.ArtifactoryRequestImpl;
import org.junit.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

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


    @Test
    public void apiKeyBuilderTest() throws IOException {
        HttpServer server = createServer();
        server.start();

        String apiKey = "my-api-key";
        ArtifactoryClientBuilder builder = ArtifactoryClientBuilder.create();
        builder.setUrl("http://localhost:" + server.getAddress().getPort())
                .setApiKey(apiKey);

        Artifactory artifactory = builder.build();

        ArtifactoryRequest repositoryRequest = new ArtifactoryRequestImpl().apiUrl("/api/system/ping")
                .method(ArtifactoryRequest.Method.POST)
                .responseType(ArtifactoryRequest.ContentType.JSON);

        ArtifactoryResponse response = artifactory.restCall(repositoryRequest);
        Assert.assertTrue(response.isSuccessResponse());
        Assert.assertEquals(apiKey, response.getRawBody());
    }

    private HttpServer createServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(0), 0);
        server.createContext("/api/system/ping", new RestMockHandler());
        server.setExecutor(null);
        return server;
    }

    class RestMockHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            Headers headers = t.getRequestHeaders();
            String response = null;
            if(headers.containsKey("X-JFrog-Art-Api")) {
                response = headers.getFirst("X-JFrog-Art-Api");
            }

            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

}
