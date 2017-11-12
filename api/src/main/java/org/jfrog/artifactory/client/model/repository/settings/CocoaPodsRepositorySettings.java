package org.jfrog.artifactory.client.model.repository.settings;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface CocoaPodsRepositorySettings extends VcsRepositorySettings {

    // ** remote ** //

    String getPodsSpecsRepoUrl();

}
