package org.jfrog.artifactory.client.model.repository.settings.impl

import groovy.transform.EqualsAndHashCode
import org.jfrog.artifactory.client.model.PackageType
import org.jfrog.artifactory.client.model.impl.PackageTypeImpl
import org.jfrog.artifactory.client.model.repository.settings.AbstractRepositorySettings
import org.jfrog.artifactory.client.model.repository.settings.PuppetRepositorySettings

@EqualsAndHashCode
class PuppetRepositorySettingsImpl extends AbstractRepositorySettings implements PuppetRepositorySettings {
    static String defaultLayout = "puppet-default"

    public PuppetRepositorySettingsImpl() {
        this.repoLayoutRef = defaultLayout
    }

    @Override
    PackageType getPackageType() {
        return PackageTypeImpl.puppet
    }
}
