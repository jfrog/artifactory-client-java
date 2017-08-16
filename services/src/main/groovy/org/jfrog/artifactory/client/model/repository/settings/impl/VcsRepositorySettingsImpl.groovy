package org.jfrog.artifactory.client.model.repository.settings.impl

import groovy.transform.EqualsAndHashCode
import org.jfrog.artifactory.client.model.PackageType
import org.jfrog.artifactory.client.model.impl.PackageTypeImpl
import org.jfrog.artifactory.client.model.repository.settings.AbstractRepositorySettings
import org.jfrog.artifactory.client.model.repository.settings.VcsRepositorySettings
import org.jfrog.artifactory.client.model.repository.settings.vcs.VcsGitProvider
import org.jfrog.artifactory.client.model.repository.settings.vcs.VcsType

/**
 * GroovyBean implementation of the {@link VcsRepositorySettings}
 * 
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
@EqualsAndHashCode
class VcsRepositorySettingsImpl extends AbstractRepositorySettings implements VcsRepositorySettings {
    VcsGitProvider vcsGitProvider
    VcsType vcsType
    Integer maxUniqueSnapshots
    String vcsGitDownloadUrl
    Boolean listRemoteFolderItems

    public VcsRepositorySettingsImpl() {
        this.repoLayoutRef = PackageTypeImpl.vcs.layout
    }

    @Override
    public PackageType getPackageType() {
        return PackageTypeImpl.vcs
    }
}
