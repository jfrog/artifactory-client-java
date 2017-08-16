package org.jfrog.artifactory.client.model.repository.settings.impl

import groovy.transform.EqualsAndHashCode
import org.jfrog.artifactory.client.model.PackageType
import org.jfrog.artifactory.client.model.impl.PackageTypeImpl
import org.jfrog.artifactory.client.model.repository.settings.ComposerRepositorySettings

@EqualsAndHashCode
class ComposerRepositorySettingsImpl extends VcsRepositorySettingsImpl implements ComposerRepositorySettings {

    String composerRegistryUrl

    public ComposerRepositorySettingsImpl() {
        this.repoLayoutRef = PackageTypeImpl.composer.layout
    }

    @Override
    PackageType getPackageType() {
      return PackageTypeImpl.composer
    }
}
