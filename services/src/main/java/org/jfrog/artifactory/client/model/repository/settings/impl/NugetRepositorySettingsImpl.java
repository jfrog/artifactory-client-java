package org.jfrog.artifactory.client.model.repository.settings.impl;

import org.jfrog.artifactory.client.model.PackageType;
import org.jfrog.artifactory.client.model.impl.PackageTypeImpl;
import org.jfrog.artifactory.client.model.repository.settings.AbstractRepositorySettings;
import org.jfrog.artifactory.client.model.repository.settings.NugetRepositorySettings;

/**
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
public class NugetRepositorySettingsImpl extends AbstractRepositorySettings implements NugetRepositorySettings {
    private static String defaultLayout = "nuget-default";
    private Integer maxUniqueSnapshots;
    private Boolean forceNugetAuthentication;
    private String feedContextPath;
    private String downloadContextPath;
    private Boolean listRemoteFolderItems;

    public NugetRepositorySettingsImpl() {
        super(defaultLayout);
    }

    public PackageType getPackageType() {
        return PackageTypeImpl.nuget;
    }

    public Integer getMaxUniqueSnapshots() {
        return maxUniqueSnapshots;
    }

    public void setMaxUniqueSnapshots(Integer maxUniqueSnapshots) {
        this.maxUniqueSnapshots = maxUniqueSnapshots;
    }

    public Boolean getForceNugetAuthentication() {
        return forceNugetAuthentication;
    }

    public void setForceNugetAuthentication(Boolean forceNugetAuthentication) {
        this.forceNugetAuthentication = forceNugetAuthentication;
    }

    public String getFeedContextPath() {
        return feedContextPath;
    }

    public void setFeedContextPath(String feedContextPath) {
        this.feedContextPath = feedContextPath;
    }

    public String getDownloadContextPath() {
        return downloadContextPath;
    }

    public void setDownloadContextPath(String downloadContextPath) {
        this.downloadContextPath = downloadContextPath;
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
        if (!(o instanceof NugetRepositorySettingsImpl)) return false;

        NugetRepositorySettingsImpl that = (NugetRepositorySettingsImpl) o;

        if (maxUniqueSnapshots != null ? !maxUniqueSnapshots.equals(that.maxUniqueSnapshots) : that.maxUniqueSnapshots != null)
            return false;
        if (forceNugetAuthentication != null ? !forceNugetAuthentication.equals(that.forceNugetAuthentication) : that.forceNugetAuthentication != null)
            return false;
        if (feedContextPath != null ? !feedContextPath.equals(that.feedContextPath) : that.feedContextPath != null)
            return false;
        if (downloadContextPath != null ? !downloadContextPath.equals(that.downloadContextPath) : that.downloadContextPath != null)
            return false;
        return listRemoteFolderItems != null ? listRemoteFolderItems.equals(that.listRemoteFolderItems) : that.listRemoteFolderItems == null;
    }

    @Override
    public int hashCode() {
        int result = maxUniqueSnapshots != null ? maxUniqueSnapshots.hashCode() : 0;
        result = 31 * result + (forceNugetAuthentication != null ? forceNugetAuthentication.hashCode() : 0);
        result = 31 * result + (feedContextPath != null ? feedContextPath.hashCode() : 0);
        result = 31 * result + (downloadContextPath != null ? downloadContextPath.hashCode() : 0);
        result = 31 * result + (listRemoteFolderItems != null ? listRemoteFolderItems.hashCode() : 0);
        return result;
    }
}
