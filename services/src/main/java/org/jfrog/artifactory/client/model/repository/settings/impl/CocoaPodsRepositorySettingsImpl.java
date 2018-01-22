package org.jfrog.artifactory.client.model.repository.settings.impl;

import org.jfrog.artifactory.client.model.PackageType;
import org.jfrog.artifactory.client.model.impl.PackageTypeImpl;
import org.jfrog.artifactory.client.model.repository.settings.CocoaPodsRepositorySettings;

/**
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
public class CocoaPodsRepositorySettingsImpl extends VcsRepositorySettingsImpl implements CocoaPodsRepositorySettings {
    private static String defaultLayout = "simple-default";
    private String podsSpecsRepoUrl;

    public CocoaPodsRepositorySettingsImpl() {
        super(defaultLayout);
    }

    @Override
    public PackageType getPackageType() {
        return PackageTypeImpl.cocoapods;
    }

    public String getPodsSpecsRepoUrl() {
        return podsSpecsRepoUrl;
    }

    public void setPodsSpecsRepoUrl(String podsSpecsRepoUrl) {
        this.podsSpecsRepoUrl = podsSpecsRepoUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CocoaPodsRepositorySettingsImpl)) return false;
        if (!super.equals(o)) return false;

        CocoaPodsRepositorySettingsImpl that = (CocoaPodsRepositorySettingsImpl) o;

        return podsSpecsRepoUrl != null ? podsSpecsRepoUrl.equals(that.podsSpecsRepoUrl) : that.podsSpecsRepoUrl == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (podsSpecsRepoUrl != null ? podsSpecsRepoUrl.hashCode() : 0);
        return result;
    }
}
