package org.jfrog.artifactory.client.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import groovyx.net.http.HttpResponseDecorator;
import groovyx.net.http.HttpResponseException;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.jfrog.artifactory.client.*;
import org.jfrog.artifactory.client.impl.util.Util;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

/**
 * @author jbaruch
 * @author Alexei Vainshtein
 * @since 25/07/12
 */
public class ArtifactoryImpl implements Artifactory {

    private String username;
    private String url;
    private String userAgent;
    private CloseableHttpClient httpClient;
    private String accessToken;

    public ArtifactoryImpl(CloseableHttpClient httpClient, String url, String userAgent, String username, String accessToken) {
        this.url = url;
        this.httpClient = httpClient;
        this.userAgent = userAgent;
        this.username = username;
        this.accessToken = accessToken;
    }

    @Override
    public void close() {
        if (httpClient != null) {
            try {
                httpClient.close();
            } catch (IOException e) {
                // Ignore
            }
        }
    }

    public CloseableHttpClient getHttpClient() {
        return httpClient;
    }

    public String getUri() throws MalformedURLException {
        URL url = new URL(this.url);
        String uri = url.getProtocol() + "://" + url.getHost();
        if (url.getPort() == -1) {
            return uri;
        }
        return uri + ":" + url.getPort();
    }

    public String getContextName() throws MalformedURLException {
        URL url = new URL(this.url);
        String urlContext = url.getPath();
        if (urlContext.startsWith("/")) {
            return urlContext.substring(1);
        }
        return urlContext;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getUserAgent() {
        return userAgent;
    }

    public Repositories repositories() {
        return new RepositoriesImpl(this, API_BASE);
    }

    public RepositoryHandle repository(String repo) {
        if (StringUtils.isBlank(repo)) {
            throw new IllegalArgumentException("Repository name is required");
        }
        return new RepositoriesImpl(this, API_BASE).repository(repo);
    }

    public Searches searches() {
        return new SearchesImpl(this, API_BASE);
    }

    public Security security() {
        return new SecurityImpl(this, API_BASE);
    }

    public Storage storage() {
        return new StorageImpl(this);
    }

    public Plugins plugins() {
        return new PluginsImpl(this, API_BASE);
    }

    @Override
    public ArtifactorySystem system() {
        return new ArtifactorySystemImpl(this, API_BASE);
    }

    /**
     * Create a REST call to artifactory with a generic request
     *
     * @param artifactoryRequest that should be sent to artifactory
     * @return {@link ArtifactoryResponse} artifactory response as per to the request sent
     */
    @Override
    public ArtifactoryResponse restCall(ArtifactoryRequest artifactoryRequest) throws Exception {
        HttpRequestBase httpRequest;

        String requestPath = "/" + artifactoryRequest.getApiUrl();
        ContentType contentType = Util.getContentType(artifactoryRequest.getRequestType());

        String queryPath = "";
        if (!artifactoryRequest.getQueryParams().isEmpty()) {
            queryPath = Util.getQueryPath("?", artifactoryRequest.getQueryParams().entrySet());
        }

        switch (artifactoryRequest.getMethod()) {
            case GET:
                httpRequest = new HttpGet();

                break;

            case POST:
                httpRequest = new HttpPost();
                setEntity((HttpPost)httpRequest, artifactoryRequest.getBody(), contentType);

                break;

            case PUT:
                httpRequest = new HttpPut();
                setEntity((HttpPut)httpRequest, artifactoryRequest.getBody(), contentType);

                break;

            case DELETE:
                httpRequest = new HttpDelete();

                break;

            default:
                throw new IllegalArgumentException("Unsupported request method.");
        }

        httpRequest.setURI(new URI(url + requestPath + queryPath));
        addAccessTokenHeaderIfNeeded(httpRequest);

        if (contentType != null) {
            httpRequest.setHeader("Content-type", contentType.getMimeType());
        }

        Map<String, String> headers = artifactoryRequest.getHeaders();
        for (String key : headers.keySet()) {
            httpRequest.setHeader(key, headers.get(key));
        }

        HttpResponse httpResponse = httpClient.execute(httpRequest);
        return new ArtifactoryResponseImpl(httpResponse);
    }

    private void setEntity(HttpEntityEnclosingRequestBase httpRequest, Object body, ContentType contentType) throws JsonProcessingException {
        if (body == null) {
            return;
        }
        if (body instanceof InputStream) {
            httpRequest.setEntity(new InputStreamEntity((InputStream)body));
        } else
        if (body instanceof String) {
            httpRequest.setEntity(new StringEntity((String)body, contentType));
        } else {
            String bodyText = Util.getStringFromObject(body);
            if (StringUtils.isNotBlank(bodyText)) {
                httpRequest.setEntity(new StringEntity(bodyText, contentType));
            }
        }
    }

    protected InputStream getInputStream(String path) throws IOException, URISyntaxException {
        HttpResponse httpResponse = get(path, null, null);
        if (httpResponse.getStatusLine().getStatusCode() == 200) {
            return httpResponse.getEntity().getContent();
        }
        throwException(httpResponse);
        return null;
    }

    private void throwException(HttpResponse httpResponse) throws IOException {
        String artifactoryResponse = Util.responseToString(httpResponse);
        if (StringUtils.isNotBlank(artifactoryResponse)) {
            httpResponse.setReasonPhrase(artifactoryResponse);
        }
        throw new HttpResponseException(new HttpResponseDecorator(httpResponse, null));
    }

    private HttpRequestBase addAccessTokenHeaderIfNeeded(HttpRequestBase httpRequestBase) {
        if (StringUtils.isNotEmpty(accessToken)) {
            httpRequestBase.addHeader("Authorization", "Bearer " + accessToken);
        }
        return httpRequestBase;
    }

    protected Boolean head(String path) throws URISyntaxException, IOException {
        HttpHead httpHead = new HttpHead();
        httpHead.setURI(new URI(url + path));
        httpHead = (HttpHead) addAccessTokenHeaderIfNeeded(httpHead);
        HttpResponse httpResponse = httpClient.execute(httpHead);
        int status = httpResponse.getStatusLine().getStatusCode();
        /** Any status code >= 100 and < 400 According to the {@link groovyx.net.http.Status} class*/
        return (status >= 100 && status < 400);
    }

    public <T> T get(String path, Class<? extends T> object, Class<T> interfaceObject) throws URISyntaxException, IOException {
        HttpGet httpGet = new HttpGet();

        httpGet.setURI(new URI(url + path));
        httpGet = (HttpGet) addAccessTokenHeaderIfNeeded(httpGet);

        HttpResponse httpResponse = httpClient.execute(httpGet);
        int status = httpResponse.getStatusLine().getStatusCode();
        if (status != HttpStatus.SC_OK && status != HttpStatus.SC_NO_CONTENT && status != HttpStatus.SC_ACCEPTED) {
            throwException(httpResponse);
        }

        if (object == null) {
            return (T) httpResponse;
        }
        if (object == String.class) {
            return (T) Util.responseToString(httpResponse);
        }
        return Util.responseToObject(httpResponse, object, interfaceObject);
    }

    public <T> T post(String path, org.apache.http.entity.ContentType contentType, String content, Map<String, String> headers, Class<? extends T> object, Class<T> interfaceObject) throws URISyntaxException, IOException {
        HttpPost httpPost = new HttpPost();
        httpPost.setURI(new URI(url + path));
        httpPost = (HttpPost) addAccessTokenHeaderIfNeeded(httpPost);

        httpPost.setHeader("Content-type", contentType.getMimeType());
        if (headers != null && !headers.isEmpty()) {
            for (String key : headers.keySet()) {
                httpPost.setHeader(key, headers.get(key));
            }
        }
        if (content != null) {
            httpPost.setEntity(new StringEntity(content, contentType));
        }
        HttpResponse httpResponse = httpClient.execute(httpPost);
        if (object == String.class) {
            return (T) Util.responseToString(httpResponse);
        }

        return Util.responseToObject(httpResponse, object, interfaceObject);
    }

    public <T> T put(String path, org.apache.http.entity.ContentType contentType, String content, Map<String, String> headers, InputStream inputStream, long length, Class<? extends T> object, Class<T> interfaceObject) throws IOException, URISyntaxException {
        HttpPut httpPut = new HttpPut();
        httpPut.setURI(new URI(url + path));
        httpPut = (HttpPut) addAccessTokenHeaderIfNeeded(httpPut);

        if (contentType != null) {
            httpPut.setHeader("Content-type", contentType.getMimeType());
        } else {
            contentType = org.apache.http.entity.ContentType.WILDCARD;
        }

        if (inputStream != null) {
            InputStreamEntity entity = new InputStreamEntity(inputStream, length);
            httpPut.setEntity(entity);
        }

        if (StringUtils.isNotBlank(content)) {
            httpPut.setEntity(new StringEntity(content, contentType));
        }
        if (headers != null && !headers.isEmpty()) {
            for (String key : headers.keySet()) {
                // Need to do String value of since the value of the key can be non String element (for example boolean)
                httpPut.setHeader(key, String.valueOf(headers.get(key)));
            }
        }
        HttpResponse httpResponse = httpClient.execute(httpPut);
        int status = httpResponse.getStatusLine().getStatusCode();
        if (status == HttpStatus.SC_OK || status == HttpStatus.SC_NO_CONTENT || status == HttpStatus.SC_ACCEPTED || status == HttpStatus.SC_CREATED) {
            if (object == String.class) {
                return (T) Util.responseToString(httpResponse);
            }
            return Util.responseToObject(httpResponse, object, interfaceObject);
        }

        throwException(httpResponse);
        return null;
    }

    public String delete(String path) throws URISyntaxException, IOException {
        HttpDelete httpDelete = new HttpDelete();

        httpDelete.setURI(new URI(url + path));
        httpDelete = (HttpDelete) addAccessTokenHeaderIfNeeded(httpDelete);
        HttpResponse httpResponse = httpClient.execute(httpDelete);
        int status = httpResponse.getStatusLine().getStatusCode();
        if (status != HttpStatus.SC_OK && status != HttpStatus.SC_NO_CONTENT && status != HttpStatus.SC_ACCEPTED) {
            throwException(httpResponse);
        }
        return Util.responseToString(httpResponse);
    }
}
