package org.jfrog.artifactory.client.model.repository.settings.impl

import groovy.transform.EqualsAndHashCode
import org.jfrog.artifactory.client.model.PackageType
import org.jfrog.artifactory.client.model.impl.PackageTypeImpl
import org.jfrog.artifactory.client.model.repository.settings.GradleRepositorySettings

/**
 * GroovyBean implementation of the {@link GradleRepositorySettings}
 * 
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
@EqualsAndHashCode
class GradleRepositorySettingsImpl extends MavenRepositorySettingsImpl implements GradleRepositorySettings {

    GradleRepositorySettingsImpl() {
        this.repoLayoutRef = PackageTypeImpl.gradle.layout
    }

    @Override
    public PackageType getPackageType() {
        return PackageTypeImpl.gradle
    }

    @Override
    public String getRepoLayout() {
        return this.repoLayoutRef
    }
}
