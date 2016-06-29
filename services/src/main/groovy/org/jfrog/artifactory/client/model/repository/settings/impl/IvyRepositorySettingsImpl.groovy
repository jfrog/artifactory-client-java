package org.jfrog.artifactory.client.model.repository.settings.impl

import org.jfrog.artifactory.client.model.PackageType
import org.jfrog.artifactory.client.model.repository.settings.IvyRepositorySettings

/**
 * GroovyBean implementation of the {@link IvyRepositorySettings}
 * 
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */ 
class IvyRepositorySettingsImpl extends MavenRepositorySettingsImpl implements IvyRepositorySettings {
    @Override
    public PackageType getPackageType() {
        return PackageType.ivy
    }
}
