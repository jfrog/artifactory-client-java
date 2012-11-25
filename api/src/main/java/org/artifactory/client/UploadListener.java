package org.artifactory.client;

/**
 * @author jbaruch
 * @since 21/11/12
 */
public interface UploadListener {

    void uploadProgress(long bytesRead, long totalBytes);
}
