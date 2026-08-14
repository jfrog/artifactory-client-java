package org.jfrog.artifactory.client.impl;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.jfrog.artifactory.client.ArtifactoryResponse;
import org.jfrog.artifactory.client.impl.util.Util;

import java.io.IOException;

public class ArtifactoryResponseImpl extends AbstractArtifactoryResponseImpl implements ArtifactoryResponse {

    private String rawBody;

    ArtifactoryResponseImpl(HttpResponse httpResponse) throws IOException {
        super(httpResponse);

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
    public String getRawBody() {
        return this.rawBody;
    }

    /**
     * Deserialise the response body to {@code toType} using the shared
     * {@link Util#CONFIGURED_MAPPER}.
     *
     * <p>Previously this method called {@code Util.configureObjectMapper(objectMapper)} on
     * every invocation, which mutated the static field's mapper state under concurrent use.
     * Delegating to the already-configured singleton removes both the mutation hazard and the
     * unnecessary per-call configuration overhead.
     */
    @Override
    public <T> T parseBody(Class<T> toType) throws IOException {
        try {
            return Util.CONFIGURED_MAPPER.readValue(rawBody, toType);
        } catch (IOException e) {
            throw new IOException(
                    "Failed casting response entity to " + toType
                    + ". response status: " + getStatusLine()
                    + ". raw entity: " + this.rawBody, e);
        }
    }

    @Override
    public boolean isSuccessResponse() {
        int status = getStatusLine().getStatusCode();
        return status >= 200 && status < 300;
    }
}
