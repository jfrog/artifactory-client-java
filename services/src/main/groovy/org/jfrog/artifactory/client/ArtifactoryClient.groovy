package org.jfrog.artifactory.client

import groovyx.net.http.ContentType
import groovyx.net.http.EncoderRegistry
import groovyx.net.http.RESTClient
import org.apache.http.entity.InputStreamEntity
import org.jfrog.artifactory.client.impl.ArtifactoryImpl

/**
 * @author jbaruch
 * @since 12/08/12
 */
public class ArtifactoryClient {

    static Artifactory create(
        String url,
        String username = null,
        String password = null,
        Integer connectionTimeoutSecs = null,
        ProxyConfig proxy = null ) {

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
                    InputStreamEntity entity = new InputStreamEntity(stream, stream.available());
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

        client.headers.'User-Agent' = 'Artifactory-Client/1.0'
        //TODO (JB) remove preemptive auth once RTFACT-5119 is fixed
        if (username && password) {
            client.auth.basic username, password
            client.headers.Authorization = "Basic ${"$username:$password".toString().bytes.encodeBase64()}"
        }
        if (connectionTimeoutSecs) {
            client.client.params.setParameter("http.connection.timeout", new Integer(connectionTimeoutSecs))
            client.client.params.setParameter("http.socket.timeout", new Integer(connectionTimeoutSecs))
        }
        if (proxy) {
            client.setProxy(proxy.host, proxy.port, proxy.scheme)
        }
        Artifactory artifactory = new ArtifactoryImpl(client, matcher[0][2])
        artifactory.@username = username
        artifactory
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

        ProxyConfig(String host, int port, String scheme) {
            this.host = host
            this.port = port
            this.scheme = scheme
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
    }
}
