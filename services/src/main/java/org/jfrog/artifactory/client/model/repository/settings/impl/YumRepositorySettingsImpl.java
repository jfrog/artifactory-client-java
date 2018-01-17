package org.jfrog.artifactory.client.model.repository.settings.impl;

import org.jfrog.artifactory.client.model.PackageType;
import org.jfrog.artifactory.client.model.impl.PackageTypeImpl;
import org.jfrog.artifactory.client.model.repository.settings.AbstractRepositorySettings;
import org.jfrog.artifactory.client.model.repository.settings.YumRepositorySettings;

/**
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 * @deprecated since Artifactory 5.0.0. Replaced by {@link RpmRepositorySettingsImpl}
 */
@Deprecated
public class YumRepositorySettingsImpl extends AbstractRepositorySettings implements YumRepositorySettings {
    private static String defaultLayout = "simple-default";
    private Integer yumRootDepth;
    private String groupFileNames;
    private Boolean calculateYumMetadata;
    private Boolean listRemoteFolderItems;

    public YumRepositorySettingsImpl() {
        super(defaultLayout);
    }

    public PackageType getPackageType() {
        return PackageTypeImpl.yum;
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

    public Boolean getListRemoteFolderItems() {
        return listRemoteFolderItems;
    }

    public void setListRemoteFolderItems(Boolean listRemoteFolderItems) {
        this.listRemoteFolderItems = listRemoteFolderItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof YumRepositorySettingsImpl)) return false;

        YumRepositorySettingsImpl that = (YumRepositorySettingsImpl) o;

        if (yumRootDepth != null ? !yumRootDepth.equals(that.yumRootDepth) : that.yumRootDepth != null) return false;
        if (groupFileNames != null ? !groupFileNames.equals(that.groupFileNames) : that.groupFileNames != null)
            return false;
        if (calculateYumMetadata != null ? !calculateYumMetadata.equals(that.calculateYumMetadata) : that.calculateYumMetadata != null)
            return false;
        return listRemoteFolderItems != null ? listRemoteFolderItems.equals(that.listRemoteFolderItems) : that.listRemoteFolderItems == null;
    }

    @Override
    public int hashCode() {
        int result = yumRootDepth != null ? yumRootDepth.hashCode() : 0;
        result = 31 * result + (groupFileNames != null ? groupFileNames.hashCode() : 0);
        result = 31 * result + (calculateYumMetadata != null ? calculateYumMetadata.hashCode() : 0);
        result = 31 * result + (listRemoteFolderItems != null ? listRemoteFolderItems.hashCode() : 0);
        return result;
    }
}
