package httpClient;

import org.apache.http.HttpHost;
import org.jfrog.artifactory.client.httpClient.http.HttpBuilder;
import org.jfrog.artifactory.client.httpClient.http.ProxyConfig;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

public class HttpBuilderBaseTest {

    @Test
    public void noProxySet() {
        HttpBuilder httpBuilder = new HttpBuilder();
        assertNull(httpBuilder.getProxyHost());
    }

    @Test
    public void noProxyProviderSet() {
        String host = "host";
        int port = 1234;
        ProxyConfig config = new ProxyConfig();
        config.setHost(host);
        config.setPort(port);
        HttpBuilder httpBuilder = new HttpBuilder().proxy(config);
        assertEquals(httpBuilder.getProxyHost(), new HttpHost(host, port));
    }
}