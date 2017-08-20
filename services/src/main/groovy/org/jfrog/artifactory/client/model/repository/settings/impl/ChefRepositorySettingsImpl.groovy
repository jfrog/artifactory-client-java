package org.jfrog.artifactory.client.model.repository.settings.impl

import groovy.transform.EqualsAndHashCode
import org.jfrog.artifactory.client.model.PackageType
import org.jfrog.artifactory.client.model.impl.PackageTypeImpl
import org.jfrog.artifactory.client.model.repository.settings.AbstractRepositorySettings
import org.jfrog.artifactory.client.model.repository.settings.ChefRepositorySettings

@EqualsAndHashCode
class ChefRepositorySettingsImpl extends AbstractRepositorySettings implements ChefRepositorySettings {
    static defaultLayout = "simple-default"

    public ChefRepositorySettingsImpl() {
        this.repoLayoutRef = defaultLayout
    }

    @Override
    PackageType getPackageType() {
        return PackageTypeImpl.chef
    }
}
