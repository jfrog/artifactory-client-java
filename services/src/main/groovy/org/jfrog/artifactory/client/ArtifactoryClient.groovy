package org.jfrog.artifactory.client

import groovyx.net.http.ContentType
import groovyx.net.http.EncoderRegistry
import groovyx.net.http.RESTClient
import org.apache.http.auth.AuthScope
import org.apache.http.auth.UsernamePasswordCredentials
import org.apache.http.client.CredentialsProvider
import org.apache.http.entity.InputStreamEntity
import org.apache.http.impl.client.DefaultHttpClient
import org.jfrog.artifactory.client.impl.ArtifactoryImpl

/**
 * @author jbaruch
 * @since 12/08/12
 */
public class ArtifactoryClient {
    /**
     *
     * @param url url
     * @param username username
     * @param password password
     * @param apiKey if true, password field will function as apikey.
     * @param connectionTimeout connectionTimeout
     * @param socketTimeout socketTimeout
     * @param proxy proxy
     * @param userAgent userAgent
     * @return {@link ArtifactoryImpl}
     *
     */
    public static Artifactory create(
        String url,
        String username = null,
        String password = null,
        boolean apiKey = false,
        Integer connectionTimeout = null,
        Integer socketTimeout = null,
        ProxyConfig proxy = null,
        userAgent = null,
        ignoreSSLIssues = false) {

        def matcher = url=~/(https?:\/\/[^\/]+)\/+([^\/]*).*/
        if (!matcher) {
            matcher = url=~/(https?:\/\/[^\/]+)\/*()/
            if (!matcher) {
                throw new IllegalArgumentException("Invalid Artifactory URL: ${url}.")
            }
        }
        def client = new RESTClient(matcher[0][1])

        //Set an entity with a length for a stream that has the totalBytes method on it
        def er = new EncoderRegistry() {
            //TODO: [by yl] move totalBytes() to a stream interface that clients can implement
            @Override
            InputStreamEntity encodeStream(Object data, Object contentType) throws UnsupportedEncodingException {
                if (data.metaClass.getMetaMethod('totalBytes')) {
                    InputStreamEntity entity = new InputStreamEntity((InputStream) data, data.totalBytes());
                    if (contentType == null) {
                        contentType = ContentType.BINARY
                    };
                    entity.setContentType(contentType.toString());
                    return entity
                } else if (data instanceof InputStream) {
                    final InputStream stream = (InputStream) data
                    InputStreamEntity entity = new InputStreamEntity(stream, null);
                    if (contentType == null) {
                        contentType = ContentType.BINARY
                    };
                    entity.setContentType(contentType.toString());
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
        if (username && password) {
            if (apiKey) {
                client.headers.'X-JFrog-Art-Api'= password
            } else {
                client.auth.basic username, password
                client.headers.Authorization = "Basic ${"$username:$password".toString().bytes.encodeBase64()}"
            }
        }
        if (connectionTimeout) {
            client.client.params.setParameter("http.connection.timeout", connectionTimeout)
        }
        if (socketTimeout) {
            client.client.params.setParameter("http.socket.timeout", socketTimeout)
        }
        if (proxy) {
            client.setProxy(proxy.host, proxy.port, proxy.scheme)
            if (proxy.user && proxy.password) {
                DefaultHttpClient c = ((DefaultHttpClient)client.client)
                CredentialsProvider credsProvider = c.getCredentialsProvider();
                credsProvider.setCredentials(new AuthScope(proxy.host, proxy.port),
                    new UsernamePasswordCredentials(proxy.user, proxy.password))
            }
        }
        if (ignoreSSLIssues) {
            client.ignoreSSLIssues();
        }

        Artifactory artifactory = new ArtifactoryImpl(client, matcher[0][2])
        artifactory.@username = username
        artifactory
    }

    private static String getUserAgent() {
        Properties prop = new Properties()
        InputStream propStream = ArtifactoryClient.class.classLoader
            .getResource("artifactory.client.release.properties").openStream();
        prop.load(propStream)
        return "artifactory-client-java/" + prop.getProperty("version")
    }

    public static class ProxyConfig {
        /**
         * Host name or IP
         */
        private String host
        /**
         * Port, or -1 for the default port
         */
        private int port
        /**
         * Usually "http" or "https," or <code>null</code> for the default
        */
        private String scheme
        /**
         * Proxy user.
         */
        private String user
        /**
         * Proxy password
         */
        private String password

        ProxyConfig(String host, int port, String scheme, String user, String password) {
            this.host = host
            this.port = port
            this.scheme = scheme
            this.user = user
            this.password = password
        }

        String getHost() {
            return host
        }

        int getPort() {
            return port
        }

        String getScheme() {
            return scheme
        }

        String getUser() {
            return user
        }

        String getPassword() {
            return password
        }
    }
}
