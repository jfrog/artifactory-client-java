package org.jfrog.artifactory.client;

import java.io.IOException;
import java.io.InputStream;


/**
 * ArtifactoryStreamingResponse object returned from {@link Artifactory#streamingRestCall(ArtifactoryRequest)}.
 * acts as a wrapper for {@link org.apache.http.HttpResponse}.
 */
public interface ArtifactoryStreamingResponse extends BaseArtifactoryResponse, AutoCloseable {
    InputStream getInputStream() throws IOException;
}
