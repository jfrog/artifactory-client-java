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
    static String defaultLayout = "gradle-default"

    GradleRepositorySettingsImpl() {
        super(defaultLayout)
    }

    @Override
    PackageType getPackageType() {
        return PackageTypeImpl.gradle
    }
}
