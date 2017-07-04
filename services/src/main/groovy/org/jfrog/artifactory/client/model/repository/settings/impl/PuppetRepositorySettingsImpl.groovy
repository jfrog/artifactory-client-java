package org.jfrog.artifactory.client.model.repository.settings.impl

import groovy.transform.EqualsAndHashCode
import org.jfrog.artifactory.client.model.PackageType
import org.jfrog.artifactory.client.model.repository.settings.PuppetRepositorySettings

@EqualsAndHashCode
class PuppetRepositorySettingsImpl implements PuppetRepositorySettings {

    @Override
    PackageType getPackageType() {
        return PackageType.puppet
    }
}
