package org.jfrog.artifactory.client.model.repository.settings.impl;

import org.jfrog.artifactory.client.model.PackageType;
import org.jfrog.artifactory.client.model.impl.PackageTypeImpl;
import org.jfrog.artifactory.client.model.repository.settings.AbstractRepositorySettings;
import org.jfrog.artifactory.client.model.repository.settings.NpmRepositorySettings;

import java.util.Collection;

/**
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
public class NpmRepositorySettingsImpl extends AbstractRepositorySettings implements NpmRepositorySettings {
    public static String defaultLayout = "npm-default";
    private Boolean listRemoteFolderItems;
    private Boolean externalDependenciesEnabled;
    private Collection<String> externalDependenciesPatterns;
    private String externalDependenciesRemoteRepo;

    public NpmRepositorySettingsImpl() {
        super(defaultLayout);
    }

    public PackageType getPackageType() {
        return PackageTypeImpl.npm;
    }

    public Boolean getListRemoteFolderItems() {
        return listRemoteFolderItems;
    }

    public void setListRemoteFolderItems(Boolean listRemoteFolderItems) {
        this.listRemoteFolderItems = listRemoteFolderItems;
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
        if (!(o instanceof NpmRepositorySettingsImpl)) return false;

        NpmRepositorySettingsImpl that = (NpmRepositorySettingsImpl) o;

        if (listRemoteFolderItems != null ? !listRemoteFolderItems.equals(that.listRemoteFolderItems) : that.listRemoteFolderItems != null)
            return false;
        if (externalDependenciesEnabled != null ? !externalDependenciesEnabled.equals(that.externalDependenciesEnabled) : that.externalDependenciesEnabled != null)
            return false;
        if (externalDependenciesPatterns != null ? !externalDependenciesPatterns.equals(that.externalDependenciesPatterns) : that.externalDependenciesPatterns != null)
            return false;
        return externalDependenciesRemoteRepo != null ? externalDependenciesRemoteRepo.equals(that.externalDependenciesRemoteRepo) : that.externalDependenciesRemoteRepo == null;
    }

    @Override
    public int hashCode() {
        int result = listRemoteFolderItems != null ? listRemoteFolderItems.hashCode() : 0;
        result = 31 * result + (externalDependenciesEnabled != null ? externalDependenciesEnabled.hashCode() : 0);
        result = 31 * result + (externalDependenciesPatterns != null ? externalDependenciesPatterns.hashCode() : 0);
        result = 31 * result + (externalDependenciesRemoteRepo != null ? externalDependenciesRemoteRepo.hashCode() : 0);
        return result;
    }
}
