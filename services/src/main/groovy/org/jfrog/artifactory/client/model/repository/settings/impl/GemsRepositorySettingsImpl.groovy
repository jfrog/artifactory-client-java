package org.jfrog.artifactory.client.model.repository.settings.impl

import org.jfrog.artifactory.client.model.PackageType
import org.jfrog.artifactory.client.model.repository.settings.GemsRepositorySettings

/**
 * GroovyBean implementation of the {@link GemsRepositorySettings}
 * 
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */ 
class GemsRepositorySettingsImpl extends AbstractXraySettings implements GemsRepositorySettings {
    Boolean listRemoteFolderItems

    @Override
    public PackageType getPackageType() {
        return PackageType.gems
    }
}
