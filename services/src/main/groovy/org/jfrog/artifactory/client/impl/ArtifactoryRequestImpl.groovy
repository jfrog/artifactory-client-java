package org.jfrog.artifactory.client.impl

import org.jfrog.artifactory.client.ArtifactoryRequest
import org.jfrog.artifactory.client.ArtifactoryRequest.ContentType
import org.jfrog.artifactory.client.ArtifactoryRequest.Method

/**
 * @author Aviad Shikloshi
 */
class ArtifactoryRequestImpl implements ArtifactoryRequest {

    private def method
    private def apiUrl
    private def queryParams
    private def headers
    private def body
    private def responseType
    private def requestType

    ArtifactoryRequestImpl(){
        requestType = ContentType.ANY
        responseType = ContentType.ANY
        queryParams = new HashMap<String, String>()
        headers = new HashMap<String, String>()
    }

    ArtifactoryRequestImpl apiUrl(String url){
        this.apiUrl = url
        this
    }

    ArtifactoryRequestImpl method(Method method) {
        this.method = method
        this
    }

    ArtifactoryRequestImpl setQueryParams(Map<String, String> queryParams) {
        this.queryParams = queryParams
        this
    }

    ArtifactoryRequestImpl setHeaders(Map<String, String> headers) {
        this.headers = headers
        this
    }

    ArtifactoryRequestImpl addHeader(String key, String value){
        headers.put(key, value)
        this
    }

    ArtifactoryRequestImpl addQueryParam(String key, String value){
        queryParams.put(key, value)
        this
    }

    def <T> ArtifactoryRequestImpl requestBody(T body) {
        this.body = body
        this
    }

    ArtifactoryRequestImpl responseType(ContentType responseType){
        this.responseType = responseType
        this
    }

    ArtifactoryRequestImpl requestType(ContentType requestType){
        this.requestType = requestType
        this
    }

    Method getMethod() {
        this.method
    }

    Map<String, String> getQueryParams() {
        this.queryParams
    }

    Map<String, String> getHeaders() {
        this.headers
    }

    def <T> T getBody() {
        this.body
    }

    ContentType getResponseType() {
        responseType
    }

    String getApiUrl() {
        apiUrl
    }

    ContentType getRequestType() {
        requestType
    }
}
