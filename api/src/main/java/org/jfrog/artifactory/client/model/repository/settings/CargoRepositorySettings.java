package org.jfrog.artifactory.client.model.repository.settings;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public interface CargoRepositorySettings extends RepositorySettings {
    String getGitRegistryUrl();
    Boolean isCargoInternalIndex();
    Boolean isCargoAnonymousAccess();
}
