package org.jfrog.artifactory.client.model.repository.settings.impl

import org.jfrog.artifactory.client.model.PackageType
import org.jfrog.artifactory.client.model.repository.settings.VcsRepositorySettings
import org.jfrog.artifactory.client.model.repository.settings.vcs.VcsGitProvider
import org.jfrog.artifactory.client.model.repository.settings.vcs.VcsType

/**
 * GroovyBean implementation of the {@link VcsRepositorySettings}
 * 
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
class VcsRepositorySettingsImpl extends AbstractRepositorySettings implements VcsRepositorySettings {
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
