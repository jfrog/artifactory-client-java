package org.jfrog.artifactory.client.model.repository.settings;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface DebianRepositorySettings extends RepositorySettings {

    // ** local ** //

    Boolean getDebianTrivialLayout();

    // ** remote ** //

    Boolean getListRemoteFolderItems();

}
