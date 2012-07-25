package org.artifactory.client

import groovyx.net.http.HTTPBuilder

import static groovyx.net.http.ContentType.TEXT
import static groovyx.net.http.Method.GET

/**
 *
 * @author jbaruch
 * @since 25/07/12
 */
class Artifactory {

    private final HTTPBuilder client
    private final String applicationName

    private Artifactory(HTTPBuilder client, String applicationName) {
        this.client = client
        this.applicationName = applicationName
    }


    static Artifactory create(String host, String applicationName, String username, String password) {
        def client = new HTTPBuilder(host)
        client.auth.basic username, password
        client.headers.'User-Agent' = "Artifactory-Client/1.0"
        new Artifactory(client, applicationName)
    }

    private def get(String path, Map query = new HashMap()) {

        client.request(GET, TEXT) { req ->
            uri.path = "/$applicationName$path"
            uri.query = query
            headers.Accept = 'application/json'

            response.success = { resp, reader ->
                println 'will parse jackson'
            }
        }
    }

}
