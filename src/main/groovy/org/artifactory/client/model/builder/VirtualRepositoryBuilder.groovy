package org.artifactory.client.model.builder

import org.artifactory.client.model.VirtualRepository

import static org.artifactory.client.model.VirtualRepository.PomRepositoryReferencesCleanupPolicy.discard_active_reference
import org.artifactory.client.model.impl.VirtualRepositoryImpl

/**
 * 
 * @author jbaruch
 * @since 31/07/12
 */
class VirtualRepositoryBuilder {

    private VirtualRepositoryBuilder() { }

    private String description
    private String excludesPattern
    private String includesPattern = '**/*'
    private String key
    private String notes
    private List<String> repositories = new ArrayList<String>()
    private boolean artifactoryRequestsCanRetrieveRemoteArtifacts
    private String keyPair
    private VirtualRepository.PomRepositoryReferencesCleanupPolicy pomRepositoryReferencesCleanupPolicy = discard_active_reference

    VirtualRepositoryBuilder description(String description) {
        this.description = description
        this
    }

    VirtualRepositoryBuilder excludesPattern(String excludesPattern) {
        this.excludesPattern = excludesPattern
        this
    }

    VirtualRepositoryBuilder includesPattern(String includesPattern) {
        this.includesPattern = includesPattern
        this
    }

    VirtualRepositoryBuilder key(String key) {
        this.key = key
        this
    }

    VirtualRepositoryBuilder notes(String notes) {
        this.notes = notes
        this
    }

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
        return new VirtualRepositoryImpl(description, excludesPattern, includesPattern, key, notes, artifactoryRequestsCanRetrieveRemoteArtifacts, keyPair, pomRepositoryReferencesCleanupPolicy, repositories)
    }
}
