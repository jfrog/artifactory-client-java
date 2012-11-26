package org.artifactory.client;

import org.artifactory.client.model.ReplicationStatus;
import org.artifactory.client.model.Repository;

import java.io.File;
import java.io.InputStream;

/**
 * @author jbaruch
 * @since 12/08/12
 */
public interface RepositoryHandle {

    ItemHandle folder(String folderName);

    ItemHandle file(String filePath);

    ReplicationStatus replicationStatus();

    String delete();

    String delete(String path);

    Repository get();

    UploadableArtifact upload(String targetPath, InputStream content);

    UploadableArtifact upload(String targetPath, File content);

    DownloadableArtifact download(String path);
}