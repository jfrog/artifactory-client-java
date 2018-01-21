package org.jfrog.artifactory.client.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.util.EntityUtils;
import org.jfrog.artifactory.client.ArtifactoryResponse;

import java.io.IOException;

public class ArtifactoryResponseImpl implements ArtifactoryResponse {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private HttpResponse httpResponse;
    private String rawBody;

    ArtifactoryResponseImpl(HttpResponse httpResponse) throws IOException {
        this.httpResponse = httpResponse;

        HttpEntity entity = httpResponse.getEntity();

        if (entity != null) {
            try {
                this.rawBody = EntityUtils.toString(entity, "UTF-8");
            } catch (IOException e) {
                throw new IOException("Failed reading from response stream.");
            } finally {
                EntityUtils.consumeQuietly(entity);
            }
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
    public String getRawBody() {
        return this.rawBody;
    }

    @Override
    public <T> T parseBody(Class<T> toType) throws IOException {
        try {
            return objectMapper.readValue(rawBody, toType);
        } catch (IOException e) {
            throw new IOException("Failed casting response entity to " + toType.toString() + ". response status: " + getStatusLine().toString() + ". raw entity: " + this.rawBody);
        }
    }

    @Override
    public boolean isSuccessResponse() {
        int status = getStatusLine().getStatusCode();

        return status >= 200 && status < 300;
    }
}
