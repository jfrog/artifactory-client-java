package org.jfrog.artifactory.client.model.repository.settings.impl;

import org.jfrog.artifactory.client.model.PackageType;
import org.jfrog.artifactory.client.model.impl.PackageTypeImpl;
import org.jfrog.artifactory.client.model.repository.settings.AbstractRepositorySettings;
import org.jfrog.artifactory.client.model.repository.settings.ConanRepositorySettings;

public class ConanRepositorySettingsImpl extends AbstractRepositorySettings implements ConanRepositorySettings {
    private static String defaultLayout = "conan-default";

    public ConanRepositorySettingsImpl() {
        super(defaultLayout);
    }

    public PackageType getPackageType() {
        return PackageTypeImpl.conan;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConanRepositorySettingsImpl)) return false;

        return true;
    }
}
