package org.artifactory.client.model.builder.impl

import org.artifactory.client.model.LocalRepository
import org.artifactory.client.model.RemoteRepository
import org.artifactory.client.model.VirtualRepository
import org.artifactory.client.model.builder.LocalRepositoryBuilder
import org.artifactory.client.model.builder.RemoteRepositoryBuilder
import org.artifactory.client.model.builder.RepositoryBuilders
import org.artifactory.client.model.builder.VirtualRepositoryBuilder

/**
 *
 * @author jbaruch
 * @since 31/07/12
 */
class RepositoryBuildersImpl implements RepositoryBuilders {

    private RepositoryBuildersImpl() {}

    static RepositoryBuilders create() {
        return new RepositoryBuildersImpl()
    }

    RemoteRepositoryBuilder remoteRepositoryBuilder() {
        new RemoteRepositoryBuilderImpl()
    }

    RemoteRepositoryBuilder builderFrom(RemoteRepository from) {
        new RemoteRepositoryBuilderImpl().blackedOut(from.blackedOut).description(from.description).excludesPattern(from.excludesPattern).handleReleases(from.handleReleases)
                .handleSnapshots(from.handleSnapshots).includesPattern(from.includesPattern).key(from.key).maxUniqueSnapshots(from.maxUniqueSnapshots).notes(from.notes)
                .propertySets(from.propertySets).snapshotVersionBehavior(from.snapshotVersionBehavior).suppressPomConsistencyChecks(from.suppressPomConsistencyChecks).url(from.url)
                .username(from.username).password(from.password).proxy(from.proxy).remoteRepoChecksumPolicyType(from.remoteRepoChecksumPolicyType).hardFail(from.hardFail)
                .offline(from.offline).storeArtifactsLocally(from.storeArtifactsLocally).socketTimeoutMillis(from.socketTimeoutMillis).localAddress(from.localAddress)
                .retrievalCachePeriodSecs(from.retrievalCachePeriodSecs).missedRetrievalCachePeriodSecs(from.missedRetrievalCachePeriodSecs)
                .failedRetrievalCachePeriodSecs(from.failedRetrievalCachePeriodSecs).unusedArtifactsCleanupEnabled(from.unusedArtifactsCleanupEnabled)
                .unusedArtifactsCleanupPeriodHours(from.unusedArtifactsCleanupPeriodHours).fetchJarsEagerly(from.fetchJarsEagerly).fetchSourcesEagerly(from.fetchSourcesEagerly)
                .shareConfiguration(from.shareConfiguration).synchronizeProperties(from.synchronizeProperties).repoLayoutRef(from.repoLayoutRef).enableNuGetSupport(from.enableNuGetSupport)
                .assumedOfflinePeriodSecs(from.assumedOfflinePeriodSecs).archiveBrowsingEnabled(from.archiveBrowsingEnabled).listRemoteFolderItems(from.listRemoteFolderItems)
                .rejectInvalidJars(from.rejectInvalidJars).p2Support(from.p2Support)
    }

    LocalRepositoryBuilder localRepositoryBuilder() {
        new LocalRepositoryBuilderImpl()
    }

    LocalRepositoryBuilder builderFrom(LocalRepository from) {
        new LocalRepositoryBuilderImpl().blackedOut(from.blackedOut).checksumPolicyType(from.checksumPolicyType).description(from.description)
                .excludesPattern(from.excludesPattern).handleReleases(from.handleReleases).handleSnapshots(from.handleSnapshots).includesPattern(from.includesPattern)
                .key(from.key).maxUniqueSnapshots(from.maxUniqueSnapshots).notes(from.notes).propertySets(from.propertySets).snapshotVersionBehavior(from.snapshotVersionBehavior)
                .suppressPomConsistencyChecks(from.suppressPomConsistencyChecks).repoLayoutRef(from.repoLayoutRef).archiveBrowsingEnabled(from.archiveBrowsingEnabled)
                .enableNuGetSupport(from.enableNuGetSupport).calculateYumMetadata(from.calculateYumMetadata).yumRootDepth(from.yumRootDepth)
    }

    VirtualRepositoryBuilder virtualRepositoryBuilder() {
        new VirtualRepositoryBuilderImpl()
    }

    VirtualRepositoryBuilder builderFrom(VirtualRepository from) {
        new VirtualRepositoryBuilderImpl().description(from.description).excludesPattern(from.excludesPattern).includesPattern(from.includesPattern).key(from.key)
                .notes(from.notes).pomRepositoryReferencesCleanupPolicy(from.pomRepositoryReferencesCleanupPolicy)
                .artifactoryRequestsCanRetrieveRemoteArtifacts(from.artifactoryRequestsCanRetrieveRemoteArtifacts).keyPair(from.keyPair).repositories(from.repositories)
    }
}
