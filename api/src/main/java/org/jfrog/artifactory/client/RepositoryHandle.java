package org.jfrog.artifactory.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.jfrog.artifactory.client.model.ItemPermission;
import org.jfrog.artifactory.client.model.ReplicationStatus;
import org.jfrog.artifactory.client.model.Repository;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

/**
 * @author jbaruch
 * @since 12/08/12
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface RepositoryHandle {

    ItemHandle folder(String folderName);

    ItemHandle file(String filePath);

    ReplicationStatus replicationStatus();

    String delete();

    String delete(String path);

    Repository get();

    UploadableArtifact upload(String targetPath, InputStream content);

    UploadableArtifact upload(String targetPath, File content);

    UploadableArtifact copyBySha1(String targetPath, String sha1);

    DownloadableArtifact download(String path);

    Set<ItemPermission> effectivePermissions();

    Replications getReplications();

    boolean isFolder(String path) throws IOException;

    boolean exists();
}

