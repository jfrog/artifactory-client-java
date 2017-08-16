package org.jfrog.artifactory.client.model.repository.settings.impl

import groovy.transform.EqualsAndHashCode
import org.jfrog.artifactory.client.model.PackageType
import org.jfrog.artifactory.client.model.impl.PackageTypeImpl
import org.jfrog.artifactory.client.model.repository.settings.AbstractRepositorySettings
import org.jfrog.artifactory.client.model.repository.settings.ConanRepositorySettings

@EqualsAndHashCode
class ConanRepositorySettingsImpl extends AbstractRepositorySettings implements ConanRepositorySettings {

    public ConanRepositorySettingsImpl() {
        this.repoLayoutRef = PackageTypeImpl.conan.layout
    }

    @Override
    PackageType getPackageType() {
        return PackageTypeImpl.conan
    }
}
