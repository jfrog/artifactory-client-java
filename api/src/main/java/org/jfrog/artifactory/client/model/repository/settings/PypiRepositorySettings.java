package org.jfrog.artifactory.client.model.repository.settings;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface PypiRepositorySettings extends RepositorySettings {

    // ** remote ** //

    Boolean getListRemoteFolderItems();

    String getPyPIRegistryUrl();
}
