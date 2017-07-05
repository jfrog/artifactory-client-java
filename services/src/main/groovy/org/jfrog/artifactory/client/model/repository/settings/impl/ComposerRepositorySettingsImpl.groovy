package org.jfrog.artifactory.client.model.repository.settings.impl

import groovy.transform.EqualsAndHashCode
import org.jfrog.artifactory.client.model.PackageType
import org.jfrog.artifactory.client.model.repository.settings.ComposerRepositorySettings

@EqualsAndHashCode
class ComposerRepositorySettingsImpl extends VcsRepositorySettingsImpl implements ComposerRepositorySettings {

    String composerRegistryUrl

    @Override
    PackageType getPackageType() {
      return PackageType.composer
    }
}
