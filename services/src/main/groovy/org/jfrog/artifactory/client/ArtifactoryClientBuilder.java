package org.jfrog.artifactory.client;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.ssl.SSLContextBuilder;
import org.jfrog.artifactory.client.httpClient.http.HttpBuilderBase;
import org.jfrog.artifactory.client.impl.ArtifactoryImpl;
import org.jfrog.artifactory.client.impl.util.ArtifactoryHttpClient;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author jbaruch
 * @author Lior Hasson
 * @author Alexei Vainshtein
 */
@SuppressWarnings("UnusedReturnValue")
public class ArtifactoryClientBuilder {

    private final List<HttpRequestInterceptor> requestInterceptorList = new ArrayList<>();

    private String url;
    private String username;
    private String password;
    private Integer connectionTimeout;
    private Integer socketTimeout;
    private ProxyConfig proxy;
    private String userAgent;
    private boolean ignoreSSLIssues;
    private SSLContext sslContext;
    private SSLContextBuilder sslContextBuilder;
    private String accessToken;

    protected ArtifactoryClientBuilder() {
        super();
    }

    public static ArtifactoryClientBuilder create() {
        return new ArtifactoryClientBuilder();
    }

    public ArtifactoryClientBuilder setUrl(String url) {
        if (url.endsWith("/")) {
            url = url.substring(0, url.length() - 1);
        }
        this.url = url;
        return this;
    }

    public ArtifactoryClientBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    public ArtifactoryClientBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public ArtifactoryClientBuilder setConnectionTimeout(Integer connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
        return this;
    }

    public ArtifactoryClientBuilder setSocketTimeout(Integer socketTimeout) {
        this.socketTimeout = socketTimeout;
        return this;
    }

    public ArtifactoryClientBuilder setProxy(ProxyConfig proxy) {
        this.proxy = proxy;
        return this;
    }

    public ArtifactoryClientBuilder setUserAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }

    public ArtifactoryClientBuilder setIgnoreSSLIssues(boolean ignoreSSLIssues) {
        this.ignoreSSLIssues = ignoreSSLIssues;
        return this;
    }

    public ArtifactoryClientBuilder setSslContextBuilder(SSLContextBuilder sslContextBuilder) {
        this.sslContextBuilder = sslContextBuilder;
        return this;
    }

    public ArtifactoryClientBuilder setSslContext(SSLContext sslContext) {
        this.sslContext = sslContext;
        return this;
    }

    public ArtifactoryClientBuilder setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    /**
     * Add an Http request interceptor to the underlying Http client builder used by the artifactory client
     * <br>
     * For further details see
     * {@link org.apache.http.impl.client.HttpClientBuilder#addInterceptorLast(org.apache.http.HttpRequestInterceptor)}
     *
     * @param httpRequestInterceptor request interceptor that allows manipulating and examining of outgoing requests
     * @return ArtifactoryClientBuilder
     */
    public ArtifactoryClientBuilder addInterceptorLast(HttpRequestInterceptor httpRequestInterceptor) {
        this.requestInterceptorList.add(httpRequestInterceptor);
        return this;
    }

    private CloseableHttpClient createClientBuilder(URI uri) {
        ArtifactoryHttpClient artifactoryHttpClient = new ArtifactoryHttpClient();
        artifactoryHttpClient.hostFromUrl(uri.toString());


        if (StringUtils.isEmpty(accessToken)) {
            artifactoryHttpClient.authentication(username, password);
        }

        if (this.proxy != null) {
            HttpBuilderBase.ProxyConfigBuilder proxyConfigBuilder = artifactoryHttpClient.proxy(this.proxy.getHost(), this.proxy.getPort());
            if (this.proxy.getUser() != null) {
                proxyConfigBuilder.authentication(this.proxy.getUser(), this.proxy.getPassword());
            }
        }
        artifactoryHttpClient.userAgent(userAgent);

        if (connectionTimeout != null) {
            artifactoryHttpClient.connectionTimeout(connectionTimeout);
        }

        if (socketTimeout != null) {
            artifactoryHttpClient.socketTimeout(socketTimeout);
        }

        if (sslContext != null) {
            artifactoryHttpClient.sslContext(sslContext);
        } else if (sslContextBuilder != null) {
            artifactoryHttpClient.sslContextBuilder(sslContextBuilder);
        } else {
            artifactoryHttpClient.trustSelfSignCert(!ignoreSSLIssues);
        }
        for (HttpRequestInterceptor httpRequestInterceptor : requestInterceptorList) {
            artifactoryHttpClient.addInterceptorLast(httpRequestInterceptor);
        }
        return artifactoryHttpClient.build();
    }

    public Artifactory build() {
        URI uri;
        try {
            uri = new URIBuilder(url).build();
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid Artifactory URL: " + url + ".", e);
        }
        if (this.sslContext != null && ignoreSSLIssues) {
            throw new IllegalStateException("SslContext can't be set with ignoreSSLIssues=true.");
        }

        if (StringUtils.isBlank(userAgent)) {
            try {
                userAgent = getUserAgent();
            } catch (IOException e) {
                userAgent = "artifactory-client-java";
            }
        }

        CloseableHttpClient closeableHttpClient = createClientBuilder(uri);

        return new ArtifactoryImpl(closeableHttpClient, url, userAgent, username, accessToken);
    }

    private static String getUserAgent() throws IOException {
        Properties prop = new Properties();
        URL resource = ArtifactoryClientBuilder.class.getClassLoader()
                .getResource("artifactory.client.release.properties");
        if (resource != null) {
            InputStream propStream = resource.openStream();
            prop.load(propStream);
            return "artifactory-client-java/" + prop.getProperty("version");
        }
        return "";
    }

    public ProxyConfig getProxy() {
        return proxy;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Integer getConnectionTimeout() {
        return connectionTimeout;
    }

    public Integer getSocketTimeout() {
        return socketTimeout;
    }

    public boolean isIgnoreSSLIssues() {
        return ignoreSSLIssues;
    }

    public SSLContextBuilder getSslContextBuilder() {
        return sslContextBuilder;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
