package org.jfrog.artifactory.client.model.repository.settings.impl;

import org.jfrog.artifactory.client.model.PackageType;
import org.jfrog.artifactory.client.model.impl.CustomPackageTypeImpl;
import org.jfrog.artifactory.client.model.repository.settings.AbstractRepositorySettings;
import org.jfrog.artifactory.client.model.repository.settings.CustomRepositorySettings;

public class CustomRepositorySettingsImpl extends AbstractRepositorySettings implements CustomRepositorySettings {
    public static String defaultLayout = "maven-2-default";

    private PackageType packageType;

    public CustomRepositorySettingsImpl(CustomPackageTypeImpl packageType) {
        this.packageType = packageType;
        this.repoLayoutRef = defaultLayout;
    }

    @Override
    public PackageType getPackageType() {
        return packageType;
    }
}