package org.jfrog.artifactory.client.model.builder.impl

import org.jfrog.artifactory.client.model.RepositoryType
import org.jfrog.artifactory.client.model.VirtualRepository
import org.jfrog.artifactory.client.model.builder.VirtualRepositoryBuilder
import org.jfrog.artifactory.client.model.impl.RepositoryTypeImpl
import org.jfrog.artifactory.client.model.impl.VirtualRepositoryImpl

import static org.jfrog.artifactory.client.model.PackageType.*

/**
 *
 * @author jbaruch
 * @since 31/07/12
 */
class VirtualRepositoryBuilderImpl extends RepositoryBuilderBase<VirtualRepositoryBuilder, VirtualRepository> implements VirtualRepositoryBuilder {

    private VirtualRepositoryBuilderImpl() {
        super([bower, docker, gems, generic, gitlfs, gradle, ivy, maven, npm, nuget, p2, pypi, sbt, yum, composer,
               conan])
    }

    private Collection<String> repositories = Collections.emptyList();
    private boolean artifactoryRequestsCanRetrieveRemoteArtifacts
    private String defaultDeploymentRepo;

    VirtualRepositoryBuilder repositories(Collection<String> repositories) {
        this.repositories = repositories
        this
    }

    VirtualRepositoryBuilder artifactoryRequestsCanRetrieveRemoteArtifacts(boolean artifactoryRequestsCanRetrieveRemoteArtifacts) {
        this.artifactoryRequestsCanRetrieveRemoteArtifacts = artifactoryRequestsCanRetrieveRemoteArtifacts
        this
    }

    VirtualRepositoryBuilder defaultDeploymentRepo(String deploymentRepo) {
        this.defaultDeploymentRepo = deploymentRepo
        this
    }

    VirtualRepository build() {
        validate()
        new VirtualRepositoryImpl(key, settings, description, excludesPattern,
            includesPattern, notes, artifactoryRequestsCanRetrieveRemoteArtifacts,
            repositories, repoLayoutRef, defaultDeploymentRepo)
    }

    @Override
    RepositoryType getRepositoryType() {
        return RepositoryTypeImpl.VIRTUAL
    }
}
