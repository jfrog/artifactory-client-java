package org.artifactory.client

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.databind.ObjectMapper
import groovyx.net.http.RESTClient

import java.text.SimpleDateFormat

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS
import static com.fasterxml.jackson.databind.introspect.VisibilityChecker.Std.defaultInstance
import static groovyx.net.http.ContentType.*

/**
 *
 * @author jbaruch
 * @since 25/07/12
 */
class Artifactory {

    private static final SimpleDateFormat ISO8601_DATE_FORMAT = new SimpleDateFormat("YYYY-MM-dd'T'HH:mm:ss.SSS'Z'")
    private final RESTClient client
    private final String applicationName
    private final ObjectMapper objectMapper

    private Artifactory(RESTClient client, String applicationName) {
        this.client = client
        this.applicationName = applicationName
        objectMapper = new ObjectMapper()
        objectMapper.configure WRITE_DATES_AS_TIMESTAMPS, false
        objectMapper.dateFormat = ISO8601_DATE_FORMAT
        objectMapper.visibilityChecker = defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY)
    }


    static Artifactory create(String host, String applicationName, String username, String password) {
        def client = new RESTClient(host)
        client.auth.basic username, password
        client.headers.'User-Agent' = 'Artifactory-Client/1.0'
        client.headers.Authorization = "Basic ${"$username:$password".toString().bytes.encodeBase64()}" //TODO remove once RTFACT-5119 is fixed
        new Artifactory(client, applicationName)
    }

    //    Storage storage() {
    //        return new Storage(this)
    //    }

    Repositories repositories() {
        new Repositories(this)
    }

    Repositories repository(String repo) {
        new Repositories(this, repo)
    }

    private Reader get(String path, Map query) {
        client.get(path: "/$applicationName$path", query: query, headers: [Accept: JSON], contentType: TEXT).data
    }

    private def putAndPostParams = {path, query, body ->
        [path: "/$applicationName$path", query: query, headers: [Accept: ANY, CONTENT_TYPE: JSON], contentType: TEXT, requestContentType: JSON, body: objectMapper.writeValueAsString(body)]
    }

    private String put(String path, Map query = [:], body) {
        client.put(putAndPostParams(path, query, body)).data.text
    }

    private String post(String path, Map query = [:], body) {
        client.post(putAndPostParams(path, query, body)).data.text
    }

    private String delete(String path, Map query = [:]) {
        client.delete(path: "/$applicationName$path", query: query).data.text
    }

    private <T> T getJson(String path, def target, Map query = [:]) {
        objectMapper.readValue(get(path, query), target) as T
    }

    private String getText(String path, Map query = [:]) {
        get(path, query).text
    }

    private <T> T parseText(String text, def target) {
        objectMapper.readValue text, target
    }
}
