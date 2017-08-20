package org.jfrog.artifactory.client.model.repository.settings.impl

import groovy.transform.EqualsAndHashCode
import org.jfrog.artifactory.client.model.PackageType
import org.jfrog.artifactory.client.model.impl.PackageTypeImpl
import org.jfrog.artifactory.client.model.repository.settings.AbstractRepositorySettings
import org.jfrog.artifactory.client.model.repository.settings.NpmRepositorySettings

/**
 * GroovyBean implementation of the {@link NpmRepositorySettings}
 * 
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
@EqualsAndHashCode
class NpmRepositorySettingsImpl extends AbstractRepositorySettings implements NpmRepositorySettings {
    static String defaultLayout = "npm-default"

    Boolean listRemoteFolderItems
    Boolean externalDependenciesEnabled
    Collection<String> externalDependenciesPatterns
    String externalDependenciesRemoteRepo

    public NpmRepositorySettingsImpl() {
        this.repoLayoutRef = defaultLayout
    }

    @Override
    public PackageType getPackageType() {
        return PackageTypeImpl.npm
    }
}
