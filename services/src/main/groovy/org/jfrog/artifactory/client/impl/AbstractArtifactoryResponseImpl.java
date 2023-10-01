package org.jfrog.artifactory.client.impl;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;

public abstract class AbstractArtifactoryResponseImpl {

    private final HttpResponse httpResponse;

    public AbstractArtifactoryResponseImpl(HttpResponse httpResponse) {
        this.httpResponse = httpResponse;
    }

    public HttpResponse getHttpResponse() {
        return httpResponse;
    }

    public Header[] getAllHeaders() {
        return this.httpResponse.getAllHeaders();
    }

    public StatusLine getStatusLine() {
        return this.httpResponse.getStatusLine();
    }

}
