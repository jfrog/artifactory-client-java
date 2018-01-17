package org.jfrog.artifactory.client.model.repository.settings.impl;

import org.jfrog.artifactory.client.model.PackageType;
import org.jfrog.artifactory.client.model.impl.PackageTypeImpl;
import org.jfrog.artifactory.client.model.repository.settings.P2RepositorySettings;

/**
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
public class P2RepositorySettingsImpl extends MavenRepositorySettingsImpl implements P2RepositorySettings {

    @Override
    public PackageType getPackageType() {
        return PackageTypeImpl.p2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof P2RepositorySettingsImpl)) return false;
        if (!super.equals(o)) return false;

        return true;
    }
}
