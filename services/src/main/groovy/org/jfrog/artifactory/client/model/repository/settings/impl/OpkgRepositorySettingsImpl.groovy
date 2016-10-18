package org.jfrog.artifactory.client.model.repository.settings.impl

import org.jfrog.artifactory.client.model.PackageType
import org.jfrog.artifactory.client.model.repository.settings.OpkgRepositorySettings

/**
 * GroovyBean implementation of the {@link OpkgRepositorySettings}
 * 
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */ 
class OpkgRepositorySettingsImpl extends AbstractRepositorySettings implements OpkgRepositorySettings {
    Boolean listRemoteFolderItems

    @Override
    public PackageType getPackageType() {
        return PackageType.opkg
    }
}
