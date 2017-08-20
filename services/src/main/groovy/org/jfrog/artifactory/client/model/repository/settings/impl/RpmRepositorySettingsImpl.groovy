package org.jfrog.artifactory.client.model.repository.settings.impl

import groovy.transform.EqualsAndHashCode
import org.jfrog.artifactory.client.model.PackageType
import org.jfrog.artifactory.client.model.impl.PackageTypeImpl
import org.jfrog.artifactory.client.model.repository.settings.AbstractRepositorySettings
import org.jfrog.artifactory.client.model.repository.settings.RpmRepositorySettings

/**
 * GroovyBean implementation of the {@link RpmRepositorySettings}
 * 
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
@EqualsAndHashCode
class RpmRepositorySettingsImpl extends AbstractRepositorySettings implements RpmRepositorySettings {
    static String defaultLayout = "simple-default"

    Integer yumRootDepth
    String groupFileNames
    Boolean calculateYumMetadata
    Boolean enableFileListsIndexing
    Boolean listRemoteFolderItems

    public RpmRepositorySettingsImpl() {
        this.repoLayoutRef = defaultLayout
    }

    @Override
    public PackageType getPackageType() {
        return PackageTypeImpl.rpm
    }
}
