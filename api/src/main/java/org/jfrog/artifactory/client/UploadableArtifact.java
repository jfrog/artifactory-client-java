package org.jfrog.artifactory.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.jfrog.artifactory.client.model.File;

/**
 * @author jbaruch
 * @since 12/08/12
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface UploadableArtifact extends Artifact<UploadableArtifact> {

    File doUpload();

    String doUploadAndExplode();

    String doUploadAndExplode(boolean atomic);

    UploadableArtifact withListener(UploadListener listener);

    UploadableArtifact bySha1Checksum();

    UploadableArtifact bySha1Checksum(String sha1);

    UploadableArtifact withProperty(String name, Object... values);

    UploadableArtifact withProperty(String name, Object value);
}
