package org.jfrog.artifactory.client.httpClient.http.auth;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.Credentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpRequestWrapper;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.protocol.HttpContext;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Request interceptor to perform preemptive authentication with http client.
 *
 * @author Yossi Shaul
 */
public class PreemptiveAuthInterceptor implements HttpRequestInterceptor {

    public static final String ORIGINAL_HOST_CONTEXT_PARAM = "original.host.context.param";

    @Override
    public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
        if (!shouldSetAuthScheme(request, context)) {
            return;
        }
        HttpClientContext clientContext = HttpClientContext.adapt(context);
        AuthState authState = clientContext.getTargetAuthState();
        // If there's no auth scheme available yet, try to initialize it preemptively
        if (authState.getAuthScheme() == null) {
            String accessToken = clientContext.getUserToken(String.class);
            if (StringUtils.isNotEmpty(accessToken)) {
                request.addHeader("Authorization", "Bearer " + accessToken);
            } else {
                CredentialsProvider credsProvider = clientContext.getCredentialsProvider();
                HttpHost targetHost = clientContext.getTargetHost();
                Credentials creds = credsProvider.getCredentials(
                        new AuthScope(targetHost.getHostName(), targetHost.getPort()));
                if (creds != null) {
                    authState.update(new BasicScheme(), creds);
                }
            }
        }
    }

	private boolean shouldSetAuthScheme(final HttpRequest request, final HttpContext context) {
		// Get the original host name (before the redirect).
		String originalHost = (String) context.getAttribute(ORIGINAL_HOST_CONTEXT_PARAM);
		if (originalHost == null) {
			// No redirect was performed.
			return true;
		}
		String host;
		try {
			// In case of a redirect, get the new target host.
			host = new URI(((HttpRequestWrapper) request).getOriginal().getRequestLine().getUri()).getHost();
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
		// Return true if the original host and the target host are identical.
		return host.equals(originalHost);
	}
}
