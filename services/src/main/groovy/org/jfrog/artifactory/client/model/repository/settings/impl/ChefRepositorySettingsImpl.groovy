package org.jfrog.artifactory.client.model.repository.settings.impl

import org.jfrog.artifactory.client.model.PackageType
import org.jfrog.artifactory.client.model.repository.settings.ChefRepositorySettings

class ChefRepositorySettingsImpl implements ChefRepositorySettings {

    @Override
    PackageType getPackageType() {
        return PackageType.chef
    }
}
