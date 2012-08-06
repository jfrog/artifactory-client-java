package org.artifactory.client

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.databind.ObjectMapper
import groovyx.net.http.ContentType
import groovyx.net.http.HttpResponseException
import groovyx.net.http.RESTClient

import java.text.SimpleDateFormat

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS
import static com.fasterxml.jackson.databind.introspect.VisibilityChecker.Std.defaultInstance
import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.GET

/**
 *
 * @author jbaruch
 * @since 25/07/12
 */
class Artifactory {

    private static final SimpleDateFormat ISO8601_DATE_FORMAT = new SimpleDateFormat("YYYY-MM-dd'T'HH:mm:ss.SSS'Z'")

    private final RESTClient client
    private final String contextName
    private final ObjectMapper objectMapper

    private Artifactory(RESTClient client, String contextName) {
        this.client = client
        this.contextName = contextName
        objectMapper = new ObjectMapper()
        objectMapper.configure WRITE_DATES_AS_TIMESTAMPS, false
        objectMapper.dateFormat = ISO8601_DATE_FORMAT
        objectMapper.visibilityChecker = defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY)
        //        client.parser."$JSON" = {HttpResponse resp ->
        //            objectMapper.readValue(resp.entity.content, Object) //TODO (JB) seem unsolvable, when this code runs I don't know the type yet. If only JSON has root element!
        //        }
    }

    String getUri() {
        return client.uri.toString()
    }

    String getContextName() {
        return contextName
    }

    static Artifactory create(String url, String username, String password) {
        def matcher = url =~ /(https?:\/\/[^\/]+)\/+([^\/]*).*/
        if (!matcher) {
            matcher = url =~ /(https?:\/\/[^\/]+)\/*()/
            if (!matcher) {
                throw new IllegalArgumentException("Invalid Artifactory URL: ${url}.")
            }
        }
        def client = new RESTClient(matcher[0][1])
        client.auth.basic username, password
        client.headers.'User-Agent' = 'Artifactory-Client/1.0'
        //TODO (JB) remove preemptive auth once RTFACT-5119 is fixed
        client.headers.Authorization = "Basic ${"$username:$password".toString().bytes.encodeBase64()}"
        new Artifactory(client, matcher[0][2])
    }

    Repositories repositories() {
        new Repositories(this)
    }

    Repositories.RepositoryHandler repository(String repo) {
        new Repositories(this).repository(repo)
    }

    Searches searches() {
        return new Searches(this)
    }

    private Reader get(String path, Map query, ContentType contentType = JSON, ContentType requestContentType = TEXT) {
        client.get(path: "/$contextName$path", query: query,
                headers: [Accept: contentType], contentType: requestContentType).data
    }

    private def getSlurper(String path, Map query) throws HttpResponseException {
        def ret
        client.request(GET, JSON) { req ->
            uri.path = "/$contextName$path"
            uri.query = query

            response.success = { resp, slurper ->
                ret = slurper
            }

            response.'404' = { resp ->
                throw new HttpResponseException(resp)
            }
        }
        ret
    }

    private def putAndPostJsonParams = {path, query, body ->
        [path: "/$contextName$path", query: query, headers:
                [Accept: ANY, CONTENT_TYPE: JSON], contentType: TEXT,
                requestContentType: JSON, body: objectMapper.writeValueAsString(body)]
    }

    def <T> T put(String path, Map query = [:], body, Class responseType = String, ContentType requestContentType = JSON) {
        Map params
        if (requestContentType == JSON) {
            params = putAndPostJsonParams(path, query, body)
        } else {
            params = [path: "/$contextName$path", query: query,
                    headers: [Accept: ANY, CONTENT_TYPE: requestContentType],
                    contentType: TEXT, requestContentType: requestContentType, body: body]

        }
        def data = client.put(params).data
        //TODO (JB) need to try once more to replace this stuff with good parser that uses Jackson(if possible- see above)
        if (responseType == String) {
            data.text
        } else {
            objectMapper.readValue(data as Reader, responseType)
        }
    }

    private String post(String path, Map query = [:], body) {
        client.post(putAndPostJsonParams(path, query, body)).data.text
    }

    private String delete(String path, Map query = [:]) {
        client.delete(path: "/$contextName$path", query: query).data.text
    }

    private <T> T getJson(String path, def target, Map query = [:]) {
        objectMapper.readValue(get(path, query), target) as T
    }

    private String getText(String path, Map query = [:]) {
        get(path, query).text
    }

    private InputStream getInputStream(String path, Map query = [:]) {
        client.get([path: "/$contextName$path", query: query, contentType: BINARY]).data
    }

    private <T> T parseText(String text, def target) {
        objectMapper.readValue text, target
    }
}
