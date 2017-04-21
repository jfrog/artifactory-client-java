package org.jfrog.artifactory.client.model.repository.settings.impl

import org.jfrog.artifactory.client.model.PackageType
import org.jfrog.artifactory.client.model.repository.settings.GradleRepositorySettings

/**
 * GroovyBean implementation of the {@link GradleRepositorySettings}
 * 
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */ 
class GradleRepositorySettingsImpl extends MavenRepositorySettingsImpl implements GradleRepositorySettings {
    @Override
    public PackageType getPackageType() {
        return PackageType.gradle
    }
}
