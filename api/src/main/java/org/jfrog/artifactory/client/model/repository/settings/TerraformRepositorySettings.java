package org.jfrog.artifactory.client.model.repository.settings;

import org.jfrog.artifactory.client.model.repository.settings.vcs.VcsGitProvider;
import org.jfrog.artifactory.client.model.repository.settings.vcs.VcsType;

public interface TerraformRepositorySettings extends RepositorySettings{

    // local and federated settings
    enum TerraformType {
        module,
        provider
    }

    // local and federated settings
    TerraformType getTerraformType();

    // remote settings

    // url is already declared in RemoteRepo

    VcsType getVcsType();

    VcsGitProvider getVcsGitProvider();

    String getTerraformRegistryUrl();

    String getTerraformProvidersUrl();

    String getRemoteRepoLayoutRef();

}
