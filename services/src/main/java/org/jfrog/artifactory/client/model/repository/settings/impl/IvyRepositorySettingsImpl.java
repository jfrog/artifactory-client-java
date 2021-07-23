package org.jfrog.artifactory.client.model.repository.settings.impl;

import org.jfrog.artifactory.client.model.PackageType;
import org.jfrog.artifactory.client.model.impl.PackageTypeImpl;
import org.jfrog.artifactory.client.model.repository.settings.IvyRepositorySettings;

/**
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
public class IvyRepositorySettingsImpl extends MavenRepositorySettingsImpl implements IvyRepositorySettings {
    public static String defaultLayout = "ivy-default";

    public IvyRepositorySettingsImpl() {
        super(defaultLayout);
    }

    @Override
    public PackageType getPackageType() {
        return PackageTypeImpl.ivy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IvyRepositorySettingsImpl)) return false;
        return super.equals(o);
    }
}
