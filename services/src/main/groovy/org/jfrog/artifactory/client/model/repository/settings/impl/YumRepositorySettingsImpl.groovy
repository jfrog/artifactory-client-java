package org.jfrog.artifactory.client.model.repository.settings.impl

import groovy.transform.EqualsAndHashCode
import org.jfrog.artifactory.client.model.PackageType
import org.jfrog.artifactory.client.model.impl.PackageTypeImpl
import org.jfrog.artifactory.client.model.repository.settings.AbstractRepositorySettings
import org.jfrog.artifactory.client.model.repository.settings.RpmRepositorySettings
import org.jfrog.artifactory.client.model.repository.settings.YumRepositorySettings

/**
 * GroovyBean implementation of the {@link YumRepositorySettings}
 *
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 * @deprecated since Artifactory 5.0.0. Replaced by {@link RpmRepositorySettings}.
 */
@Deprecated
@EqualsAndHashCode
class YumRepositorySettingsImpl extends AbstractRepositorySettings implements YumRepositorySettings {
    static String defaultLayout = "simple-default"

    Integer yumRootDepth
    String groupFileNames
    Boolean calculateYumMetadata
    Boolean listRemoteFolderItems

    YumRepositorySettingsImpl() {
        super(defaultLayout)
    }

    @Override
    PackageType getPackageType() {
        return PackageTypeImpl.yum
    }
}
