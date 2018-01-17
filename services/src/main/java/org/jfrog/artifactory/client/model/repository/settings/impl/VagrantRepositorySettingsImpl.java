package org.jfrog.artifactory.client.model.repository.settings.impl;

import org.jfrog.artifactory.client.model.PackageType;
import org.jfrog.artifactory.client.model.impl.PackageTypeImpl;
import org.jfrog.artifactory.client.model.repository.settings.AbstractRepositorySettings;
import org.jfrog.artifactory.client.model.repository.settings.VagrantRepositorySettings;

/**
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
public class VagrantRepositorySettingsImpl extends AbstractRepositorySettings implements VagrantRepositorySettings {
    private static String defaultLayout = "simple-default";

    public VagrantRepositorySettingsImpl() {
        super(defaultLayout);
    }

    public PackageType getPackageType() {
        return PackageTypeImpl.vagrant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VagrantRepositorySettingsImpl)) return false;

        return true;
    }
}
