package org.jfrog.artifactory.client;

import java.io.InputStream;

/**
 * @author jbaruch
 * @since 12/08/12
 */
public interface DownloadableArtifact extends Artifact<DownloadableArtifact> {

    InputStream doDownload();

    DownloadableArtifact withMandatoryProperty(String name, Object... values);

    DownloadableArtifact withMandatoryProperty(String name, Object value);
}
