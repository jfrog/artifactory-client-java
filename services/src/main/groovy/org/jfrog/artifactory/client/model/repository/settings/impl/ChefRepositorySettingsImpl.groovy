package org.jfrog.artifactory.client.model.repository.settings.impl

import groovy.transform.EqualsAndHashCode
import org.jfrog.artifactory.client.model.PackageType
import org.jfrog.artifactory.client.model.impl.PackageTypeImpl
import org.jfrog.artifactory.client.model.repository.settings.AbstractRepositorySettings
import org.jfrog.artifactory.client.model.repository.settings.ChefRepositorySettings

@EqualsAndHashCode
class ChefRepositorySettingsImpl extends AbstractRepositorySettings implements ChefRepositorySettings {

    public ChefRepositorySettingsImpl() {
        this.repoLayoutRef = PackageTypeImpl.chef.layout
    }

    @Override
    PackageType getPackageType() {
        return PackageTypeImpl.chef
    }
}
