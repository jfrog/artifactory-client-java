package org.jfrog.artifactory.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @author jbaruch
 * @since 12/08/12
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface DownloadableArtifact extends Artifact<DownloadableArtifact> {

    InputStream doDownload() throws IOException;

    InputStream doDownloadWithHeaders(Map<String, String> headers) throws IOException;
    DownloadableArtifact withProperty(String name, Object... values);

    DownloadableArtifact withProperty(String name, Object value);

    DownloadableArtifact withMandatoryProperty(String name, Object... values);

    DownloadableArtifact withMandatoryProperty(String name, Object value);
}
