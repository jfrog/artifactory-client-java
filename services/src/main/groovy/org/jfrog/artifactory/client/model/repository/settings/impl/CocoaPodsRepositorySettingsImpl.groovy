package org.jfrog.artifactory.client.model.repository.settings.impl

import org.jfrog.artifactory.client.model.PackageType
import org.jfrog.artifactory.client.model.repository.settings.CocoaPodsRepositorySettings

/**
 * GroovyBean implementation of the {@link CocoaPodsRepositorySettings}
 * 
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
class CocoaPodsRepositorySettingsImpl extends VcsRepositorySettingsImpl implements CocoaPodsRepositorySettings {
    String podsSpecsRepoUrl

    @Override
    public PackageType getPackageType() {
        return PackageType.cocoapods
    }
}
