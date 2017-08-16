package org.jfrog.artifactory.client.model.repository.settings.impl

import groovy.transform.EqualsAndHashCode
import org.jfrog.artifactory.client.model.PackageType
import org.jfrog.artifactory.client.model.impl.PackageTypeImpl
import org.jfrog.artifactory.client.model.repository.settings.AbstractRepositorySettings
import org.jfrog.artifactory.client.model.repository.settings.VagrantRepositorySettings

/**
 * GroovyBean implementation of the {@link VagrantRepositorySettings}
 * 
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
@EqualsAndHashCode
class VagrantRepositorySettingsImpl extends AbstractRepositorySettings implements VagrantRepositorySettings {

    public VagrantRepositorySettingsImpl() {
        this.repoLayoutRef = PackageTypeImpl.vagrant.layout
    }

    @Override
    public PackageType getPackageType() {
        return PackageTypeImpl.vagrant
    }
}
