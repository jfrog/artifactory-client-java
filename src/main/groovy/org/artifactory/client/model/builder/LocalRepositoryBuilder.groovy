package org.artifactory.client.model.builder

import org.artifactory.client.model.LocalRepository
import org.artifactory.client.model.impl.LocalRepositoryImpl

import static org.artifactory.client.model.LocalRepository.ChecksumPolicyType.client_checksums

/**
 *
 * @author jbaruch
 * @since 31/07/12
 */
class LocalRepositoryBuilder extends NonVirtualRepositoryBuilderBase<LocalRepositoryBuilder> {

    private LocalRepositoryBuilder() { }

    private LocalRepository.ChecksumPolicyType checksumPolicyType = client_checksums

    LocalRepositoryBuilder checksumPolicyType(LocalRepository.ChecksumPolicyType checksumPolicyType) {
        this.checksumPolicyType = checksumPolicyType
        this
    }

    LocalRepository build() {
        return new LocalRepositoryImpl(description, excludesPattern, includesPattern, key, notes, blackedOut, handleReleases, handleSnapshots, maxUniqueSnapshots, propertySets, snapshotVersionBehavior, suppressPomConsistencyChecks, checksumPolicyType, repoLayoutRef)
    }
}
