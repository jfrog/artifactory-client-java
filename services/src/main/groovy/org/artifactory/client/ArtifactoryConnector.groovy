package org.artifactory.client

import groovyx.net.http.RESTClient
import org.artifactory.client.impl.ArtifactoryImpl

/**
 * @author jbaruch
 * @since 12/08/12
 */
public class ArtifactoryConnector {

    static Artifactory create(String url, String username, String password) {
        def matcher = url =~ /(https?:\/\/[^\/]+)\/+([^\/]*).*/
        if (!matcher) {
            matcher = url =~ /(https?:\/\/[^\/]+)\/*()/
            if (!matcher) {
                throw new IllegalArgumentException("Invalid ArtifactoryImpl URL: ${url}.")
            }
        }
        def client = new RESTClient(matcher[0][1])
        client.auth.basic username, password
        client.headers.'User-Agent' = 'ArtifactoryImpl-Client/1.0'
        //TODO (JB) remove preemptive auth once RTFACT-5119 is fixed
        client.headers.Authorization = "Basic ${"$username:$password".toString().bytes.encodeBase64()}"
        new ArtifactoryImpl(client, matcher[0][2])
    }
}
