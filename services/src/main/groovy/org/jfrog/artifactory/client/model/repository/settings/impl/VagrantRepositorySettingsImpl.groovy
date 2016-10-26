package org.jfrog.artifactory.client.model.repository.settings.impl

import org.jfrog.artifactory.client.model.PackageType
import org.jfrog.artifactory.client.model.repository.settings.VagrantRepositorySettings

/**
 * GroovyBean implementation of the {@link VagrantRepositorySettings}
 * 
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */ 
class VagrantRepositorySettingsImpl extends AbstractXraySettings implements VagrantRepositorySettings {
    @Override
    public PackageType getPackageType() {
        return PackageType.vagrant
    }
}
