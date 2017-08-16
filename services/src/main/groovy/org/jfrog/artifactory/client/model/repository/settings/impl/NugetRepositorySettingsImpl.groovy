package org.jfrog.artifactory.client.model.repository.settings.impl

import groovy.transform.EqualsAndHashCode
import org.jfrog.artifactory.client.model.PackageType
import org.jfrog.artifactory.client.model.impl.PackageTypeImpl
import org.jfrog.artifactory.client.model.repository.settings.AbstractRepositorySettings
import org.jfrog.artifactory.client.model.repository.settings.NugetRepositorySettings

/**
 * GroovyBean implementation of the {@link NugetRepositorySettings}
 * 
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
@EqualsAndHashCode
class NugetRepositorySettingsImpl extends AbstractRepositorySettings implements NugetRepositorySettings {
    Integer maxUniqueSnapshots
    Boolean forceNugetAuthentication
    String feedContextPath
    String downloadContextPath
    Boolean listRemoteFolderItems

    public NugetRepositorySettingsImpl() {
        this.repoLayoutRef = PackageTypeImpl.nuget.layout
    }

    @Override
    public PackageType getPackageType() {
        return PackageTypeImpl.nuget
    }
}
