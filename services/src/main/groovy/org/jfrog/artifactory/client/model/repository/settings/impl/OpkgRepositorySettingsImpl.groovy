package org.jfrog.artifactory.client.model.repository.settings.impl

import groovy.transform.EqualsAndHashCode
import org.jfrog.artifactory.client.model.PackageType
import org.jfrog.artifactory.client.model.impl.PackageTypeImpl
import org.jfrog.artifactory.client.model.repository.settings.AbstractRepositorySettings
import org.jfrog.artifactory.client.model.repository.settings.OpkgRepositorySettings

/**
 * GroovyBean implementation of the {@link OpkgRepositorySettings}
 * 
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
@EqualsAndHashCode
class OpkgRepositorySettingsImpl extends AbstractRepositorySettings implements OpkgRepositorySettings {
    static String defaultLayout = "simple-default"

    Boolean listRemoteFolderItems

  OpkgRepositorySettingsImpl() {
        super(defaultLayout)
    }

    @Override
    PackageType getPackageType() {
        return PackageTypeImpl.opkg
    }
}
