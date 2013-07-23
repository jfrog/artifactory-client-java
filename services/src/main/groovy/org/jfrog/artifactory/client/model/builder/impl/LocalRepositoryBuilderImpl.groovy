package org.jfrog.artifactory.client.model.builder.impl

import org.jfrog.artifactory.client.model.ChecksumPolicyType
import org.jfrog.artifactory.client.model.impl.ChecksumPolicyTypeImpl
import org.jfrog.artifactory.client.model.LocalRepository
import org.jfrog.artifactory.client.model.Repository
import org.jfrog.artifactory.client.model.builder.LocalRepositoryBuilder
import org.jfrog.artifactory.client.model.impl.LocalRepositoryImpl

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

    private ChecksumPolicyType checksumPolicyType
    private boolean calculateYumMetadata
    private int yumRootDepth

    LocalRepositoryBuilder checksumPolicyType(ChecksumPolicyType checksumPolicyType) {
        this.checksumPolicyType = checksumPolicyType
        this
    }

    LocalRepositoryBuilder calculateYumMetadata(boolean calculateYumMetadata) {
        this.calculateYumMetadata = calculateYumMetadata
        this
    }

    LocalRepositoryBuilder yumRootDepth(int yumRootDepth) {
        this.yumRootDepth = yumRootDepth
        this
    }

    LocalRepository build() {
        return new LocalRepositoryImpl(description, excludesPattern, includesPattern, key, notes, blackedOut, handleReleases, handleSnapshots, maxUniqueSnapshots, propertySets, snapshotVersionBehavior, suppressPomConsistencyChecks, checksumPolicyType, repoLayoutRef, enableNuGetSupport, archiveBrowsingEnabled, calculateYumMetadata, yumRootDepth, enableGemsSupport)
    }

}
