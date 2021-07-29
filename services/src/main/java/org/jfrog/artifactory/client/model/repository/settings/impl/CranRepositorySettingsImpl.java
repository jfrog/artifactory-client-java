package org.jfrog.artifactory.client.model.repository.settings.impl;

import org.jfrog.artifactory.client.model.PackageType;
import org.jfrog.artifactory.client.model.impl.PackageTypeImpl;
import org.jfrog.artifactory.client.model.repository.settings.AbstractRepositorySettings;
import org.jfrog.artifactory.client.model.repository.settings.CranRepositorySettings;

public class CranRepositorySettingsImpl extends AbstractRepositorySettings implements CranRepositorySettings {
    public static String defaultLayout = "simple-default";

    public CranRepositorySettingsImpl() {
        super(defaultLayout);
    }

    public PackageType getPackageType() {
        return PackageTypeImpl.cran;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o instanceof CranRepositorySettingsImpl;
    }
}
