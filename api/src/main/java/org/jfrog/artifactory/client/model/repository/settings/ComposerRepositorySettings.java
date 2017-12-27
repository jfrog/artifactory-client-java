package org.jfrog.artifactory.client.model.repository.settings;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public interface ComposerRepositorySettings extends VcsRepositorySettings {

    // ** remote ** //

    String getComposerRegistryUrl();
}
