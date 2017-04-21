/* Copyright 2013 Yahoo! Inc. Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or 
 * agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR 
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and 
 * limitations under the License. See accompanying LICENSE file.  */
package org.jfrog.artifactory.client.ning

import com.fasterxml.jackson.databind.ObjectMapper
import com.ning.http.client.AsyncHttpClient
import com.ning.http.client.AsyncHttpClient.BoundRequestBuilder
import com.ning.http.client.generators.InputStreamBodyGenerator
import com.ning.http.client.Request
import com.ning.http.client.Response
import groovyx.net.http.ContentType
import groovyx.net.http.Method
import groovyx.net.http.RESTClient
import net.sf.json.JSON
import org.apache.http.client.HttpResponseException
import org.jfrog.artifactory.client.impl.ArtifactoryImpl
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static groovyx.net.http.ContentType.ANY
import static groovyx.net.http.ContentType.TEXT
import static groovyx.net.http.Method.*

/**
 * This class simply extends {@link ArtifactoryImpl} to keep existing API
 * but able to provide a different HTTP Client.
 * Also allows to add Cookie to the requests.
 *
 * @author charlesk
 *
 */
public class ArtifactoryNingClientImpl extends ArtifactoryImpl {
    private static final Logger log = LoggerFactory.getLogger(ArtifactoryNingClientImpl.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final AsyncHttpClient ningHttpClient;
    private final String url;
    private final NingRequest ningRequest;

    public ArtifactoryNingClientImpl(
            final AsyncHttpClient ningHttpClient,
            final String context, final String host, final NingRequest ningRequest) {
        //Setup a mock client but won't be used.
        super(new RESTClient(), null);
        this.ningRequest = ningRequest;
        this.ningHttpClient = ningHttpClient;
        this.url = host + "/" + context;
    }

    def <T> T get(String path, ContentType responseContentType = ANY, def responseClass = null, Map headers = null) {
        get(path, [:], responseContentType, responseClass, headers)
    }

    def <T> T get(String path, Map query, ContentType responseContentType = ANY,
                  def responseClass = null, Map headers = null) {
        rest(GET, path, query, responseContentType, responseClass, ANY, null, headers)
    }

    def <T> T delete(String path, Map query = null, ContentType responseContentType = ANY,
                     def responseClass = null, Map addlHeaders = null) {
        rest(DELETE, path, query, responseContentType, responseClass, TEXT, null, addlHeaders)
    }

    def <T> T post(String path, Map query = null, ContentType responseContentType = ANY,
                   def responseClass = null, ContentType requestContentType = JSON, requestBody = null, Map addlHeaders = null, long contentLength = -1) {
        rest(POST, path, query, responseContentType, responseClass, requestContentType, requestBody, addlHeaders, contentLength)
    }

    def <T> T put(String path, Map query = null, ContentType responseContentType = ANY,
                  def responseClass = null, ContentType requestContentType = JSON, requestBody = null, Map addlHeaders = null, long contentLength = -1) {
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

    private def <T> T rest(Method method, String path, Map query = null, responseType = ANY,
                           def responseClass, ContentType requestContentType = JSON, requestBody = null, Map addlHeaders = null, long contentLength = -1) {
        log.debug("Method: {}, Path: {}", method, path);
        log.debug("ResponseType: {}, ResponseClass: {}", responseType, responseClass);
        log.debug("RequestContentType: {}", requestContentType);
        log.debug("Headers: {}", addlHeaders);
        String finalUrl = this.url + path;
        log.debug("finalUrl: {}", finalUrl);
        BoundRequestBuilder requestBuilder;
        switch (method) {
            case POST:
                requestBuilder = ningHttpClient.preparePost(finalUrl).setBody(createInputStreamBodyGenerator(requestBody));
                break;
            case PUT:
                requestBuilder = ningHttpClient.preparePut(finalUrl).setBody(createInputStreamBodyGenerator(requestBody));
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
                // This may puke on IOException, JsonParseException, JsonMappingException but we will let the caller deal with it.
                if (responseType == ContentType.JSON && inputStream.available() > 0) {
                    if (responseClass == String) {
                        return (T) inputStream.text;
                    }
                    return (T) objectMapper.readValue(inputStream, responseClass);
                }
                //TODO: Handle other cases.
            } finally {
                inputStream.close();
            }
        }
        return null;
    }

    private Request customizeRequest(BoundRequestBuilder boundRequestBuilder, Map addlHeaders, Map query) {
        BoundRequestBuilder customRequestBuilder = this.ningRequest.getBoundRequestBuilder(boundRequestBuilder);
        if (customRequestBuilder == null) {
            customRequestBuilder = boundRequestBuilder;
        }
        if (addlHeaders != null) {
            for (e in addlHeaders) {
                customRequestBuilder.setHeader(e.key, (String) e.value);
            }
        }
        if (query != null) {
            for (e in query) {
                customRequestBuilder.addQueryParameter(e.key, (String) e.value);
            }
        }
        return customRequestBuilder.build();
    }

    private InputStream handleResponse(Response response, String url, boolean isBinary) throws IOException {
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
                throw new HttpResponseException(statusCode, response.getStatusText());
            default:
                //For all other types, throw IOException.
                throw new IOException("Response was " + statusCode
                        + " message '" + response.getStatusText() + "'");
        }
    }
    
    private Object createInputStreamBodyGenerator(Object requestBody){
        if (requestBody instanceof InputStream){
            //wrap it with InputStreamBodyGenerator.  See https://github.com/AsyncHttpClient/async-http-client/issues/576
            return new InputStreamBodyGenerator(requestBody);
        }
        return requestBody;
    }
}
