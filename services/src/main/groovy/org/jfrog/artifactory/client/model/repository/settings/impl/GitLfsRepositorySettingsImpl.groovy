package org.jfrog.artifactory.client.model.repository.settings.impl

import groovy.transform.EqualsAndHashCode
import org.jfrog.artifactory.client.model.PackageType
import org.jfrog.artifactory.client.model.impl.PackageTypeImpl
import org.jfrog.artifactory.client.model.repository.settings.AbstractRepositorySettings
import org.jfrog.artifactory.client.model.repository.settings.GitLfsRepositorySettings

/**
 * GroovyBean implementation of the {@link GitLfsRepositorySettings}
 * 
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
@EqualsAndHashCode
class GitLfsRepositorySettingsImpl extends AbstractRepositorySettings implements GitLfsRepositorySettings {
    Boolean listRemoteFolderItems

    public GitLfsRepositorySettingsImpl() {
        this.repoLayoutRef = PackageTypeImpl.gitlfs.layout
    }

    @Override
    public PackageType getPackageType() {
        return PackageTypeImpl.gitlfs
    }
}
