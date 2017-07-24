package org.jfrog.artifactory.client.model.repository.settings.impl

import org.jfrog.artifactory.client.model.PackageType
import org.jfrog.artifactory.client.model.impl.CustomPackageTypeImpl
import org.jfrog.artifactory.client.model.repository.settings.CustomRepositorySettings

class CustomRepositorySettingsImpl implements CustomRepositorySettings {

    private PackageType packageType

    CustomRepositorySettingsImpl(CustomPackageTypeImpl packageType) {
        this.packageType = packageType
    }

    @Override
    PackageType getPackageType() {
        return packageType
    }
}
