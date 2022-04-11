package httpClient;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.stubbing.ServeEvent;
import com.google.common.collect.Lists;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.conn.DefaultRoutePlanner;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.jfrog.artifactory.client.httpClient.http.DefaultHostSpecificProxyRoutePlanner;
import org.jfrog.artifactory.client.httpClient.http.HttpBuilder;
import org.jfrog.artifactory.client.httpClient.http.ProxyConfig;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.testng.AssertJUnit.assertNull;


@Test
public class HttpBuilderTest {

    private static final String NOT_PROXIED_HOST = "http://jfrog.com:80";
    private static final String NO_PROXY_HOSTS_LIST = ".gom.com,*.jfrog.com,.not.important.host:443";
    private static final List<String> GET_REQUEST_URI_BASIC_LIST = Lists.newArrayList(
            NOT_PROXIED_HOST, "http://google.com", "http://microsoft.com");

    public void testConstructor() throws IOException {
        try (CloseableHttpClient multiThreadedClient = new HttpBuilder().build()) {
            Assert.assertNotNull(multiThreadedClient, "A valid client should have been constructed.");

            HttpClientConnectionManager connManager = getConnManager(multiThreadedClient);
            Assert.assertTrue(connManager instanceof PoolingHttpClientConnectionManager,
                    "Expected a multi-threaded connection manager but found " + multiThreadedClient.getClass());
        }
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testHostFromInvalidUrl() {
        new HttpBuilder().hostFromUrl("sttp:/.com");
    }

    public void testHost() throws IOException {
        try (CloseableHttpClient client = new HttpBuilder().host("bob").build()) {
            HttpHost defaultHost = getDefaultHost(client);
            Assert.assertEquals(defaultHost.getHostName(), "bob", "Unexpected default host");
            Assert.assertEquals(defaultHost.getPort(), 80, "Using default port");
            Assert.assertEquals(defaultHost.getSchemeName(), "http", "Using default port");
        }
    }

    public void testHostPort() throws IOException {
        try (CloseableHttpClient client = new HttpBuilder().host("bob", 9090).build()) {
            HttpHost defaultHost = getDefaultHost(client);
            Assert.assertEquals(defaultHost.getHostName(), "bob", "Unexpected default host");
            Assert.assertEquals(defaultHost.getPort(), 9090, "Using default port");
            Assert.assertEquals(defaultHost.getSchemeName(), "http", "Using default port");
        }
    }

    public void testHostPortHttps() throws IOException {
        try (CloseableHttpClient client = new HttpBuilder().host("bob", 443).build()) {
            HttpHost defaultHost = getDefaultHost(client);
            Assert.assertEquals(defaultHost.getHostName(), "bob", "Unexpected default host");
            Assert.assertEquals(defaultHost.getPort(), 443, "Using default port");
            Assert.assertEquals(defaultHost.getSchemeName(), "https", "Using default port");
        }
    }

    public void testHostPortScheme() throws IOException {
        try (CloseableHttpClient client = new HttpBuilder().host("bob", 8080, "https").build()) {
            HttpHost defaultHost = getDefaultHost(client);
            Assert.assertEquals(defaultHost.getHostName(), "bob", "Unexpected default host");
            Assert.assertEquals(defaultHost.getPort(), 8080, "Using default port");
            Assert.assertEquals(defaultHost.getSchemeName(), "https", "Using default port");
        }
    }

    public void testHostFromUrlWithHostOnly() throws IOException {
        try (CloseableHttpClient client = new HttpBuilder().hostFromUrl("http://bob").build()) {
            HttpHost defaultHost = getDefaultHost(client);
            Assert.assertEquals(defaultHost.getHostName(), "bob", "Unexpected host.");
            Assert.assertEquals(defaultHost.getPort(), -1, "Unexpected host.");
        }
    }

    public void testHostFromUrlWithPort() throws IOException {
        try (CloseableHttpClient client = new HttpBuilder().hostFromUrl("http://bob:9745").build()) {
            HttpHost defaultHost = getDefaultHost(client);
            Assert.assertEquals(defaultHost.getHostName(), "bob", "Unexpected host.");
            Assert.assertEquals(defaultHost.getPort(), 9745, "Unexpected host.");
            Assert.assertEquals(defaultHost.getSchemeName(), "http", "Unexpected host.");
        }
    }

    public void testHostFromUrlWithPortAndHttpsSchema() throws IOException {
        try (CloseableHttpClient client = new HttpBuilder().hostFromUrl("https://bob:9745").build()) {
            HttpHost defaultHost = getDefaultHost(client);
            Assert.assertEquals(defaultHost.getSchemeName(), "https", "Unexpected host.");
            Assert.assertEquals(defaultHost.getHostName(), "bob", "Unexpected host.");
            Assert.assertEquals(defaultHost.getPort(), 9745, "Unexpected host.");
        }
    }

    public void testHostNameWithUnderscore() throws IOException {
        try (CloseableHttpClient client = new HttpBuilder().hostFromUrl("https://my_host").build()) {
            HttpHost defaultHost = getDefaultHost(client);
            Assert.assertEquals(defaultHost.getHostName(), "my_host", "Unexpected host.");
            Assert.assertEquals(defaultHost.getSchemeName(), "https", "Unexpected scheme");
        }
    }

    public void testHostFromUrlWithContext() throws IOException {
        try (CloseableHttpClient client = new HttpBuilder().hostFromUrl("https://my_host:892/ignored")
                .build()) {
            HttpHost defaultHost = getDefaultHost(client);
            Assert.assertEquals(defaultHost.getHostName(), "my_host", "Unexpected host");
            Assert.assertEquals(defaultHost.getPort(), 892, "Unexpected port");
            Assert.assertEquals(defaultHost.getSchemeName(), "https", "Unexpected scheme");
        }
    }

    public void testRoutingWithoutProxy() throws IOException {
        List<ServeEvent> events = getNumberOfServeEvents(false, false);
        Assert.assertEquals(events.size(), 1);
        verifyProxying(events);
    }

    public void testRoutingWithProxy() throws IOException {
        List<ServeEvent> events = getNumberOfServeEvents(true, false);
        Assert.assertEquals(events.size(), 4);
        verifyProxying(events);
    }

    public void testRoutingWithoutProxyWithNonProxyHosts() throws IOException {
        List<ServeEvent> events = getNumberOfServeEvents(false, true);
        Assert.assertEquals(events.size(), 1);
        verifyProxying(events);
    }

    public void testRoutingWithProxyWithNonProxyHosts() throws IOException {
        List<ServeEvent> events = getNumberOfServeEvents(true, true);
        Assert.assertEquals(events.size(), 3);
        verifyProxying(events);
    }

    public void testRoutingWithProxyAndCredentials() throws IOException {
        WireMockServer wireMockServer = new WireMockServer(WireMockConfiguration.options().dynamicPort().bindAddress("localhost"));
        try {
            wireMockServer.stubFor(WireMock.get(WireMock.urlEqualTo("/test/proxy"))
                    .willReturn(WireMock.aResponse().withStatus(200)));
            wireMockServer.start();

            ProxyConfig proxyConfig = new ProxyConfig();
            proxyConfig.setHost("localhost");
            proxyConfig.setPort(wireMockServer.port());
            proxyConfig.setUsername("proxy_user");
            proxyConfig.setPassword("proxy_pass");

            try (CloseableHttpClient client = new HttpBuilder()
                    .hostFromUrl("http://jfrog.com")
                    .proxy(proxyConfig)
                    .authentication("auth_user", "auth_pass")
                    .build()) {

                // first testing that the HttpClient hold both host and proxy different credentials
                BasicCredentialsProvider credsProvider = getBasicCredentialsProvider(client);
                Assert.assertTrue(credsProvider != null && credsProvider.toString().contains("auth_user")
                        && credsProvider.toString().contains("proxy_user"));
                client.execute(new HttpGet("/test/proxy"));
            }

            wireMockServer.verify(WireMock.getRequestedFor(WireMock.urlEqualTo("/test/proxy"))
                    .withHeader(HttpHeaders.PROXY_AUTHORIZATION, WireMock.containing("Basic ")));
        } finally {
            wireMockServer.stop();
            wireMockServer.shutdownServer();
        }
    }

    public void testConnectionManagerConnectNoSoConfigSetByDefault() throws IOException {
        try (CloseableHttpClient client = new HttpBuilder().build()) {
            HttpClientConnectionManager connManager = getConnManager(client);
            assertNull(((PoolingHttpClientConnectionManager) connManager).getDefaultSocketConfig());
        }
    }

    public void testConnectionManagerConnectTimeoutSameAsConnectionTimeout() throws IOException {
        int socketTimeout = 15000;
        try (CloseableHttpClient client = new HttpBuilder().connectionTimeout(socketTimeout).build()) {
            HttpClientConnectionManager connManager = getConnManager(client);
            SocketConfig a = ((PoolingHttpClientConnectionManager) connManager).getDefaultSocketConfig();
            Assert.assertEquals(((PoolingHttpClientConnectionManager) connManager).getDefaultSocketConfig().getSoTimeout(),
                    socketTimeout);
        }
    }

    private static <T> T getField(Object target, String name) {
        try {
            Field field = findField(target.getClass(), name);
            if (field == null) {
                throw new IllegalArgumentException("Could not find field [" + name + "] on target [" + target + "]");
            }
            field.setAccessible(true);
            return (T) field.get(target);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static Field findField(Class<?> clazz, String name) {
        Class<?> searchType = clazz;
        while (searchType != null) {
            Field[] fields = searchType.getDeclaredFields();
            for (Field field : fields) {
                if ((name == null || name.equals(field.getName()))) {
                    return field;
                }
            }
            searchType = searchType.getSuperclass();
        }
        return null;
    }

    private DefaultRoutePlanner getRoutePlanner(HttpClient client) {
        return getField(getCloseableHttpClient(client), "routePlanner");
    }

    private HttpHost getDefaultHost(DefaultHostSpecificProxyRoutePlanner routePlanner) {
        return routePlanner.getDefaultHost();
    }

    private HttpClientConnectionManager getConnManager(HttpClient client) {
        return getField(getCloseableHttpClient(client), "connManager");
    }

    private CloseableHttpClient getCloseableHttpClient(HttpClient client) {
        return getField(client, "closeableHttpClient");
    }

    private HttpHost getDefaultHost(CloseableHttpClient client) {
        DefaultRoutePlanner routePlanner = getRoutePlanner(client);
        assertThat(routePlanner).isInstanceOf(DefaultHostSpecificProxyRoutePlanner.class);
        return getDefaultHost((DefaultHostSpecificProxyRoutePlanner) routePlanner);
    }

    private BasicCredentialsProvider getBasicCredentialsProvider(HttpClient client) {
        return getField(getCloseableHttpClient(client), "credentialsProvider");
    }

    private List<ServeEvent> getNumberOfServeEvents(boolean withProxy, boolean withNoProxyHosts)
            throws IOException {
        WireMockServer wireMockServer = new WireMockServer(WireMockConfiguration.options().dynamicPort().enableBrowserProxying(true));
        try {
            // Wiremock is used both for tracking requests (even without stubbing)
            // and for being an online proxy server
            wireMockServer.start();
            ProxyConfig proxyConfig = new ProxyConfig();
            proxyConfig.setHost("localhost");
            proxyConfig.setPort(wireMockServer.port());

            // each test will have a unique port of running wiremock server for testing localhost
            List<String> getRequestUriList = Lists.newLinkedList(GET_REQUEST_URI_BASIC_LIST);
            getRequestUriList.add("http://127.0.0.1:" + wireMockServer.port());

            try (CloseableHttpClient client = new HttpBuilder()
                    .redirectStrategy(new DefaultRedirectStrategy(new String[0]))
                    .proxy(withProxy ? proxyConfig : null)
                    .noProxyHosts(withNoProxyHosts ? NO_PROXY_HOSTS_LIST : null)
                    .build()) {
                clientExecution(client, getRequestUriList);
            }

            return wireMockServer.getAllServeEvents();
        } finally {
            wireMockServer.resetAll();
            wireMockServer.stop();
        }
    }

    private void clientExecution(CloseableHttpClient client, List<String> getRequestUriList)
            throws IOException {
        for (String uri : getRequestUriList) {
            client.execute(new HttpGet(uri));
        }
    }

    private void verifyProxying(List<ServeEvent> events) {
        for (ServeEvent event : events) {
            if (event.getRequest().getAbsoluteUrl().contains("127.0.0.1")
                    || event.getRequest().getAbsoluteUrl().contains(NOT_PROXIED_HOST)) {
                Assert.assertFalse(event.getRequest().isBrowserProxyRequest());
            } else {
                Assert.assertTrue(event.getRequest().isBrowserProxyRequest());
            }
        }
    }
}
