package org.jfrog.artifactory.client.model.repository.settings.impl;

import org.jfrog.artifactory.client.model.PackageType;
import org.jfrog.artifactory.client.model.impl.CustomPackageTypeImpl;
import org.jfrog.artifactory.client.model.repository.settings.AbstractRepositorySettings;
import org.jfrog.artifactory.client.model.repository.settings.CustomRepositorySettings;

public class CustomRepositorySettingsImpl extends AbstractRepositorySettings implements CustomRepositorySettings {
    static String defaultLayout = "maven-2-default";

    private PackageType packageType;

    public CustomRepositorySettingsImpl(CustomPackageTypeImpl packageType) {
        super(defaultLayout);
        this.packageType = packageType;
    }

    @Override
    public PackageType getPackageType() {
        return packageType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomRepositorySettingsImpl)) return false;

        return true;
    }
}
