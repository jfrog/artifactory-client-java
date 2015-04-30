package org.jfrog.artifactory.client;

import java.util.Map;

/**
 * @author Aviad Shikloshi
 */
public interface ArtifactoryRequest {

    enum Method { GET, POST, PUT, DELETE }

    ArtifactoryRequest method(Method method);

    ArtifactoryRequest apiUrl(String apiUrl);
    ArtifactoryRequest addHeader(String key, String value);
    ArtifactoryRequest addQueryParam(String key, String value);
    ArtifactoryRequest requestBody(Map<String, Object> body);
    ArtifactoryRequest responseType(ContentType responseType);
    ArtifactoryRequest requestType(ContentType requestType);

    Map<String, String> getHeaders();
    Map<String, String> getQueryParams();
    ContentType getRequestType();
    ContentType getResponseType();
    String getApiUrl();


    enum ContentType {
        JSON("JSON"),
        TEXT("TEXT") ;
        private String text;
        ContentType(String text){
            this.text = text;
        }
        String getText(){
            return this.text;
        }
    }

}
