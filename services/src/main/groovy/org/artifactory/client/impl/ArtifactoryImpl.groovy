package org.artifactory.client.impl

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.util.ISO8601DateFormat
import groovyx.net.http.ContentType
import groovyx.net.http.HttpResponseException
import groovyx.net.http.Method
import groovyx.net.http.RESTClient
import org.apache.http.HttpResponse
import org.apache.http.protocol.HTTP
import org.artifactory.client.*

import java.text.DateFormat

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS
import static com.fasterxml.jackson.databind.introspect.VisibilityChecker.Std.defaultInstance
import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*

/**
 *
 * @author jbaruch
 * @since 25/07/12
 */
class ArtifactoryImpl implements Artifactory {

    private static final DateFormat ISO8601_DATE_FORMAT = ISO8601DateFormat.newInstance()

    final RESTClient client
    def final originalJsonParser
    def final originalTextParser
    private final String contextName
    private final ObjectMapper objectMapper

    ArtifactoryImpl(RESTClient client, String contextName) {
        this.client = client
        originalJsonParser = client.parser.getAt(JSON)
        originalTextParser = client.parser.getAt(TEXT)
        this.contextName = contextName
        objectMapper = new ObjectMapper()
        objectMapper.configure WRITE_DATES_AS_TIMESTAMPS, false
        objectMapper.dateFormat = ISO8601_DATE_FORMAT
        objectMapper.visibilityChecker = defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY)
        //        client.parser."$JSON" = {HttpResponse resp ->
        //            objectMapper.readValue(resp.entity.content, Object) //TODO (JB) seem unsolvable, when this code runs I don't know the type yet. If only JSON had root element!
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

    Plugins plugins(){
        new PluginsImpl(this)
    }

    @Override
    ArtifactorySystem system() {
        new ArtifactorySystemImpl(this)
    }

    protected InputStream getInputStream(String path, Map query = [:]) {
        // Otherwise when we leave the response.success block, ensureConsumed will be called to close the stream
        def ret = client.get([path: "/$contextName$path", query: query, contentType: BINARY])
        return ret.data
//        rest(GET, path, query, BINARY, null, ANY, null, null)
    }

    def <T> T get(String path, ContentType responseContentType = ANY, def responseClass = null, Map headers = null) {
        get(path, [:], responseContentType, responseClass, headers)
    }

    def <T> T get(String path, Map query, ContentType responseContentType = ANY, def responseClass = null, Map headers = null) {
        rest(GET, path, query, responseContentType, responseClass, ANY, null, headers)
    }

    def <T> T delete(String path, Map query = null, ContentType responseContentType = ANY, def responseClass = null, Map addlHeaders = null) {
        rest(DELETE, path, query, responseContentType, responseClass, ContentType.TEXT, null, addlHeaders)
    }

    def <T> T post(String path, Map query = null, ContentType responseContentType = ANY, def responseClass = null, ContentType requestContentType = JSON, requestBody = null, Map addlHeaders = null) {
        rest(POST, path, query, responseContentType, responseClass, requestContentType, requestBody, addlHeaders)
    }

    def <T> T put(String path, Map query = null, ContentType responseContentType = ANY, def responseClass = null, ContentType requestContentType = JSON, requestBody = null, Map addlHeaders = null) {
        rest(PUT, path, query, responseContentType, responseClass, requestContentType, requestBody, addlHeaders)
    }

    /**
     *
     * @param method
     * @param path
     * @param query
     * @param responseType Header and parsing type for response
     * @param responseClass If responseType is JSON, a class to have the object mapped to can be provided. Not necessarily a class, it could be a TypeReference, hence it's not the same as T
     * @param requestContentType
     * @param requestBody
     * @param addlHeaders
     * @return
     */
    private def <T> T rest(Method method, String path, Map query = null, responseType = ANY, def responseClass, ContentType requestContentType = JSON, requestBody = null, Map addlHeaders = null ) {
        def originalParser
        try {
            // Let us send one thing to the server and parse it differently. But we have to careful to restore it.
            originalParser = client.parser.getAt(responseType)

            // Change the JSON parser on the fly, not thread safe since the responseClass we're using is locked to this call's argument
            if (responseClass == String) {
                // They just want us to leave the response alone
                client.parser.putAt(responseType, originalTextParser)
            } else if (responseType == JSON && responseClass) {
                client.parser.putAt(JSON) { org.apache.http.HttpResponse resp ->
                    InputStream is = resp.entity.content
                    return objectMapper.readValue(is, responseClass) as T
                }
            }

            restWrapped(method, path, query, responseType, responseClass, requestContentType, requestBody, addlHeaders)
        } finally {
            client.parser.putAt(responseType, originalParser)
        }
    }

    private def <T> T restWrapped(Method method, String path, Map query = null, responseType = ANY, def responseClass, ContentType requestContentType = JSON, requestBody = null, Map addlHeaders = null ) {
        def ret

        //TODO Ensure requestContentType is not null
        def allHeaders = addlHeaders?addlHeaders.clone():[:]

        // responseType will be used as the type to parse (XML, JSON, or Reader), it'll also create a header for Accept
        // Artifactory typically only returns one type, so let's ANY align those two. The caller can then do the appropriate thing.
        // Artifactory returns Content-Type: application/vnd.org.jfrog.artifactory.repositories.RepositoryDetailsList+json when ANT is used.
        client.request(method, responseType) { req ->
            uri.path = "/$contextName$path"
            if (query) {
                uri.query = query
            }
            headers.putAll(allHeaders)

            if(requestBody) {
                if (requestContentType == JSON) {
                    // Override JSON
                    send JSON, objectMapper.writeValueAsString(requestBody)
                } else {
                    send requestContentType, requestBody
                }
            } else {
                setRequestContentType(requestContentType)
            }

            response.success = { HttpResponse resp, slurped ->
                if ( responseClass==String || responseType == TEXT) {
                    // we overrode the parser to be just text, but oddly it returns an InputStreamReader instead of a String
                    ret = slurped.text
                } else {
                    ret = slurped // Will be type according to responseType
                }
            }

            response.'404' = { resp ->
                throw new HttpResponseException(resp)
            }
        }
        ret
    }

    /**
     * Extra entrypoint into our objectMapper
     */
    protected <T> T parseText(String text, def target) {
        objectMapper.readValue text, target
    }
}
