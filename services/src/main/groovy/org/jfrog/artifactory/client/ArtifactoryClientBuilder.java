package org.jfrog.artifactory.client;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.jfrog.artifactory.client.httpClient.http.HttpBuilderBase;
import org.jfrog.artifactory.client.impl.ArtifactoryImpl;
import org.jfrog.artifactory.client.impl.util.ArtifactoryHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

/**
 * @author jbaruch
 * @author Lior Hasson
 * @author Alexei Vainshtein
 */
public class ArtifactoryClientBuilder {

    private String url;
    private String username;
    private String password;
    private Integer connectionTimeout;
    private Integer socketTimeout;
    private ProxyConfig proxy;
    private String userAgent;
    private boolean ignoreSSLIssues;
    private String accessToken;
    private String apiKey;

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

    public ArtifactoryClientBuilder setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public ArtifactoryClientBuilder setApiKey(String apiKey) {
        this.apiKey = apiKey;
        return this;
    }


    private CloseableHttpClient createClientBuilder(URI uri) {
        ArtifactoryHttpClient artifactoryHttpClient = new ArtifactoryHttpClient();
        artifactoryHttpClient.hostFromUrl(uri.toString());

        if (StringUtils.isEmpty(accessToken) && StringUtils.isEmpty(apiKey)) {
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

        artifactoryHttpClient.trustSelfSignCert(!ignoreSSLIssues);
        return artifactoryHttpClient.build();
    }

    public Artifactory build() {
        URI uri;
        try {
            uri = new URIBuilder(url).build();
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid Artifactory URL: " + url + ".", e);
        }

        if (StringUtils.isBlank(userAgent)) {
            try {
                userAgent = getUserAgent();
            } catch (IOException e) {
                userAgent = "artifactory-client-java";
            }
        }

        CloseableHttpClient closeableHttpClient = createClientBuilder(uri);

        return new ArtifactoryImpl(closeableHttpClient, url, userAgent, username, accessToken, apiKey);
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

    public String getAccessToken() {
        return accessToken;
    }
}
