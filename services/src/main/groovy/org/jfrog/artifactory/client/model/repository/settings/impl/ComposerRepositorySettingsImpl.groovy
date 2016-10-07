package org.jfrog.artifactory.client.model.repository.settings.impl

import org.jfrog.artifactory.client.model.PackageType
import org.jfrog.artifactory.client.model.repository.settings.ComposerRepositorySettings

class ComposerRepositorySettingsImpl implements ComposerRepositorySettings {

    @Override
    PackageType getPackageType() {
      return PackageType.composer;
    }
}
