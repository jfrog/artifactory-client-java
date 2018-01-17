package org.jfrog.artifactory.client.model.repository.settings.impl;

import org.jfrog.artifactory.client.model.PackageType;
import org.jfrog.artifactory.client.model.impl.PackageTypeImpl;
import org.jfrog.artifactory.client.model.repository.settings.AbstractRepositorySettings;
import org.jfrog.artifactory.client.model.repository.settings.PypiRepositorySettings;

/**
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
public class PypiRepositorySettingsImpl extends AbstractRepositorySettings implements PypiRepositorySettings {
    private static String defaultLayout = "simple-default";
    private Boolean listRemoteFolderItems;

    public PypiRepositorySettingsImpl() {
        super(defaultLayout);
    }

    public PackageType getPackageType() {
        return PackageTypeImpl.pypi;
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
        if (!(o instanceof PypiRepositorySettingsImpl)) return false;

        PypiRepositorySettingsImpl that = (PypiRepositorySettingsImpl) o;

        return listRemoteFolderItems != null ? listRemoteFolderItems.equals(that.listRemoteFolderItems) : that.listRemoteFolderItems == null;
    }

    @Override
    public int hashCode() {
        return listRemoteFolderItems != null ? listRemoteFolderItems.hashCode() : 0;
    }
}
