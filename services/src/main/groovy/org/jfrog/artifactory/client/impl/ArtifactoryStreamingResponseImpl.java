package org.jfrog.artifactory.client.impl;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.jfrog.artifactory.client.ArtifactoryStreamingResponse;

import java.io.IOException;
import java.io.InputStream;

public class ArtifactoryStreamingResponseImpl extends AbstractArtifactoryResponseImpl implements ArtifactoryStreamingResponse {

    public ArtifactoryStreamingResponseImpl(HttpResponse httpResponse) {
        super(httpResponse);
    }

    @Override
    public InputStream getInputStream() throws IOException {
        InputStream is = null;
        HttpEntity entity = getHttpResponse().getEntity();
        if (entity != null) {
            is = entity.getContent();
        }
        return is;
    }

    @Override
    public boolean isSuccessResponse() {
        int status = getStatusLine().getStatusCode();
        return (status == HttpStatus.SC_OK ||
                status == HttpStatus.SC_PARTIAL_CONTENT);
    }

    @Override
    public void close() throws Exception {
        IOUtils.close(getInputStream());
    }
}
