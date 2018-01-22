package org.jfrog.artifactory.client.model.repository.settings.impl;

import org.jfrog.artifactory.client.model.PackageType;
import org.jfrog.artifactory.client.model.impl.PackageTypeImpl;
import org.jfrog.artifactory.client.model.repository.settings.ComposerRepositorySettings;

public class ComposerRepositorySettingsImpl extends VcsRepositorySettingsImpl implements ComposerRepositorySettings {
    private static String defaultLayout = "composer-default";
    private String composerRegistryUrl;

    public ComposerRepositorySettingsImpl() {
        super(defaultLayout);
    }

    @Override
    public PackageType getPackageType() {
        return PackageTypeImpl.composer;
    }

    public String getComposerRegistryUrl() {
        return composerRegistryUrl;
    }

    public void setComposerRegistryUrl(String composerRegistryUrl) {
        this.composerRegistryUrl = composerRegistryUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ComposerRepositorySettingsImpl)) return false;
        if (!super.equals(o)) return false;

        ComposerRepositorySettingsImpl that = (ComposerRepositorySettingsImpl) o;

        return composerRegistryUrl != null ? composerRegistryUrl.equals(that.composerRegistryUrl) : that.composerRegistryUrl == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (composerRegistryUrl != null ? composerRegistryUrl.hashCode() : 0);
        return result;
    }
}
