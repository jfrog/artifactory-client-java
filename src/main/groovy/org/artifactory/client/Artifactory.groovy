package org.artifactory.client

import com.fasterxml.jackson.databind.ObjectMapper
import groovyx.net.http.HTTPBuilder

import java.text.SimpleDateFormat

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS
import static com.fasterxml.jackson.databind.introspect.VisibilityChecker.Std.defaultInstance
import static groovyx.net.http.ContentType.TEXT

/**
 *
 * @author jbaruch
 * @since 25/07/12
 */
class Artifactory {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("YYYY-MM-dd'T'HH:mm:ss.SSS'Z'")
    private final HTTPBuilder client
    private final String applicationName
    private final ObjectMapper objectMapper

    private Artifactory(HTTPBuilder client, String applicationName) {
        this.client = client
        this.applicationName = applicationName
        objectMapper = new ObjectMapper()
        objectMapper.configure WRITE_DATES_AS_TIMESTAMPS, false
        objectMapper.dateFormat = DATE_FORMAT
        objectMapper.visibilityChecker = defaultInstance().withFieldVisibility(ANY)
    }


    static Artifactory create(String host, String applicationName, String username, String password) {
        def client = new HTTPBuilder(host)
        client.auth.basic username, password
        client.headers.'User-Agent' = 'Artifactory-Client/1.0'
        new Artifactory(client, applicationName)
    }

    Storage storage() {
        return new Storage(this)
    }

    Repositories repositories() {
        return new Repositories(this)
    }

    private <T> T getJson(String path, def target, Map query = new HashMap()) {
        objectMapper.readValue(get(path, query), target) as T
    }

    private String getText(String path, Map query = new HashMap()) {
        get(path, query).text

    }

    private Reader get(String path, query) {
        client.get(path: "/$applicationName$path", query: query, headers: [Accept: 'application/json'], contentType: TEXT)
    }

    private <T> T parseText(String text, def target) {
        objectMapper.readValue text, target
    }
}
