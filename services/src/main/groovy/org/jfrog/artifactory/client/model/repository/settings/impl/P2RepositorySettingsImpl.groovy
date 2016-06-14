package org.jfrog.artifactory.client.model.repository.settings.impl

import org.jfrog.artifactory.client.model.PackageType
import org.jfrog.artifactory.client.model.repository.settings.P2RepositorySettings

/**
 * GroovyBean implementation of the {@link P2RepositorySettings}
 * 
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */ 
class P2RepositorySettingsImpl extends MavenRepositorySettingsImpl implements P2RepositorySettings {
    @Override
    public PackageType getPackageType() {
        return PackageType.p2
    }
}
