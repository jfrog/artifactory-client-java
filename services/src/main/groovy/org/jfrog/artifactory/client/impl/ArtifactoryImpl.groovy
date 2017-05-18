package org.jfrog.artifactory.client.impl

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.util.ISO8601DateFormat
import groovyx.net.http.*
import org.apache.http.HttpResponse
import org.jfrog.artifactory.client.*
import org.jfrog.artifactory.client.impl.jackson.RepositoryMixIn
import org.jfrog.artifactory.client.impl.jackson.RepositorySettingsMixIn
import org.jfrog.artifactory.client.model.Repository
import org.jfrog.artifactory.client.model.repository.settings.RepositorySettings

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
    private final String contextName
    private String username
    private final ObjectMapper objectMapper

    ArtifactoryImpl(RESTClient client, String contextName) {
        this.client = client
        client.parser[JSON] = client.parser[TEXT]
        client.parser[XML] = client.parser[TEXT]

        this.contextName = contextName
        objectMapper = new ObjectMapper()

        objectMapper.addMixIn Repository, RepositoryMixIn
        objectMapper.addMixIn RepositorySettings, RepositorySettingsMixIn

        objectMapper.configure WRITE_DATES_AS_TIMESTAMPS, false
        objectMapper.dateFormat = ISO8601_DATE_FORMAT
        objectMapper.visibilityChecker = defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY)

        objectMapper.configure DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false
        objectMapper.configure SerializationFeature.FAIL_ON_UNWRAPPED_TYPE_IDENTIFIERS, false
        objectMapper.setSerializationInclusion(Include.NON_NULL)
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

    @Override
    String getUsername() {
        return username
    }

    @Override
    String getUserAgent() {
        return client.headers.'User-Agent'
    }

    Repositories repositories() {
        new RepositoriesImpl(this, API_BASE)
    }

    RepositoryHandle repository(String repo) {
        if (!repo) {
            throw new IllegalArgumentException('Repository name is required')
        }
        new RepositoriesImpl(this, API_BASE).repository(repo)
    }

    Searches searches() {
        return new SearchesImpl(this, API_BASE)
    }

    Security security() {
        new SecurityImpl(this, API_BASE)
    }

    Storage storage() {
        new StorageImpl(this)
    }

    Plugins plugins() {
        new PluginsImpl(this, API_BASE)
    }

    @Override
    ArtifactorySystem system() {
        new ArtifactorySystemImpl(this, API_BASE)
    }

    /**
     * Create a REST call to artifactory with a generic request
     * @param request that should be sent to artifactory
     * @return artifactory response as per to the request sent
     */
    @Override
    def <T> T restCall(ArtifactoryRequest request) {

        def responseType = Enum.valueOf(ContentType.class, request.getResponseType().getText())
        def requestType = Enum.valueOf(ContentType.class, request.getRequestType().getText())
        def requestPath = "/${request.getApiUrl()}"

        switch (request.getMethod()) {
            case (ArtifactoryRequest.Method.GET):
                get(requestPath, request.getQueryParams(), responseType, null, request.getHeaders())
                break
            case (ArtifactoryRequest.Method.POST):
                post(requestPath, request.getQueryParams(), responseType, null, requestType, request.getBody(),
                        request.getHeaders())
                break
            case (ArtifactoryRequest.Method.PUT):
                put(requestPath, request.getQueryParams(), responseType, null, requestType, request.getBody(),
                        request.getHeaders())
                break
            case (ArtifactoryRequest.Method.DELETE):
                delete(requestPath, request.getQueryParams(), responseType, null, request.getHeaders())
                break
            default:
                throw new IllegalArgumentException("HTTP method invalid.")
        }
    }

    protected InputStream getInputStream(String path, Map query = [:]) {
        // Otherwise when we leave the response.success block, ensureConsumed will be called to close the stream
        def ret = client.get([path: cleanPath(path), query: query, contentType: BINARY])
        return ret.data
    }

    protected Boolean head(String path) {
        try {
            client.head([path: cleanPath(path)]).success
        } catch (HttpResponseException e) {
            false
        }
    }

    def <T> T get(String path, ContentType responseContentType = ANY, def responseClass = null, Map headers = null) {
        get(path, [:], responseContentType, responseClass, headers)
    }

    def <T> T get(String path, Map query, ContentType responseContentType = ANY,
                  def responseClass = null, Map headers = null) {
        doRest(GET, path, query, responseContentType, responseClass, ANY, null, headers)
    }

    def <T> T delete(String path, Map query = null, ContentType responseContentType = ANY,
                     def responseClass = null, Map addlHeaders = null) {
        doRest(DELETE, path, query, responseContentType, responseClass, TEXT, null, addlHeaders)
    }

    def <T> T post(String path, Map query = null, ContentType responseContentType = ANY,
                   def responseClass = null, ContentType requestContentType = JSON, requestBody = null, Map addlHeaders = null, long contentLength = -1) {
        doRest(POST, path, query, responseContentType, responseClass, requestContentType, requestBody, addlHeaders, contentLength)
    }

    def <T> T put(String path, Map query = null, ContentType responseContentType = ANY,
                  def responseClass = null, ContentType requestContentType = JSON, requestBody = null, Map addlHeaders = null, long contentLength = -1) {
        doRest(PUT, path, query, responseContentType, responseClass, requestContentType, requestBody, addlHeaders, contentLength)
    }


    def cleanPath(path) {
        def fullpath = "${contextName}${path}"
        // URIBuilder will try to "simplify" the apiUrl, so if there's double slashes it'll remove the first part of the uri purposefully
        if (!fullpath.startsWith('/')) {
            fullpath = "/${fullpath}"
        }
        return fullpath
    }

    private def <T> T doRest(Method method, String path, Map query = null, responseType = ANY,
                             def responseClass, ContentType requestContentType = JSON, requestBody = null,
                             Map<String, String> addlHeaders = null, long contentLength = -1) {

        def ret

        Map<String, String> allHeaders = addlHeaders ? addlHeaders.clone() : [:]

        // responseType will be used as the type to parse (XML, JSON, or Reader), it'll also create a header for Accept
        // Artifactory typically only returns one type, so let's ANY align those two. The caller can then do the appropriate thing.
        // Artifactory returns Content-Type: application/vnd.org.jfrog.artifactory.repositories.RepositoryDetailsList+json when ANT is used.
        client.request(method, responseType) { req ->
            // There might be a conflict with the getUri above, so lets be specific and typesafe
            URIBuilder uriBuilder = delegate.uri
            uriBuilder.path = cleanPath(path)
            if (query) {
                uriBuilder.query = query
            }
            headers.putAll(allHeaders)

            if (requestBody) {
                if (requestContentType == JSON) {
                    // Override JSON
                    send JSON, objectMapper.writeValueAsString(requestBody)
                } else {
                    send requestContentType, requestBody
                }
            } else {
                setRequestContentType(requestContentType)
            }

            response.success = { HttpResponse resp, responseBody ->
                ret = parseResponseBody(responseBody, responseType, responseClass)
            }

            response.'404' = { resp ->
                throw new HttpResponseException(resp)
            }

            response.'409' = { resp, responseBody ->
                ret = parseResponseBody(responseBody, responseType, responseClass)
            }
        }
        ret
    }

    private parseResponseBody(responseBody, responseType, responseClass) {
        if (responseBody) {
            if (responseType == BINARY) {
                return responseBody
            }
            if (responseType != JSON || responseClass == String) {
                return responseBody.text
            }
            return parseJson(responseClass, responseBody)
        }
        return null
    }

    private Object parseJson(responseClass, responseBody) {
        responseClass ? objectMapper.readValue(responseBody, responseClass) : objectMapper.readValue(responseBody, Object)
    }

    /**
     * Extra entrypoint into our objectMapper
     */
    protected <T> T parseText(String text, def target) {
        objectMapper.readValue text, target
    }
}
