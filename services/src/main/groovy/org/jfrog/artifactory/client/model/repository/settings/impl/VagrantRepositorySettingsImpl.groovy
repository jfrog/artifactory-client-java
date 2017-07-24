package org.jfrog.artifactory.client.model.repository.settings.impl

import groovy.transform.EqualsAndHashCode
import org.jfrog.artifactory.client.model.PackageType
import org.jfrog.artifactory.client.model.repository.settings.VagrantRepositorySettings

/**
 * GroovyBean implementation of the {@link VagrantRepositorySettings}
 * 
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
@EqualsAndHashCode
class VagrantRepositorySettingsImpl implements VagrantRepositorySettings {
    @Override
    public PackageType getPackageType() {
        return PackageType.vagrant
    }
}
