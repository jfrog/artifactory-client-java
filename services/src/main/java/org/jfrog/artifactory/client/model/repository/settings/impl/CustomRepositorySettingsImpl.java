package org.jfrog.artifactory.client.model.repository.settings.impl;

import org.jfrog.artifactory.client.model.PackageType;
import org.jfrog.artifactory.client.model.impl.CustomPackageTypeImpl;
import org.jfrog.artifactory.client.model.repository.settings.AbstractRepositorySettings;
import org.jfrog.artifactory.client.model.repository.settings.CustomRepositorySettings;

public class CustomRepositorySettingsImpl extends AbstractRepositorySettings implements CustomRepositorySettings {
    private static final String DEFAULT_LAYOUT = "maven-2-default";

    private PackageType packageType;

    public CustomRepositorySettingsImpl(CustomPackageTypeImpl packageType) {
        super(DEFAULT_LAYOUT);
        this.packageType = packageType;
    }

    @Override
    public PackageType getPackageType() {
        return packageType;
    }
}