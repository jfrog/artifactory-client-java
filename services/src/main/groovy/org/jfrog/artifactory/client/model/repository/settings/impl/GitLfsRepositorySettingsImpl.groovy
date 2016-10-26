package org.jfrog.artifactory.client.model.repository.settings.impl

import org.jfrog.artifactory.client.model.PackageType
import org.jfrog.artifactory.client.model.repository.settings.GitLfsRepositorySettings

/**
 * GroovyBean implementation of the {@link GitLfsRepositorySettings}
 * 
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */ 
class GitLfsRepositorySettingsImpl extends AbstractXraySettings implements GitLfsRepositorySettings {
    Boolean listRemoteFolderItems

    @Override
    public PackageType getPackageType() {
        return PackageType.gitlfs
    }
}
