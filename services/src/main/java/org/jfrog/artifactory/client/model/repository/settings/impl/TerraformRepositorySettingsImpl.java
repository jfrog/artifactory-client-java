package org.jfrog.artifactory.client.model.repository.settings.impl;

import org.jfrog.artifactory.client.model.PackageType;
import org.jfrog.artifactory.client.model.impl.PackageTypeImpl;
import org.jfrog.artifactory.client.model.repository.settings.AbstractRepositorySettings;
import org.jfrog.artifactory.client.model.repository.settings.TerraformRepositorySettings;
import org.jfrog.artifactory.client.model.repository.settings.vcs.VcsGitProvider;
import org.jfrog.artifactory.client.model.repository.settings.vcs.VcsType;

public class TerraformRepositorySettingsImpl extends AbstractRepositorySettings implements TerraformRepositorySettings {

    public static String defaultLayout = "simple-default";
    public static String moduleLayout = "terraform-module-default";
    public static String providerLayout = "terraform-provider-default";
    private TerraformType terraformType;
    private String terraformRegistryUrl;
    private String terraformProvidersUrl;
    private VcsType vcsType;
    private VcsGitProvider vcsGitProvider;
    private String remoteRepoLayoutRef;

    public TerraformRepositorySettingsImpl() {
        super(defaultLayout);
    }

    @Override
    public PackageType getPackageType() {
        return PackageTypeImpl.terraform;
    }

    @Override
    public TerraformType getTerraformType() {
        return terraformType;
    }

    @Override
    public VcsType getVcsType() {
        return vcsType;
    }

    @Override
    public VcsGitProvider getVcsGitProvider() {
        return vcsGitProvider;
    }

    @Override
    public String getTerraformRegistryUrl() {
        return terraformRegistryUrl;
    }

    @Override
    public String getTerraformProvidersUrl() {
        return terraformProvidersUrl;
    }

    @Override
    public String getRemoteRepoLayoutRef() {
        return remoteRepoLayoutRef;
    }

    public void setTerraformType(TerraformType terraformType) {
        this.terraformType = terraformType;
    }

    public void setVcsType(VcsType vcsType) {
        this.vcsType = vcsType;
    }

    public void setVcsGitProvider(VcsGitProvider vcsGitProvider) {
        this.vcsGitProvider = vcsGitProvider;
    }

    public void setTerraformRegistryUrl(String terraformRegistryUrl) {
        this.terraformRegistryUrl = terraformRegistryUrl;
    }

    public void setTerraformProvidersUrl(String terraformProvidersUrl) {
        this.terraformProvidersUrl = terraformProvidersUrl;
    }

    public void setRemoteRepoLayoutRef(String remoteRepoLayoutRef) {
        this.remoteRepoLayoutRef = remoteRepoLayoutRef;
    }
}
