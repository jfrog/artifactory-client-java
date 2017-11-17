package org.jfrog.artifactory.client.model.repository.settings.impl

import groovy.transform.EqualsAndHashCode
import org.jfrog.artifactory.client.model.PackageType
import org.jfrog.artifactory.client.model.impl.PackageTypeImpl
import org.jfrog.artifactory.client.model.repository.settings.IvyRepositorySettings

/**
 * GroovyBean implementation of the {@link IvyRepositorySettings}
 * 
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
@EqualsAndHashCode
class IvyRepositorySettingsImpl extends MavenRepositorySettingsImpl implements IvyRepositorySettings {
    static String defaultLayout = "ivy-default"

  IvyRepositorySettingsImpl() {
        super(defaultLayout)
    }

    @Override
    PackageType getPackageType() {
        return PackageTypeImpl.ivy
    }
}
