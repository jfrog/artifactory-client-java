package org.jfrog.artifactory.client.model.repository.settings.impl

import org.jfrog.artifactory.client.model.PackageType
import org.jfrog.artifactory.client.model.repository.settings.PypiRepositorySettings

/**
 * GroovyBean implementation of the {@link PypiRepositorySettings}
 * 
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */ 
class PypiRepositorySettingsImpl extends AbstractXraySettings implements PypiRepositorySettings {
    Boolean listRemoteFolderItems

    @Override
    public PackageType getPackageType() {
        return PackageType.pypi
    }
}
