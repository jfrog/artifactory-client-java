package org.jfrog.artifactory.client

import groovyx.net.http.ContentType
import groovyx.net.http.EncoderRegistry
import groovyx.net.http.RESTClient
import org.apache.commons.lang.StringUtils
import org.apache.http.HttpHost
import org.apache.http.auth.AuthScope
import org.apache.http.auth.UsernamePasswordCredentials
import org.apache.http.client.CredentialsProvider
import org.apache.http.client.config.RequestConfig
import org.apache.http.client.utils.URIBuilder
import org.apache.http.conn.HttpClientConnectionManager
import org.apache.http.entity.InputStreamEntity
import org.apache.http.impl.client.BasicCredentialsProvider
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.impl.client.HttpClients
import org.apache.http.impl.conn.DefaultProxyRoutePlanner
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager
import org.jfrog.artifactory.client.impl.ArtifactoryImpl

import java.text.MessageFormat

/**
 * @author jbaruch
 * @author Lior Hasson
 */
public class ArtifactoryClientBuilder {

     String url
     String username
     String password
     Integer connectionTimeout
     Integer socketTimeout
     ArtifactoryClient.ProxyConfig proxy
     String userAgent
     boolean ignoreSSLIssues
     String accessToken
     HttpClientConnectionManager cm

    protected ArtifactoryClientBuilder() {
        super()
    }

    public static ArtifactoryClientBuilder create() {
        return new ArtifactoryClientBuilder()
    }

    ArtifactoryClientBuilder setUrl(String url) {
        this.url = url
        return this
    }

    ArtifactoryClientBuilder setUsername(String username) {
        this.username = username
        return this
    }

    ArtifactoryClientBuilder setPassword(String password) {
        this.password = password
        return this
    }

    ArtifactoryClientBuilder setConnectionTimeout(Integer connectionTimeout) {
        this.connectionTimeout = connectionTimeout
        return this
    }

    ArtifactoryClientBuilder setSocketTimeout(Integer socketTimeout) {
        this.socketTimeout = socketTimeout
        return this
    }

    ArtifactoryClientBuilder setProxy(ArtifactoryClient.ProxyConfig proxy) {
        this.proxy = proxy
        return this
    }

    ArtifactoryClientBuilder setUserAgent(String userAgent) {
        this.userAgent = userAgent
        return this
    }

    ArtifactoryClientBuilder setIgnoreSSLIssues(boolean ignoreSSLIssues) {
        this.ignoreSSLIssues = ignoreSSLIssues
        return this
    }

    ArtifactoryClientBuilder setAccessToken(String accessToken) {
        this.accessToken = accessToken
        return this
    }

    ArtifactoryClientBuilder setConnectionManager(HttpClientConnectionManager connectionManager) {
        this.cm = connectionManager
        return this
    }

    protected HttpClientBuilder createClientBuilder(URI uri) {
        HttpClientBuilder clientBuilder = HttpClients.custom()
        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom()

        CredentialsProvider credentialsProvider = addBasicAuth(uri)
        credentialsProvider = addProxyRoute(clientBuilder, credentialsProvider)

        clientBuilder.setDefaultCredentialsProvider(credentialsProvider)

        if (connectionTimeout) {
            requestConfigBuilder.setConnectTimeout(connectionTimeout)
        }
        if (socketTimeout) {
            requestConfigBuilder.setSocketTimeout(socketTimeout)
        }

        if (cm) {
            clientBuilder.setConnectionManager(cm)
        } else {
           /* This implementation will create no more than than 2 concurrent connections per given route
            * and no more 20 connections in total.
            */
            clientBuilder.setConnectionManager(new PoolingHttpClientConnectionManager())
        }

        clientBuilder.setDefaultRequestConfig(requestConfigBuilder.build())

        return clientBuilder
    }

    protected RESTClient createClient(URI uri) {
        HttpClientBuilder clientBuilder = createClientBuilder(uri)

        RESTClient restClient = new RESTClient()
        restClient.setUri(MessageFormat.format("{0}://{1}", uri.getScheme(), uri.getAuthority()))
        restClient.setClient(clientBuilder.build())

        return restClient
    }

    protected CredentialsProvider addBasicAuth(URI uri) {
        CredentialsProvider credsProvider = new BasicCredentialsProvider()
        if (username && password) {
            credsProvider.setCredentials(
                    new AuthScope(uri.getHost(), uri.getPort()),
                    new UsernamePasswordCredentials(username, password)
            )
        }

        return credsProvider
    }

    protected CredentialsProvider addProxyRoute(HttpClientBuilder clientBuilder, CredentialsProvider credentialsProvider) {
        if (proxy) {
            HttpHost proxyHost = new HttpHost(proxy.host, proxy.port, proxy.scheme)
            DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxyHost)
            clientBuilder.setRoutePlanner(routePlanner)

            if (proxy.user && proxy.password) {
                credentialsProvider.setCredentials(new AuthScope(proxy.host, proxy.port),
                        new UsernamePasswordCredentials(proxy.user, proxy.password))
            }
        }

        return credentialsProvider
    }

    public Artifactory build() {
        URI uri
        try {
            uri = new URIBuilder(url).build()
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid Artifactory URL: ${url}.", e)
        }

        def client = createClient(uri)

        //Set an entity with a length for a stream that has the totalBytes method on it
        def er = new EncoderRegistry() {
            //TODO: [by yl] move totalBytes() to a stream interface that clients can implement
            @Override
            InputStreamEntity encodeStream(Object data, Object contentType) throws UnsupportedEncodingException {
                if (data.metaClass.getMetaMethod('totalBytes')) {
                    InputStreamEntity entity = new InputStreamEntity((InputStream) data, data.totalBytes())
                    if (contentType == null) {
                        contentType = ContentType.BINARY
                    }
                    entity.setContentType(contentType.toString())
                    return entity
                } else if (data instanceof InputStream) {
                    final InputStream stream = (InputStream) data
                    InputStreamEntity entity = new InputStreamEntity(stream, null)
                    if (contentType == null) {
                        contentType = ContentType.BINARY
                    }
                    entity.setContentType(contentType.toString())
                    return entity
                } else {
                    return super.encodeStream(data, contentType)
                }
            }
        }
        client.encoders = er

        if (!userAgent) {
            userAgent = getUserAgent()
        }
        client.headers.'User-Agent' = userAgent
        //TODO (JB) remove preemptive auth once RTFACT-5119 is fixed
        if (accessToken) {
            client.headers.Authorization = "Bearer $accessToken"
        } else if (username && password) {
            client.headers.Authorization = "Basic ${"$username:$password".toString().bytes.encodeBase64()}"
        }

        if (ignoreSSLIssues) {
            client.ignoreSSLIssues()
        }

        Artifactory artifactory = new ArtifactoryImpl(client, StringUtils.strip(uri.getPath(), "/"))
        artifactory.@username = username
        artifactory
    }

    private static String getUserAgent() {
        Properties prop = new Properties()
        InputStream propStream = ArtifactoryClientBuilder.class.classLoader
                .getResource("artifactory.client.release.properties").openStream()
        prop.load(propStream)
        return "artifactory-client-java/" + prop.getProperty("version")
    }
}
