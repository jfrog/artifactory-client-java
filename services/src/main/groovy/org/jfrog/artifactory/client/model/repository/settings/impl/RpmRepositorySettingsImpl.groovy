package org.jfrog.artifactory.client.model.repository.settings.impl

import groovy.transform.EqualsAndHashCode
import org.jfrog.artifactory.client.model.PackageType
import org.jfrog.artifactory.client.model.repository.settings.RpmRepositorySettings

/**
 * GroovyBean implementation of the {@link RpmRepositorySettings}
 * 
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
@EqualsAndHashCode
class RpmRepositorySettingsImpl implements RpmRepositorySettings {
    Integer yumRootDepth
    String groupFileNames
    Boolean calculateYumMetadata
    Boolean enableFileListsIndexing
    Boolean listRemoteFolderItems

    @Override
    public PackageType getPackageType() {
        return PackageType.rpm
    }
}
