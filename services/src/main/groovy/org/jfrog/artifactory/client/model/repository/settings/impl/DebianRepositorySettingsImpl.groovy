package org.jfrog.artifactory.client.model.repository.settings.impl

import org.jfrog.artifactory.client.model.PackageType
import org.jfrog.artifactory.client.model.repository.settings.DebianRepositorySettings

/**
 * GroovyBean implementation of the {@link DebianRepositorySettings}
 * 
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
class DebianRepositorySettingsImpl extends AbstractRepositorySettings implements DebianRepositorySettings {
    Boolean debianTrivialLayout
    Boolean listRemoteFolderItems

    @Override
    public PackageType getPackageType() {
        return PackageType.debian
    }
}
