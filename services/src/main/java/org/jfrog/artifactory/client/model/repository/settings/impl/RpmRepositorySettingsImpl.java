package org.jfrog.artifactory.client.model.repository.settings.impl;

import org.jfrog.artifactory.client.model.PackageType;
import org.jfrog.artifactory.client.model.impl.PackageTypeImpl;
import org.jfrog.artifactory.client.model.repository.settings.AbstractRepositorySettings;
import org.jfrog.artifactory.client.model.repository.settings.RpmRepositorySettings;

/**
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
public class RpmRepositorySettingsImpl extends AbstractRepositorySettings implements RpmRepositorySettings {
    private static String defaultLayout = "simple-default";
    private Integer yumRootDepth;
    private String groupFileNames;
    private Boolean calculateYumMetadata;
    private Boolean enableFileListsIndexing;
    private Boolean listRemoteFolderItems;

    public RpmRepositorySettingsImpl() {
        super(defaultLayout);
    }

    public PackageType getPackageType() {
        return PackageTypeImpl.rpm;
    }

    public Integer getYumRootDepth() {
        return yumRootDepth;
    }

    public void setYumRootDepth(Integer yumRootDepth) {
        this.yumRootDepth = yumRootDepth;
    }

    public String getGroupFileNames() {
        return groupFileNames;
    }

    public void setGroupFileNames(String groupFileNames) {
        this.groupFileNames = groupFileNames;
    }

    public Boolean getCalculateYumMetadata() {
        return calculateYumMetadata;
    }

    public void setCalculateYumMetadata(Boolean calculateYumMetadata) {
        this.calculateYumMetadata = calculateYumMetadata;
    }

    public Boolean getEnableFileListsIndexing() {
        return enableFileListsIndexing;
    }

    public void setEnableFileListsIndexing(Boolean enableFileListsIndexing) {
        this.enableFileListsIndexing = enableFileListsIndexing;
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
        if (!(o instanceof RpmRepositorySettingsImpl)) return false;

        RpmRepositorySettingsImpl that = (RpmRepositorySettingsImpl) o;

        if (yumRootDepth != null ? !yumRootDepth.equals(that.yumRootDepth) : that.yumRootDepth != null) return false;
        if (groupFileNames != null ? !groupFileNames.equals(that.groupFileNames) : that.groupFileNames != null)
            return false;
        if (calculateYumMetadata != null ? !calculateYumMetadata.equals(that.calculateYumMetadata) : that.calculateYumMetadata != null)
            return false;
        if (enableFileListsIndexing != null ? !enableFileListsIndexing.equals(that.enableFileListsIndexing) : that.enableFileListsIndexing != null)
            return false;
        return listRemoteFolderItems != null ? listRemoteFolderItems.equals(that.listRemoteFolderItems) : that.listRemoteFolderItems == null;
    }

    @Override
    public int hashCode() {
        int result = yumRootDepth != null ? yumRootDepth.hashCode() : 0;
        result = 31 * result + (groupFileNames != null ? groupFileNames.hashCode() : 0);
        result = 31 * result + (calculateYumMetadata != null ? calculateYumMetadata.hashCode() : 0);
        result = 31 * result + (enableFileListsIndexing != null ? enableFileListsIndexing.hashCode() : 0);
        result = 31 * result + (listRemoteFolderItems != null ? listRemoteFolderItems.hashCode() : 0);
        return result;
    }
}
