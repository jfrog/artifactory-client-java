package org.jfrog.artifactory.client.model.repository.settings.impl;

import org.jfrog.artifactory.client.model.PackageType;
import org.jfrog.artifactory.client.model.impl.PackageTypeImpl;
import org.jfrog.artifactory.client.model.repository.settings.AbstractRepositorySettings;
import org.jfrog.artifactory.client.model.repository.settings.CondaRepositorySettings;

public class CondaRepositorySettingsImpl extends AbstractRepositorySettings implements CondaRepositorySettings {
    public static String defaultLayout = "simple-default";

    public CondaRepositorySettingsImpl() {
        super(defaultLayout);
    }

    public PackageType getPackageType() {
        return PackageTypeImpl.conda;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o instanceof CondaRepositorySettingsImpl;
    }
}