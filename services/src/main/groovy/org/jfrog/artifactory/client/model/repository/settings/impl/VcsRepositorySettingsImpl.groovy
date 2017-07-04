package org.jfrog.artifactory.client.model.repository.settings.impl

import groovy.transform.EqualsAndHashCode
import org.jfrog.artifactory.client.model.PackageType
import org.jfrog.artifactory.client.model.repository.settings.VcsRepositorySettings
import org.jfrog.artifactory.client.model.repository.settings.vcs.VcsGitProvider
import org.jfrog.artifactory.client.model.repository.settings.vcs.VcsType

/**
 * GroovyBean implementation of the {@link VcsRepositorySettings}
 * 
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
@EqualsAndHashCode
class VcsRepositorySettingsImpl implements VcsRepositorySettings {
    VcsGitProvider vcsGitProvider
    VcsType vcsType
    Integer maxUniqueSnapshots
    String vcsGitDownloadUrl
    Boolean listRemoteFolderItems

    @Override
    public PackageType getPackageType() {
        return PackageType.vcs
    }
}
