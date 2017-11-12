package org.jfrog.artifactory.client.httpClient.http.auth;

import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.ChallengeState;
import org.apache.http.auth.Credentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.routing.RouteInfo;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;

/**
 * Request interceptor to perform preemptive authentication against an http proxy.
 *
 * @author Yossi Shaul
 */
public class ProxyPreemptiveAuthInterceptor implements HttpRequestInterceptor {

    public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
        HttpClientContext clientContext = HttpClientContext.adapt(context);
        AuthState proxyAuthState = clientContext.getProxyAuthState();

        // If there's no auth scheme available yet, try to initialize it preemptively
        if (proxyAuthState.getAuthScheme() == null) {
            CredentialsProvider credsProvider = clientContext.getCredentialsProvider();
            RouteInfo route = clientContext.getHttpRoute();
            if (route == null) {
                return;
            }

            HttpHost proxyHost = route.getProxyHost();
            if (proxyHost == null) {
                return;
            }

            Credentials creds = credsProvider.getCredentials(
                    new AuthScope(proxyHost.getHostName(), proxyHost.getPort()));
            if (creds == null) {
                return;
            }
            proxyAuthState.update(new BasicScheme(ChallengeState.PROXY), creds);
        }
    }
}
