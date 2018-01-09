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
    static String defaultLayout = "nuget-default"

    Integer maxUniqueSnapshots
    Boolean forceNugetAuthentication
    String feedContextPath
    String downloadContextPath
    Boolean listRemoteFolderItems

    NugetRepositorySettingsImpl() {
        super(defaultLayout)
    }

    @Override
    PackageType getPackageType() {
        return PackageTypeImpl.nuget
    }
}
