package org.jfrog.artifactory.client.model.repository.settings.impl

import groovy.transform.EqualsAndHashCode
import org.jfrog.artifactory.client.model.PackageType
import org.jfrog.artifactory.client.model.impl.PackageTypeImpl
import org.jfrog.artifactory.client.model.repository.settings.ChefRepositorySettings

@EqualsAndHashCode
class ChefRepositorySettingsImpl implements ChefRepositorySettings {

    @Override
    PackageType getPackageType() {
        return PackageTypeImpl.chef
    }
}
