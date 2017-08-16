package org.jfrog.artifactory.client.model.repository.settings.impl

import groovy.transform.EqualsAndHashCode
import org.jfrog.artifactory.client.model.PackageType
import org.jfrog.artifactory.client.model.impl.PackageTypeImpl
import org.jfrog.artifactory.client.model.repository.settings.AbstractRepositorySettings
import org.jfrog.artifactory.client.model.repository.settings.PypiRepositorySettings

/**
 * GroovyBean implementation of the {@link PypiRepositorySettings}
 * 
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
@EqualsAndHashCode
class PypiRepositorySettingsImpl extends AbstractRepositorySettings implements PypiRepositorySettings {
    Boolean listRemoteFolderItems

    public PypiRepositorySettingsImpl() {
        this.repoLayoutRef = PackageTypeImpl.pypi.layout
    }

    @Override
    public PackageType getPackageType() {
        return PackageTypeImpl.pypi
    }
}
