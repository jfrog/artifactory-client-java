package org.artifactory.client;

import org.artifactory.client.model.File;

/**
 * @author jbaruch
 * @since 12/08/12
 */
public interface UploadableArtifact extends Artifact<UploadableArtifact> {

    File doUpload();
}
