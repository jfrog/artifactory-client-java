package org.jfrog.artifactory.client.model.repository.settings.impl

import groovy.transform.EqualsAndHashCode
import org.jfrog.artifactory.client.model.PackageType
import org.jfrog.artifactory.client.model.impl.PackageTypeImpl
import org.jfrog.artifactory.client.model.repository.settings.ConanRepositorySettings

@EqualsAndHashCode
class ConanRepositorySettingsImpl implements ConanRepositorySettings {

    @Override
    PackageType getPackageType() {
        return PackageTypeImpl.conan
    }
}
