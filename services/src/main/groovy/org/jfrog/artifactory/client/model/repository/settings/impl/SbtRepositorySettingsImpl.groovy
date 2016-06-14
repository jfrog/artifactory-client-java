package org.jfrog.artifactory.client.model.repository.settings.impl

import org.jfrog.artifactory.client.model.PackageType
import org.jfrog.artifactory.client.model.repository.settings.SbtRepositorySettings

/**
 * GroovyBean implementation of the {@link SbtRepositorySettings}
 * 
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */ 
class SbtRepositorySettingsImpl extends MavenRepositorySettingsImpl implements SbtRepositorySettings {
    @Override
    public PackageType getPackageType() {
        return PackageType.sbt
    }
}
