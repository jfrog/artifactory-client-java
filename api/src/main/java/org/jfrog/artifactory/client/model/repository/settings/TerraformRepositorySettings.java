package org.jfrog.artifactory.client.model.repository.settings;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.jfrog.artifactory.client.model.repository.settings.vcs.VcsGitProvider;
import org.jfrog.artifactory.client.model.repository.settings.vcs.VcsType;

@JsonIgnoreProperties(ignoreUnknown = true)
public interface TerraformRepositorySettings extends RepositorySettings{

    // Local and federated settings
    enum TerraformType {
        module,
        provider
    }

    TerraformType getTerraformType();

    // Remote settings
    // Url is already declared in RemoteRepo

    VcsType getVcsType();

    VcsGitProvider getVcsGitProvider();

    String getTerraformRegistryUrl();

    String getTerraformProvidersUrl();

    String getRemoteRepoLayoutRef();

}
