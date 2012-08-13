package org.artifactory.client.model.builder.impl

import org.artifactory.client.model.ChecksumPolicyType
import org.artifactory.client.model.impl.ChecksumPolicyTypeImpl
import org.artifactory.client.model.LocalRepository
import org.artifactory.client.model.Repository
import org.artifactory.client.model.builder.LocalRepositoryBuilder
import org.artifactory.client.model.impl.LocalRepositoryImpl

import static ChecksumPolicyTypeImpl.client_checksums
/**
 *
 * @author jbaruch
 * @since 31/07/12
 */
class LocalRepositoryBuilderImpl extends NonVirtualRepositoryBuilderBase<LocalRepositoryBuilder, LocalRepository> implements LocalRepositoryBuilder {

    private LocalRepositoryBuilderImpl() {
        this.repoLayoutRef = Repository.MAVEN_2_REPO_LAYOUT
        this.checksumPolicyType = client_checksums
    }

    private ChecksumPolicyTypeImpl checksumPolicyType

    LocalRepositoryBuilder checksumPolicyType(ChecksumPolicyType checksumPolicyType) {
        this.checksumPolicyType = checksumPolicyType
        this
    }

    LocalRepository build() {
        return new LocalRepositoryImpl(description, excludesPattern, includesPattern, key, notes, blackedOut, handleReleases, handleSnapshots, maxUniqueSnapshots, propertySets, snapshotVersionBehavior, suppressPomConsistencyChecks, checksumPolicyType, repoLayoutRef)
    }

}
