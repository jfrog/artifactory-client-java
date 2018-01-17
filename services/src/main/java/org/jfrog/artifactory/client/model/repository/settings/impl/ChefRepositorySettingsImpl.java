package org.jfrog.artifactory.client.model.repository.settings.impl;

import org.jfrog.artifactory.client.model.PackageType;
import org.jfrog.artifactory.client.model.impl.PackageTypeImpl;
import org.jfrog.artifactory.client.model.repository.settings.AbstractRepositorySettings;
import org.jfrog.artifactory.client.model.repository.settings.ChefRepositorySettings;

public class ChefRepositorySettingsImpl extends AbstractRepositorySettings implements ChefRepositorySettings {
    private static String defaultLayout = "simple-default";

    public ChefRepositorySettingsImpl() {
        super(defaultLayout);
    }

    public PackageType getPackageType() {
        return PackageTypeImpl.chef;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChefRepositorySettingsImpl)) return false;

        return true;
    }
}
