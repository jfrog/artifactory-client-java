package org.jfrog.artifactory.client.model.repository.settings.impl;

import org.jfrog.artifactory.client.model.PackageType;
import org.jfrog.artifactory.client.model.impl.PackageTypeImpl;
import org.jfrog.artifactory.client.model.repository.settings.AbstractRepositorySettings;
import org.jfrog.artifactory.client.model.repository.settings.PuppetRepositorySettings;

public class PuppetRepositorySettingsImpl extends AbstractRepositorySettings implements PuppetRepositorySettings {
    private static String defaultLayout = "puppet-default";

    public PuppetRepositorySettingsImpl() {
        super(defaultLayout);
    }

    public PackageType getPackageType() {
        return PackageTypeImpl.puppet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PuppetRepositorySettingsImpl)) return false;

        return true;
    }
}
