/* Copyright (c) 2013, Yahoo! Inc.  All rights reserved. */
package org.jfrog.artifactory.client.ning

import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*

import java.util.Collection;
import java.util.Map;

import groovyx.net.http.ContentType
import groovyx.net.http.Method
import groovyx.net.http.RESTClient
import net.sf.json.JSON

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import com.fasterxml.jackson.databind.ObjectMapper
import com.ning.http.client.AsyncHttpClient
import com.ning.http.client.Cookie
import com.ning.http.client.Request
import com.ning.http.client.Response
import com.ning.http.client.AsyncHttpClient.BoundRequestBuilder

/**
 * This class simply extends {@link ArtifactoryImpl} to keep existing API
 * but able to provide a different HTTP Client.
 * Also allows to add Cookie to the requests.
 *
 * @author charlesk
 *
 */
public class ArtifactoryNingClientImpl extends org.jfrog.artifactory.client.impl.ArtifactoryImpl {
    private static final Logger log = LoggerFactory.getLogger(ArtifactoryNingClientImpl.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final AsyncHttpClient ningHttpClient;
    private final String url;
    private final NingRequest ningRequest;

    public ArtifactoryNingClientImpl(final AsyncHttpClient ningHttpClient, final String context, final String host, final NingRequest ningRequest){
        //Setup a mock client but won't be used.
        super(new RESTClient(), null);
        this.ningRequest = ningRequest;
        this.ningHttpClient = ningHttpClient;
        this.url = host + "/" + context;
    }

    def <T> T get(String path, ContentType responseContentType = ANY, def responseClass = null, Map headers = null) {
        get(path, [:], responseContentType, responseClass, headers)
    }

    def <T> T get(String path, Map query, ContentType responseContentType = ANY, def responseClass = null, Map headers = null) {
        rest(GET, path, query, responseContentType, responseClass, ANY, null, headers)
    }

    def <T> T delete(String path, Map query = null, ContentType responseContentType = ANY, def responseClass = null, Map addlHeaders = null) {
        rest(DELETE, path, query, responseContentType, responseClass, TEXT, null, addlHeaders)
    }

    def <T> T post(String path, Map query = null, ContentType responseContentType = ANY, def responseClass = null, ContentType requestContentType = JSON, requestBody = null, Map addlHeaders = null, long contentLength = -1) {
        rest(POST, path, query, responseContentType, responseClass, requestContentType, requestBody, addlHeaders, contentLength)
    }

    def <T> T put(String path, Map query = null, ContentType responseContentType = ANY, def responseClass = null, ContentType requestContentType = JSON, requestBody = null, Map addlHeaders = null, long contentLength = -1) {
        rest(PUT, path, query, responseContentType, responseClass, requestContentType, requestBody, addlHeaders, contentLength)
    }

    /**
     * Used by doDownload only.  Expects the doDownload method to close the inputstream.
     * @param path
     * @return
     */
    protected InputStream getInputStream(String path, Map query = [:]) {
        String finalUrl = this.url + path;
        log.debug("finalUrl: {}", finalUrl);
        BoundRequestBuilder requestBuilder = this.ningHttpClient.prepareGet(finalUrl);
        Request request = customizeRequest(requestBuilder, null, query);
        return handleResponse(ningHttpClient.executeRequest(request).get(), finalUrl, false);
    }

    private def <T> T rest(Method method, String path, Map query = null, responseType = ANY, def responseClass, ContentType requestContentType = JSON, requestBody = null, Map addlHeaders = null, long contentLength = -1 ) {
        log.debug("Method: {}, Path: {}", method, path);
        String finalUrl = this.url + path;
        log.debug("finalUrl: {}", finalUrl);
        BoundRequestBuilder requestBuilder;
        switch (method){
            case POST:
                requestBuilder = ningHttpClient.preparePost(finalUrl).setBody(requestBody);
                break;
            case PUT:
                requestBuilder = ningHttpClient.preparePut(finalUrl).setBody(requestBody);
                break;
            case DELETE:
                requestBuilder = ningHttpClient.prepareDelete(finalUrl);
                break;
            default:
                requestBuilder = ningHttpClient.prepareGet(finalUrl);
        }
        Request request = customizeRequest(requestBuilder, addlHeaders, query);
        InputStream inputStream = handleResponse(ningHttpClient.executeRequest(request).get(), finalUrl, false);
        if (inputStream != null) {
            try {
                //This may puke on IOException, JsonParseException, JsonMappingException but we will let the caller deal with it.
                return (T) objectMapper.readValue(inputStream, responseClass);
            } finally {
                inputStream.close();
            }
        }
        return null;
    }

    private Request customizeRequest(BoundRequestBuilder boundRequestBuilder, Map addlHeaders, Map query){
        BoundRequestBuilder customRequestBuilder = this.ningRequest.getBoundRequestBuilder(boundRequestBuilder);
        if (customRequestBuilder==null){
            customRequestBuilder = boundRequestBuilder;
        }
        if (addlHeaders !=null){
            for (e in addlHeaders){
                customRequestBuilder.setHeader(e.key, (String) e.value);
            }
        }
        if (query !=null){
            for (e in query){
                customRequestBuilder.addQueryParameter(e.key, (String) e.value);
            }
        }
        return customRequestBuilder.build();
    }

    private InputStream handleResponse(Response response, String url, boolean isBinary) throws IOException{
        int statusCode = response.getStatusCode();
        log.debug("Artifact path: " + url + " Response status code: " + response.getStatusCode()
                + " status text: " + response.getStatusText() + " body: "
                + ((isBinary) ? " binary " : response.getResponseBody()));
        switch (statusCode) {
            // 200 series are ok.
            case 200:
            case 201:
            case 202:
                return response.getResponseBodyAsStream();
            case 404:
            // not found 404
            case 401:
            // auth failures 401/403
            case 403:
            //Throw apache http client's HttpResponseException since the API expects that.
                throw new org.apache.http.client.HttpResponseException(statusCode, response.getStatusText());
            default:
                //For all other types, throw IOException.
                throw new IOException("Response was " + statusCode
                + " message '" + response.getStatusText() + "'");
        }
    }
}
