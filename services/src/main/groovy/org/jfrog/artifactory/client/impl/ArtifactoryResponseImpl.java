package org.jfrog.artifactory.client.impl;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.util.EntityUtils;
import org.jfrog.artifactory.client.ArtifactoryResponse;

import java.io.IOException;

public class ArtifactoryResponseImpl implements ArtifactoryResponse {

    private HttpResponse httpResponse;
    private String body;

    ArtifactoryResponseImpl(HttpResponse httpResponse) throws IOException {
        this.httpResponse = httpResponse;

        HttpEntity entity = httpResponse.getEntity();

        try {
            this.body = EntityUtils.toString(entity);
        }
        catch (IOException e) {
            throw new IOException("Failed reading from response stream.");
        }
        finally {
            EntityUtils.consumeQuietly(entity);
        }
    }

    @Override
    public Header[] getAllHeaders() {
        return this.httpResponse.getAllHeaders();
    }

    @Override
    public StatusLine getStatusLine() {
        return this.httpResponse.getStatusLine();
    }

    @Override
    public String getBody() {
        return this.body;
    }
}
