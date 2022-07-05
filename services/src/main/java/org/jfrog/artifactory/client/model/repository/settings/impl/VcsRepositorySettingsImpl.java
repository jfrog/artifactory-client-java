package org.jfrog.artifactory.client.model.repository.settings.impl;

import org.jfrog.artifactory.client.model.PackageType;
import org.jfrog.artifactory.client.model.impl.PackageTypeImpl;
import org.jfrog.artifactory.client.model.repository.settings.AbstractRepositorySettings;
import org.jfrog.artifactory.client.model.repository.settings.VcsRepositorySettings;
import org.jfrog.artifactory.client.model.repository.settings.vcs.VcsGitProvider;
import org.jfrog.artifactory.client.model.repository.settings.vcs.VcsType;

/**
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
public class VcsRepositorySettingsImpl extends AbstractRepositorySettings implements VcsRepositorySettings {
    public static String defaultLayout = "vcs-default";
    private VcsGitProvider vcsGitProvider;
    private VcsType vcsType;
    private Integer maxUniqueSnapshots;
    private String vcsGitDownloadUrl;
    private Boolean listRemoteFolderItems;

    public VcsRepositorySettingsImpl() {
        this(defaultLayout);
    }

    VcsRepositorySettingsImpl(String repoLayout){
        super(repoLayout);
    }

    public PackageType getPackageType() {
        return PackageTypeImpl.vcs;
    }

    public VcsGitProvider getVcsGitProvider() {
        return vcsGitProvider;
    }

    public void setVcsGitProvider(VcsGitProvider vcsGitProvider) {
        this.vcsGitProvider = vcsGitProvider;
    }

    public VcsType getVcsType() {
        return vcsType;
    }

    public void setVcsType(VcsType vcsType) {
        this.vcsType = vcsType;
    }

    public Integer getMaxUniqueSnapshots() {
        return maxUniqueSnapshots;
    }

    public void setMaxUniqueSnapshots(Integer maxUniqueSnapshots) {
        this.maxUniqueSnapshots = maxUniqueSnapshots;
    }

    public String getVcsGitDownloadUrl() {
        return vcsGitDownloadUrl;
    }

    public void setVcsGitDownloadUrl(String vcsGitDownloadUrl) {
        this.vcsGitDownloadUrl = vcsGitDownloadUrl;
    }

    public Boolean getListRemoteFolderItems() {
        return listRemoteFolderItems;
    }

    public void setListRemoteFolderItems(Boolean listRemoteFolderItems) {
        this.listRemoteFolderItems = listRemoteFolderItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VcsRepositorySettingsImpl)) return false;

        VcsRepositorySettingsImpl that = (VcsRepositorySettingsImpl) o;

        if (vcsGitProvider != that.vcsGitProvider) return false;
        if (vcsType != that.vcsType) return false;
        if (maxUniqueSnapshots != null ? !maxUniqueSnapshots.equals(that.maxUniqueSnapshots) : that.maxUniqueSnapshots != null)
            return false;
        if (vcsGitDownloadUrl != null ? !vcsGitDownloadUrl.equals(that.vcsGitDownloadUrl) : that.vcsGitDownloadUrl != null)
            return false;
        return listRemoteFolderItems != null ? listRemoteFolderItems.equals(that.listRemoteFolderItems) : that.listRemoteFolderItems == null;
    }

    @Override
    public int hashCode() {
        int result = vcsGitProvider != null ? vcsGitProvider.hashCode() : 0;
        result = 31 * result + (vcsType != null ? vcsType.hashCode() : 0);
        result = 31 * result + (maxUniqueSnapshots != null ? maxUniqueSnapshots.hashCode() : 0);
        result = 31 * result + (vcsGitDownloadUrl != null ? vcsGitDownloadUrl.hashCode() : 0);
        result = 31 * result + (listRemoteFolderItems != null ? listRemoteFolderItems.hashCode() : 0);
        return result;
    }
}
