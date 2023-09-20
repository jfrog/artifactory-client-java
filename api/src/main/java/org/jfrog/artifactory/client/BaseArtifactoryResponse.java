package org.jfrog.artifactory.client;
import org.apache.http.Header;
import org.apache.http.StatusLine;

public interface BaseArtifactoryResponse {

    Header[] getAllHeaders();

    StatusLine getStatusLine();

    boolean isSuccessResponse();

}
