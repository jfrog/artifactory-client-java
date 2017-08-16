package org.jfrog.artifactory.client.model.repository.settings.impl

import groovy.transform.EqualsAndHashCode
import org.jfrog.artifactory.client.model.PackageType
import org.jfrog.artifactory.client.model.impl.PackageTypeImpl
import org.jfrog.artifactory.client.model.repository.settings.CocoaPodsRepositorySettings

/**
 * GroovyBean implementation of the {@link CocoaPodsRepositorySettings}
 * 
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
@EqualsAndHashCode
class CocoaPodsRepositorySettingsImpl extends VcsRepositorySettingsImpl implements CocoaPodsRepositorySettings {
    String podsSpecsRepoUrl

    public CocoaPodsRepositorySettingsImpl() {
        this.repoLayoutRef = PackageTypeImpl.cocoapods.layout
    }

    @Override
    public PackageType getPackageType() {
        return PackageTypeImpl.cocoapods
    }
}
