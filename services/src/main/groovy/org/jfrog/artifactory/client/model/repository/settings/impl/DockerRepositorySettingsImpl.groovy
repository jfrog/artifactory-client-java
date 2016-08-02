package org.jfrog.artifactory.client.model.repository.settings.impl

import org.jfrog.artifactory.client.model.PackageType
import org.jfrog.artifactory.client.model.repository.settings.DockerRepositorySettings
import org.jfrog.artifactory.client.model.repository.settings.docker.DockerApiVersion

/**
 * GroovyBean implementation of the {@link DockerRepositorySettings}
 *
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
public class DockerRepositorySettingsImpl implements DockerRepositorySettings{
    DockerApiVersion dockerApiVersion
    Boolean enableTokenAuthentication
    Boolean listRemoteFolderItems

    @Override
    public PackageType getPackageType() {
        return PackageType.docker
    }
}
