package org.jfrog.artifactory.client.model.repository.settings.impl

import groovy.transform.EqualsAndHashCode
import org.jfrog.artifactory.client.model.PackageType
import org.jfrog.artifactory.client.model.repository.settings.OpkgRepositorySettings

/**
 * GroovyBean implementation of the {@link OpkgRepositorySettings}
 * 
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
@EqualsAndHashCode
class OpkgRepositorySettingsImpl implements OpkgRepositorySettings {
    Boolean listRemoteFolderItems

    @Override
    public PackageType getPackageType() {
        return PackageType.opkg
    }
}
