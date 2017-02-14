package org.jfrog.artifactory.client.model.repository.settings.impl

import org.jfrog.artifactory.client.model.PackageType
import org.jfrog.artifactory.client.model.repository.settings.PuppetRepositorySettings

class PuppetRepositorySettingsImpl implements PuppetRepositorySettings {

    @Override
    PackageType getPackageType() {
        return PackageType.puppet
    }
}
