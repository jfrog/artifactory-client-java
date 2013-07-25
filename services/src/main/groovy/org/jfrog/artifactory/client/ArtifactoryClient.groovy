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

    static Artifactory create(String url, String username, String password) {
        def matcher = url =~ /(https?:\/\/[^\/]+)\/+([^\/]*).*/
        if (!matcher) {
            matcher = url =~ /(https?:\/\/[^\/]+)\/*()/
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

        client.auth.basic username, password
        client.headers.'User-Agent' = 'Artifactory-Client/1.0'
        //TODO (JB) remove preemptive auth once RTFACT-5119 is fixed
        client.headers.Authorization = "Basic ${"$username:$password".toString().bytes.encodeBase64()}"
        new ArtifactoryImpl(client, matcher[0][2])
    }
}
