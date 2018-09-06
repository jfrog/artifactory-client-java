package org.jfrog.artifactory.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Map;

/**
 * ArtifactoryRequest object that can handle all types of APIs in artifactory
 *
 * @author Aviad Shikloshi
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface ArtifactoryRequest {

    enum Method { GET, POST, PUT, DELETE, PATCH, OPTIONS }

    ArtifactoryRequest method(Method method);
    ArtifactoryRequest apiUrl(String apiUrl);
    ArtifactoryRequest addHeader(String key, String value);
    ArtifactoryRequest addQueryParam(String key, String value);
    <T> ArtifactoryRequest requestBody(T body);
    ArtifactoryRequest responseType(ContentType responseType);
    ArtifactoryRequest requestType(ContentType requestType);

    Method getMethod();
    <T> T getBody();
    Map<String, String> getHeaders();
    Map<String, String> getQueryParams();
    ContentType getRequestType();
    ContentType getResponseType();
    String getApiUrl();

    enum ContentType {
        JSON,
        JOSE,
        JOSE_JSON,
        TEXT,
        URLENC,
        ANY,
        XML,
        YAML
    }
}
