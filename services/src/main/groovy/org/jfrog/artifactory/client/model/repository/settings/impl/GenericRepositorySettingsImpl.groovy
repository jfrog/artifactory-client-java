package org.jfrog.artifactory.client.model.repository.settings.impl

import groovy.transform.EqualsAndHashCode
import org.jfrog.artifactory.client.model.PackageType
import org.jfrog.artifactory.client.model.impl.PackageTypeImpl
import org.jfrog.artifactory.client.model.repository.settings.AbstractRepositorySettings
import org.jfrog.artifactory.client.model.repository.settings.GenericRepositorySettings

/**
 * GroovyBean implementation of the {@link GenericRepositorySettings}
 * 
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
@EqualsAndHashCode
class GenericRepositorySettingsImpl extends AbstractRepositorySettings implements GenericRepositorySettings {
    Boolean listRemoteFolderItems

    public GenericRepositorySettingsImpl() {
        this.repoLayoutRef = PackageTypeImpl.generic.layout
    }

    @Override
    public PackageType getPackageType() {
        return PackageTypeImpl.generic
    }
}
