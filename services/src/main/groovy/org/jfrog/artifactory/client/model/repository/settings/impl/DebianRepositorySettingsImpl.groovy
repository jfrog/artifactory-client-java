package org.jfrog.artifactory.client.model.repository.settings.impl

import groovy.transform.EqualsAndHashCode
import org.jfrog.artifactory.client.model.PackageType
import org.jfrog.artifactory.client.model.impl.PackageTypeImpl
import org.jfrog.artifactory.client.model.repository.settings.AbstractRepositorySettings
import org.jfrog.artifactory.client.model.repository.settings.DebianRepositorySettings

/**
 * GroovyBean implementation of the {@link DebianRepositorySettings}
 * 
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
@EqualsAndHashCode
class DebianRepositorySettingsImpl extends AbstractRepositorySettings implements DebianRepositorySettings {
    Boolean debianTrivialLayout
    Boolean listRemoteFolderItems

    public DebianRepositorySettingsImpl() {
        this.repoLayoutRef = PackageTypeImpl.debian.layout
    }

    @Override
    public PackageType getPackageType() {
        return PackageTypeImpl.debian
    }
}
