package org.artifactory.client.model.builder

import org.artifactory.client.model.LocalRepository

import static org.artifactory.client.model.LocalRepository.ChecksumPolicyType.client_checksums
import org.artifactory.client.model.NonVirtualRepository

import static org.artifactory.client.model.NonVirtualRepository.SnapshotVersionBehavior.non_unique
import org.artifactory.client.model.impl.LocalRepositoryImpl

/**
 * 
 * @author jbaruch
 * @since 31/07/12
 */
class LocalRepositoryBuilder {

    private LocalRepositoryBuilder() { }

    private LocalRepository.ChecksumPolicyType checksumPolicyType = client_checksums
    private String description
    private String excludesPattern
    private String includesPattern = '**/*'
    private String key
    private String notes
    private boolean blackedOut = false
    private boolean handleReleases = true
    private boolean handleSnapshots = true
    private int maxUniqueSnapshots = 0
    private List<String> propertySets = new ArrayList<String>()
    private NonVirtualRepository.SnapshotVersionBehavior snapshotVersionBehavior = non_unique
    private boolean suppressPomConsistencyChecks = false
    private String repoLayoutRef

    LocalRepositoryBuilder blackedOut(boolean blackedOut) {
        this.blackedOut = blackedOut
        this
    }

    LocalRepositoryBuilder checksumPolicyType(LocalRepository.ChecksumPolicyType checksumPolicyType) {
        this.checksumPolicyType = checksumPolicyType
        this
    }

    LocalRepositoryBuilder description(String description) {
        this.description = description
        this
    }

    LocalRepositoryBuilder excludesPattern(String excludesPattern) {
        this.excludesPattern = excludesPattern
        this
    }

    LocalRepositoryBuilder handleReleases(boolean handleReleases) {
        this.handleReleases = handleReleases
        this
    }

    LocalRepositoryBuilder handleSnapshots(boolean handleSnapshots) {
        this.handleSnapshots = handleSnapshots
        this
    }

    LocalRepositoryBuilder includesPattern(String includesPattern) {
        this.includesPattern = includesPattern
        this
    }

    LocalRepositoryBuilder key(String key) {
        this.key = key
        this
    }

    LocalRepositoryBuilder maxUniqueSnapshots(int maxUniqueSnapshots) {
        this.maxUniqueSnapshots = maxUniqueSnapshots
        this
    }

    LocalRepositoryBuilder notes(String notes) {
        this.notes = notes
        this
    }

    LocalRepositoryBuilder propertySets(List<String> propertySets) {
        this.propertySets = propertySets
        this
    }

    LocalRepositoryBuilder snapshotVersionBehavior(NonVirtualRepository.SnapshotVersionBehavior snapshotVersionBehavior) {
        this.snapshotVersionBehavior = snapshotVersionBehavior
        this
    }

    LocalRepositoryBuilder suppressPomConsistencyChecks(boolean suppressPomConsistencyChecks) {
        this.suppressPomConsistencyChecks = suppressPomConsistencyChecks
        this
    }

    LocalRepositoryBuilder repoLayoutRef(String repoLayoutRef){
        this.repoLayoutRef = repoLayoutRef
        this
    }


    LocalRepository build() {
        return new LocalRepositoryImpl(description, excludesPattern, includesPattern, key, notes, blackedOut, handleReleases, handleSnapshots, maxUniqueSnapshots, propertySets, snapshotVersionBehavior, suppressPomConsistencyChecks, checksumPolicyType, repoLayoutRef)
    }
}
