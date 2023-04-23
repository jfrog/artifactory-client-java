package org.jfrog.artifactory.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Map;

/**
 * @author jbaruch
 * @since 25/07/12
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface Artifactory extends ApiInterface, AutoCloseable {

    String API_BASE = "/api";

    String getUri() throws MalformedURLException;

    String getContextName() throws MalformedURLException;

    String getUsername();

    String getUserAgent();

    Repositories repositories();

    RepositoryHandle repository(String repo);

    Searches searches();

    Builds builds();

    Security security();

    Storage storage();

    Plugins plugins();

    ArtifactorySystem system();

    ArtifactoryResponse restCall(ArtifactoryRequest artifactoryRequest) throws IOException;

    InputStream getInputStream(String path) throws IOException;

    InputStream getInputStreamWithHeaders(String path, Map<String, String> headers) throws IOException;

    default public <T> T get(String path, Class<? extends T> object, Class<T> interfaceObject) throws IOException {
        return null;
    }

    default public <T> T get(String path, Class<? extends T> object, Class<T> interfaceObject, Map<String, String> headers) throws IOException {
        return null;
    }

    default public <T> T post(String path, org.apache.http.entity.ContentType contentType, String content,
                              Map<String, String> headers, Class<? extends T> object, Class<T> interfaceObject) throws IOException {
        return null;
    }

    default public <T> T patch(String path, org.apache.http.entity.ContentType contentType, String content,
                               Map<String, String> headers, Class<? extends T> object, Class<T> interfaceObject) throws IOException {
        return null;
    }

    default public <T> T put(String path, org.apache.http.entity.ContentType contentType, String content,
                             Map<String, String> headers, InputStream inputStream, long length, Class<? extends T> object, Class<T> interfaceObject) throws IOException {
        return null;
    }

    default public String delete(String path) throws IOException {
        return null;
    }

    void close();
}
