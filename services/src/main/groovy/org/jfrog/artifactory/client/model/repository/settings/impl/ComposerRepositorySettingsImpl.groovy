package org.jfrog.artifactory.client.model.repository.settings.impl

import org.jfrog.artifactory.client.model.PackageType
import org.jfrog.artifactory.client.model.repository.settings.ComposerRepositorySettings

class ComposerRepositorySettingsImpl extends VcsRepositorySettingsImpl implements ComposerRepositorySettings {

    String composerRegistryUrl

    @Override
    PackageType getPackageType() {
      return PackageType.composer;
    }
}
