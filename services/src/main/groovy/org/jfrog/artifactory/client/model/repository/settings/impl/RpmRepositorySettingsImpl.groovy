package org.jfrog.artifactory.client.model.repository.settings.impl

import org.jfrog.artifactory.client.model.PackageType
import org.jfrog.artifactory.client.model.repository.settings.RpmRepositorySettings

/**
 * GroovyBean implementation of the {@link RpmRepositorySettings}
 * 
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
class RpmRepositorySettingsImpl implements RpmRepositorySettings {
    Integer yumRootDepth
    String groupFileNames
    Boolean calculateYumMetadata
    Boolean listRemoteFolderItems
    Boolean maintainFilelistsMetadata

    @Override
    public PackageType getPackageType() {
        return PackageType.rpm
    }
}
