package org.jfrog.artifactory.client.model.builder.impl

import org.jfrog.artifactory.client.model.RepositoryType
import org.jfrog.artifactory.client.model.VirtualRepository
import org.jfrog.artifactory.client.model.builder.VirtualRepositoryBuilder
import org.jfrog.artifactory.client.model.impl.RepositoryTypeImpl
import org.jfrog.artifactory.client.model.impl.VirtualRepositoryImpl

import static org.jfrog.artifactory.client.model.VirtualRepository.PomRepositoryReferencesCleanupPolicy.discard_active_reference
import static org.jfrog.artifactory.client.model.PackageType.*

/**
 *
 * @author jbaruch
 * @since 31/07/12
 */
class VirtualRepositoryBuilderImpl extends RepositoryBuilderBase<VirtualRepositoryBuilder, VirtualRepository> implements VirtualRepositoryBuilder {

    private VirtualRepositoryBuilderImpl() {
        super([maven, gradle, ivy, sbt, nuget, gems, npm, bower, pypi, p2, generic, docker])
    }

    private List<String> repositories = new ArrayList<String>()
    private boolean artifactoryRequestsCanRetrieveRemoteArtifacts
    private String keyPair
    private VirtualRepository.PomRepositoryReferencesCleanupPolicy pomRepositoryReferencesCleanupPolicy = discard_active_reference

    VirtualRepositoryBuilder repositories(List<String> repositories) {
        this.repositories = repositories
        this
    }

    VirtualRepositoryBuilder artifactoryRequestsCanRetrieveRemoteArtifacts(boolean artifactoryRequestsCanRetrieveRemoteArtifacts) {
        this.artifactoryRequestsCanRetrieveRemoteArtifacts = artifactoryRequestsCanRetrieveRemoteArtifacts
        this
    }

    VirtualRepositoryBuilder keyPair(String keyPair) {
        this.keyPair = keyPair
        this
    }

    VirtualRepositoryBuilder pomRepositoryReferencesCleanupPolicy(VirtualRepository.PomRepositoryReferencesCleanupPolicy pomRepositoryReferencesCleanupPolicy) {
        this.pomRepositoryReferencesCleanupPolicy = pomRepositoryReferencesCleanupPolicy
        this
    }

    VirtualRepository build() {
        validate()
        new VirtualRepositoryImpl(key, packageType, description, excludesPattern,
            includesPattern, notes, artifactoryRequestsCanRetrieveRemoteArtifacts,
            keyPair, pomRepositoryReferencesCleanupPolicy, repositories,
            repoLayoutRef, debianTrivialLayout)
    }

    @Override
    RepositoryType getRepositoryType() {
        return RepositoryTypeImpl.VIRTUAL
    }
}
