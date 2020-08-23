package org.jfrog.artifactory.client.model.repository.settings.impl;

import org.jfrog.artifactory.client.model.PackageType;
import org.jfrog.artifactory.client.model.impl.PackageTypeImpl;
import org.jfrog.artifactory.client.model.repository.settings.GradleRepositorySettings;

/**
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
public class GradleRepositorySettingsImpl extends MavenRepositorySettingsImpl implements GradleRepositorySettings {
    private static String defaultLayout = "maven-2-default";

    public GradleRepositorySettingsImpl() {
        super(defaultLayout);
    }

    @Override
    public PackageType getPackageType() {
        return PackageTypeImpl.gradle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GradleRepositorySettingsImpl)) return false;
        if (!super.equals(o)) return false;

        return true;
    }
}
