package org.jfrog.artifactory.client.model.repository.settings.impl

import groovy.transform.EqualsAndHashCode
import org.jfrog.artifactory.client.model.PackageType
import org.jfrog.artifactory.client.model.impl.PackageTypeImpl
import org.jfrog.artifactory.client.model.repository.settings.ComposerRepositorySettings

@EqualsAndHashCode
class ComposerRepositorySettingsImpl extends VcsRepositorySettingsImpl implements ComposerRepositorySettings {
    static defaultLayout = "composer-default"

    String composerRegistryUrl

    ComposerRepositorySettingsImpl() {
        super(defaultLayout)
    }

    @Override
    PackageType getPackageType() {
      return PackageTypeImpl.composer
    }
}
