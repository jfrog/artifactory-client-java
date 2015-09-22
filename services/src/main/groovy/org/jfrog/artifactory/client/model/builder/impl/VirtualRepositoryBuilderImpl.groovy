package org.jfrog.artifactory.client.model.builder.impl

import org.jfrog.artifactory.client.model.VirtualRepository
import org.jfrog.artifactory.client.model.builder.VirtualRepositoryBuilder
import org.jfrog.artifactory.client.model.impl.VirtualRepositoryImpl

import static org.jfrog.artifactory.client.model.VirtualRepository.PomRepositoryReferencesCleanupPolicy.discard_active_reference

/**
 *
 * @author jbaruch
 * @since 31/07/12
 */
class VirtualRepositoryBuilderImpl extends RepositoryBuilderBase<VirtualRepositoryBuilder, VirtualRepository> implements VirtualRepositoryBuilder {

    private VirtualRepositoryBuilderImpl() { }

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
        new VirtualRepositoryImpl(description, excludesPattern, includesPattern, key, notes, artifactoryRequestsCanRetrieveRemoteArtifacts, keyPair, pomRepositoryReferencesCleanupPolicy, repositories, repoLayoutRef, packageType, enableNuGetSupport, enableGemsSupport, enableNpmSupport, enableVagrantSupport, enableBowerSupport, enableGitLfsSupport, enableDebianSupport, enableDockerSupport, enablePypiSupport, debianTrivialLayout)
    }
}
