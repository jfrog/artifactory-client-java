package org.jfrog.artifactory.client.model.repository.settings.impl

import org.jfrog.artifactory.client.model.PackageType
import org.jfrog.artifactory.client.model.repository.settings.BowerRepositorySettings

/**
 * GroovyBean implementation of the {@link BowerRepositorySettings}
 * 
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
class BowerRepositorySettingsImpl extends VcsRepositorySettingsImpl implements BowerRepositorySettings {
    String bowerRegistryUrl
    Boolean externalDependenciesEnabled
    Collection<String> externalDependenciesPatterns
    String externalDependenciesRemoteRepo

    @Override
    public PackageType getPackageType() {
        return PackageType.bower
    }
}
