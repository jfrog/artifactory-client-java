package org.jfrog.artifactory.client.model.repository.settings.impl;

import org.jfrog.artifactory.client.model.PackageType;
import org.jfrog.artifactory.client.model.impl.CustomPackageTypeImpl;
import org.jfrog.artifactory.client.model.impl.PackageTypeImpl;
import org.jfrog.artifactory.client.model.repository.settings.AbstractRepositorySettings;
import org.jfrog.artifactory.client.model.repository.settings.CustomRepositorySettings;

public class CustomRepositorySettingsImpl extends AbstractRepositorySettings implements CustomRepositorySettings {

    private PackageType packageType;

    public CustomRepositorySettingsImpl(CustomPackageTypeImpl packageType) {
        this.packageType = packageType;
        this.repoLayoutRef = PackageTypeImpl.maven.getLayout();
    }

    @Override
    public PackageType getPackageType() {
        return packageType;
    }
}