package org.artifactory.client

import groovyx.net.http.HTTPBuilder

import static groovyx.net.http.ContentType.JSON
/**
 *
 * @author jbaruch
 * @since 25/07/12
 */
class Artifactory {

    private HTTPBuilder client

    private Artifactory(HTTPBuilder client) {
        this.client = client
    }

    static Artifactory create(String url, String username, String password) {
        def client = new HTTPBuilder(url)
        client.auth.basic username, password
        client.headers.'User-Agent' = "Artifactory-Client/1.0 ${client.headers.'User-Agent'}"
        new Artifactory(client)
    }

    private def get(String path, Map query = new HashMap()) {
        client.get(path: path, query: query, contentType: JSON) { resp, json ->
            println resp.status

            json.each {  // iterate over JSON 'status' object in the response:
                println it.created_at
                println '  ' + it.text
            }
        }

    }
}
