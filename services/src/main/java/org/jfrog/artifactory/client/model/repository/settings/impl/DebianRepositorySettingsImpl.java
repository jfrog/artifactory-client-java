package org.jfrog.artifactory.client.model.repository.settings.impl;

import org.jfrog.artifactory.client.model.PackageType;
import org.jfrog.artifactory.client.model.impl.PackageTypeImpl;
import org.jfrog.artifactory.client.model.repository.settings.AbstractRepositorySettings;
import org.jfrog.artifactory.client.model.repository.settings.DebianRepositorySettings;

/**
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
public class DebianRepositorySettingsImpl extends AbstractRepositorySettings implements DebianRepositorySettings {
    private static String defaultLayout = "simple-default";
    private Boolean debianTrivialLayout;
    private Boolean listRemoteFolderItems;

    public DebianRepositorySettingsImpl() {
        super(defaultLayout);
    }

    public PackageType getPackageType() {
        return PackageTypeImpl.debian;
    }

    public Boolean getDebianTrivialLayout() {
        return debianTrivialLayout;
    }

    public void setDebianTrivialLayout(Boolean debianTrivialLayout) {
        this.debianTrivialLayout = debianTrivialLayout;
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
        if (!(o instanceof DebianRepositorySettingsImpl)) return false;

        DebianRepositorySettingsImpl that = (DebianRepositorySettingsImpl) o;

        if (debianTrivialLayout != null ? !debianTrivialLayout.equals(that.debianTrivialLayout) : that.debianTrivialLayout != null)
            return false;
        return listRemoteFolderItems != null ? listRemoteFolderItems.equals(that.listRemoteFolderItems) : that.listRemoteFolderItems == null;
    }

    @Override
    public int hashCode() {
        int result = debianTrivialLayout != null ? debianTrivialLayout.hashCode() : 0;
        result = 31 * result + (listRemoteFolderItems != null ? listRemoteFolderItems.hashCode() : 0);
        return result;
    }
}
