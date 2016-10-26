package org.jfrog.artifactory.client.model.repository.settings.impl

import org.jfrog.artifactory.client.model.PackageType
import org.jfrog.artifactory.client.model.repository.settings.NugetRepositorySettings

/**
 * GroovyBean implementation of the {@link NugetRepositorySettings}
 * 
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
class NugetRepositorySettingsImpl extends AbstractXraySettings implements NugetRepositorySettings {
    Integer maxUniqueSnapshots
    Boolean forceNugetAuthentication
    String feedContextPath
    String downloadContextPath
    Boolean listRemoteFolderItems

    @Override
    public PackageType getPackageType() {
        return PackageType.nuget
    }
}
