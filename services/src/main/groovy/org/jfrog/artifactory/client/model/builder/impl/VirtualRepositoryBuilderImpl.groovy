package org.jfrog.artifactory.client.model.builder.impl

import org.jfrog.artifactory.client.model.RepositoryType
import org.jfrog.artifactory.client.model.VirtualRepository
import org.jfrog.artifactory.client.model.builder.VirtualRepositoryBuilder
import org.jfrog.artifactory.client.model.impl.RepositoryTypeImpl
import org.jfrog.artifactory.client.model.impl.VirtualRepositoryImpl

import static org.jfrog.artifactory.client.model.impl.PackageTypeImpl.*

/**
 *
 * @author jbaruch
 * @since 31/07/12
 */
class VirtualRepositoryBuilderImpl extends RepositoryBuilderBase<VirtualRepositoryBuilder, VirtualRepository> implements VirtualRepositoryBuilder {

    private VirtualRepositoryBuilderImpl() {
        super([bower, docker, gems, generic, gitlfs, gradle, ivy, maven, npm, nuget, p2, pypi, sbt, yum, rpm, composer,
               conan, chef, puppet] as Set)
    }

    private Collection<String> repositories = Collections.emptyList()
    private boolean artifactoryRequestsCanRetrieveRemoteArtifacts
    private String defaultDeploymentRepo

    VirtualRepositoryBuilder repositories(Collection<String> repositories) {
        this.repositories = repositories
        this
    }

    @Override
    Collection<String> getRepositories() {
        repositories
    }

    VirtualRepositoryBuilder artifactoryRequestsCanRetrieveRemoteArtifacts(boolean artifactoryRequestsCanRetrieveRemoteArtifacts) {
        this.artifactoryRequestsCanRetrieveRemoteArtifacts = artifactoryRequestsCanRetrieveRemoteArtifacts
        this
    }

    @Override
    boolean isArtifactoryRequestsCanRetrieveRemoteArtifacts() {
        artifactoryRequestsCanRetrieveRemoteArtifacts
    }

    VirtualRepositoryBuilder defaultDeploymentRepo(String deploymentRepo) {
        this.defaultDeploymentRepo = deploymentRepo
        this
    }

    @Override
    String getDefaultDeploymentRepo() {
        defaultDeploymentRepo
    }

    VirtualRepository build() {
        validate()
        new VirtualRepositoryImpl(key, settings, description, excludesPattern,
            includesPattern, notes, artifactoryRequestsCanRetrieveRemoteArtifacts,
            repositories, repoLayoutRef, defaultDeploymentRepo, customProperties)
    }

    @Override
    RepositoryType getRepositoryType() {
        return RepositoryTypeImpl.VIRTUAL
    }
}
