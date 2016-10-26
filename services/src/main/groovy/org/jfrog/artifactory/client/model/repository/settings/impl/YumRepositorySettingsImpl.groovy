package org.jfrog.artifactory.client.model.repository.settings.impl

import org.jfrog.artifactory.client.model.PackageType
import org.jfrog.artifactory.client.model.repository.settings.YumRepositorySettings

/**
 * GroovyBean implementation of the {@link YumRepositorySettings}
 * 
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
class YumRepositorySettingsImpl extends AbstractXraySettings implements YumRepositorySettings {
    Integer yumRootDepth
    String groupFileNames
    Boolean calculateYumMetadata
    Boolean listRemoteFolderItems

    @Override
    public PackageType getPackageType() {
        return PackageType.yum
    }
}
