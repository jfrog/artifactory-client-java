package org.jfrog.artifactory.client.httpClient.http;

import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.conn.DefaultRoutePlanner;
import org.apache.http.impl.conn.DefaultSchemePortResolver;
import org.apache.http.protocol.HttpContext;


public class DefaultHostSpecificProxyRoutePlanner extends DefaultRoutePlanner {

    private final HttpHost defaultHost;
    private final ProxyProvider proxyProvider;
    private final NoProxyHostsEvaluator noProxyHostsEvaluator;

    private DefaultHostSpecificProxyRoutePlanner(HttpHost defaultHost,
                                                 ProxyProvider proxyProvider, String noProxyHosts) {
        super(DefaultSchemePortResolver.INSTANCE);
        this.defaultHost = defaultHost;
        this.proxyProvider = proxyProvider;
        this.noProxyHostsEvaluator = new NoProxyHostsEvaluator(noProxyHosts);
    }

    public static class Builder {

        private HttpHost defaultHost;
        private ProxyProvider proxyProvider = () -> null;
        private String noProxyHosts;

        public Builder defaultHost(HttpHost defaultHost) {
            this.defaultHost = defaultHost;
            return this;
        }

        public Builder proxyProvider(ProxyProvider proxyProvider) {
            this.proxyProvider = proxyProvider;
            return this;
        }

        public Builder noProxyHosts(String noProxyHosts) {
            this.noProxyHosts = noProxyHosts;
            return this;
        }

        public DefaultHostSpecificProxyRoutePlanner build() {
            return new DefaultHostSpecificProxyRoutePlanner(defaultHost, proxyProvider, noProxyHosts);
        }
    }

    @Override
    public HttpRoute determineRoute(HttpHost host, HttpRequest request, HttpContext context)
            throws HttpException {
        if (host == null) {
            host = defaultHost;
        }
        return super.determineRoute(host, request, context);
    }

    @Override
    protected HttpHost determineProxy(HttpHost target, HttpRequest request, HttpContext context) {
        HttpHost proxy = proxyProvider.getProxy();
        if (proxy != null// no proxy -> no need to check
                && (isTargetLocalhost(target) // first check localhost
                || (noProxyHostsEvaluator.shouldBypassProxy(target.toHostString())))) { // check host from list
            return null;
        }
        return proxy;
    }

    public HttpHost getDefaultHost() {
        return defaultHost;
    }

    public HttpHost getProxy() {
        return proxyProvider.getProxy();
    }

    public String getNoProxyHosts() {
        return noProxyHostsEvaluator.getNoProxyHosts();
    }

    private boolean isTargetLocalhost(HttpHost target) {
        return target.getHostName().equalsIgnoreCase("localhost")
                || target.getHostName().equals("127.0.0.1");
    }
}