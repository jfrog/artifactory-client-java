package httpClient;

import org.apache.http.HttpHost;
import org.jfrog.artifactory.client.httpClient.http.DefaultHostSpecificProxyRoutePlanner;
import org.jfrog.artifactory.client.httpClient.http.ProxyProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

public class DefaultHostSpecificProxyRoutePlannerTest {

    @Test
    public void getProxy() {
        String host = "proxyHost";
        int port = 4567;
        DefaultHostSpecificProxyRoutePlanner routePlanner = new DefaultHostSpecificProxyRoutePlanner.Builder()
                .proxyProvider(() -> new HttpHost(host, port)).build();
        assertEquals(new HttpHost(host, port), routePlanner.getProxy());
    }

    @Test
    public void changeProxy() {
        TestProxyProvider proxyProvider = new TestProxyProvider();
        DefaultHostSpecificProxyRoutePlanner routePlanner = new DefaultHostSpecificProxyRoutePlanner.Builder()
                .proxyProvider(proxyProvider).build();
        assertNull(routePlanner.getProxy());

        String host = "host";
        int port = 9876;
        proxyProvider.proxy = new HttpHost(host, port);
        assertEquals(new HttpHost(host, port), routePlanner.getProxy());
    }

    @Test
    public void getProxyDefaultConfigHasNotProxy() {
        DefaultHostSpecificProxyRoutePlanner routePlanner = new DefaultHostSpecificProxyRoutePlanner.Builder().build();
        assertNull(routePlanner.getProxy());
    }

    private static class TestProxyProvider implements ProxyProvider {
        private HttpHost proxy;

        @Override
        public HttpHost getProxy() {
            return proxy;
        }
    }
}