package org.jfrog.artifactory.client.model.repository.settings;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.jfrog.artifactory.client.model.repository.settings.docker.DockerApiVersion;

/**
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface DockerRepositorySettings extends RepositorySettings {

    // ** local ** //

    DockerApiVersion getDockerApiVersion();

    Integer getMaxUniqueTags();

    Integer getDockerTagRetention();

    // ** remote ** //

    Boolean getEnableTokenAuthentication();

    Boolean getListRemoteFolderItems();
}
