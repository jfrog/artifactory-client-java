package org.jfrog.artifactory.client.model.repository.settings.impl;

import org.jfrog.artifactory.client.model.PackageType;
import org.jfrog.artifactory.client.model.impl.PackageTypeImpl;
import org.jfrog.artifactory.client.model.repository.settings.BowerRepositorySettings;

import java.util.Collection;

/**
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
public class BowerRepositorySettingsImpl extends VcsRepositorySettingsImpl implements BowerRepositorySettings {
    public static String defaultLayout = "bower-default";
    private String bowerRegistryUrl;
    private Boolean externalDependenciesEnabled;
    private Collection<String> externalDependenciesPatterns;
    private String externalDependenciesRemoteRepo;

    public BowerRepositorySettingsImpl() {
        super(defaultLayout);
    }

    @Override
    public PackageType getPackageType() {
        return PackageTypeImpl.bower;
    }

    public String getBowerRegistryUrl() {
        return bowerRegistryUrl;
    }

    public void setBowerRegistryUrl(String bowerRegistryUrl) {
        this.bowerRegistryUrl = bowerRegistryUrl;
    }

    public Boolean getExternalDependenciesEnabled() {
        return externalDependenciesEnabled;
    }

    public void setExternalDependenciesEnabled(Boolean externalDependenciesEnabled) {
        this.externalDependenciesEnabled = externalDependenciesEnabled;
    }

    public Collection<String> getExternalDependenciesPatterns() {
        return externalDependenciesPatterns;
    }

    public void setExternalDependenciesPatterns(Collection<String> externalDependenciesPatterns) {
        this.externalDependenciesPatterns = externalDependenciesPatterns;
    }

    public String getExternalDependenciesRemoteRepo() {
        return externalDependenciesRemoteRepo;
    }

    public void setExternalDependenciesRemoteRepo(String externalDependenciesRemoteRepo) {
        this.externalDependenciesRemoteRepo = externalDependenciesRemoteRepo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BowerRepositorySettingsImpl)) return false;
        if (!super.equals(o)) return false;

        BowerRepositorySettingsImpl that = (BowerRepositorySettingsImpl) o;

        if (bowerRegistryUrl != null ? !bowerRegistryUrl.equals(that.bowerRegistryUrl) : that.bowerRegistryUrl != null)
            return false;
        if (externalDependenciesEnabled != null ? !externalDependenciesEnabled.equals(that.externalDependenciesEnabled) : that.externalDependenciesEnabled != null)
            return false;
        if (externalDependenciesPatterns != null ? !externalDependenciesPatterns.equals(that.externalDependenciesPatterns) : that.externalDependenciesPatterns != null)
            return false;
        return externalDependenciesRemoteRepo != null ? externalDependenciesRemoteRepo.equals(that.externalDependenciesRemoteRepo) : that.externalDependenciesRemoteRepo == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (bowerRegistryUrl != null ? bowerRegistryUrl.hashCode() : 0);
        result = 31 * result + (externalDependenciesEnabled != null ? externalDependenciesEnabled.hashCode() : 0);
        result = 31 * result + (externalDependenciesPatterns != null ? externalDependenciesPatterns.hashCode() : 0);
        result = 31 * result + (externalDependenciesRemoteRepo != null ? externalDependenciesRemoteRepo.hashCode() : 0);
        return result;
    }
}
