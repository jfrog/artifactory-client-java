package org.jfrog.artifactory.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author jbaruch
 * @since 21/11/12
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface UploadListener {

    void uploadProgress(long bytesRead, long totalBytes);
}
