package org.jfrog.artifactory.client;

import org.jfrog.artifactory.client.model.ItemPermission;
import org.jfrog.artifactory.client.model.ReplicationStatus;
import org.jfrog.artifactory.client.model.Repository;

import java.io.File;
import java.io.InputStream;
import java.util.Set;

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

    Set<ItemPermission> effectivePermissions();

    boolean isFolder(String path);

    boolean exists();
}

