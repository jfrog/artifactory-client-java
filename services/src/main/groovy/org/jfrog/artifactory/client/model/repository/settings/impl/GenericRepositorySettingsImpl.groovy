package org.jfrog.artifactory.client.model.repository.settings.impl;

import org.jfrog.artifactory.client.model.PackageType
import org.jfrog.artifactory.client.model.repository.settings.GenericRepositorySettings

/**
 * GroovyBean implementation of the {@link GenericRepositorySettings}
 * 
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */ 
class GenericRepositorySettingsImpl implements GenericRepositorySettings {
    Boolean listRemoteFolderItems

    @Override
    public PackageType getPackageType() {
        return PackageType.generic;
    }
}
