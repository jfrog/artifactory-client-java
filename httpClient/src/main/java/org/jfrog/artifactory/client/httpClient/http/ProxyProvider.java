package org.jfrog.artifactory.client.httpClient.http;

import org.apache.http.HttpHost;

@FunctionalInterface
public interface ProxyProvider {
    HttpHost getProxy();
}
