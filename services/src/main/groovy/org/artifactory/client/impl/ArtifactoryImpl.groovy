package org.artifactory.client.impl

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.util.ISO8601DateFormat
import groovyx.net.http.ContentType
import groovyx.net.http.HttpResponseException
import groovyx.net.http.RESTClient
import org.artifactory.client.*

import java.text.DateFormat

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS
import static com.fasterxml.jackson.databind.introspect.VisibilityChecker.Std.defaultInstance
import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.GET

/**
 *
 * @author jbaruch
 * @since 25/07/12
 */
class ArtifactoryImpl implements Artifactory {

    private static final DateFormat ISO8601_DATE_FORMAT = ISO8601DateFormat.newInstance()

    final RESTClient client
    private final String contextName
    private final ObjectMapper objectMapper

    ArtifactoryImpl(RESTClient client, String contextName) {
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

    void close() {
        client.shutdown()
    }

    String getUri() {
        return client.uri.toString()
    }

    String getContextName() {
        return contextName
    }

    Repositories repositories() {
        new RepositoriesImpl(this)
    }

    RepositoryHandle repository(String repo) {
        new RepositoriesImpl(this).repository(repo)
    }

    Searches searches() {
        return new SearchesImpl(this)
    }

    Security security() {
        new SecurityImpl(this)
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

    private def putAndPostJsonParams = { path, query, body ->
        [path: "/$contextName$path", query: query, headers:
                [Accept: ANY, CONTENT_TYPE: JSON], contentType: TEXT,
                requestContentType: JSON, body: objectMapper.writeValueAsString(body)]
    }

    private def <T> T put(String path, Map query = [:], Class responseType = null) {
        put(path, query, null, responseType, ANY)
    }

    private def <T> T put(String path, Map query = [:], body, Class responseType = String, ContentType requestContentType = JSON) {
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
        if (responseType == null) {
            null
        } else if (responseType == String) {
            data.text
        } else {
            objectMapper.readValue(data as Reader, responseType)
        }
    }

    protected String post(String path, Map query = [:], body) {
        client.post(putAndPostJsonParams(path, query, body)).data?.text
    }

    protected String delete(String path, Map query = [:]) {
        client.delete(path: "/$contextName$path", query: query).data?.text
    }

    protected <T> T getJson(String path, def target, Map query = [:]) {
        objectMapper.readValue(get(path, query), target) as T
    }

    protected String getText(String path, Map query = [:]) {
        get(path, query).text
    }

    protected InputStream getInputStream(String path, Map query = [:]) {
        client.get([path: "/$contextName$path", query: query, contentType: BINARY]).data
    }

    protected <T> T parseText(String text, def target) {
        objectMapper.readValue text, target
    }
}
