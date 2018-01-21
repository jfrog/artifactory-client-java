package org.jfrog.artifactory.client;

import org.apache.http.Header;
import org.apache.http.StatusLine;

import java.io.IOException;

/**
 * ArtifactoryResponse object returned from {@link Artifactory#restCall(ArtifactoryRequest)}.
 * acts as a wrapper for {@link org.apache.http.HttpResponse} but removes the need to handle response stream.
 */
public interface ArtifactoryResponse {

    Header[] getAllHeaders();

    StatusLine getStatusLine();

    String getRawBody();

    <T> T parseBody(Class<T> toType) throws IOException;
}
