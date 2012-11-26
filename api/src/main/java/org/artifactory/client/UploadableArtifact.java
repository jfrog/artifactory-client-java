package org.artifactory.client;

import org.artifactory.client.model.File;

/**
 * @author jbaruch
 * @since 12/08/12
 */
public interface UploadableArtifact extends Artifact<UploadableArtifact> {

    File doUpload();

    UploadableArtifact withListener(UploadListener listener);

    UploadableArtifact bySha1Checksum();

    UploadableArtifact bySha1Checksum(String sha1);
}
