package org.jfrog.artifactory.client.model.repository.settings.impl

import groovy.transform.EqualsAndHashCode
import org.jfrog.artifactory.client.model.PackageType
import org.jfrog.artifactory.client.model.impl.PackageTypeImpl
import org.jfrog.artifactory.client.model.repository.settings.SbtRepositorySettings

/**
 * GroovyBean implementation of the {@link SbtRepositorySettings}
 * 
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
@EqualsAndHashCode
class SbtRepositorySettingsImpl extends MavenRepositorySettingsImpl implements SbtRepositorySettings {

    public SbtRepositorySettingsImpl() {
        this.repoLayoutRef = PackageTypeImpl.sbt.layout
    }

    @Override
    public PackageType getPackageType() {
        return PackageTypeImpl.sbt
    }
}
