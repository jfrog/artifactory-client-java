package org.artifactory.client.model.builder

import org.artifactory.client.Repositories
import org.artifactory.client.model.LocalRepository
import org.artifactory.client.model.RemoteRepository
import org.artifactory.client.model.VirtualRepository

/**
 *
 * @author jbaruch
 * @since 31/07/12
 */
class RepositoryBuilders {

    RemoteRepositoryBuilder remoteRepositoryBuilder() {
        new RemoteRepositoryBuilder()
    }

    RemoteRepositoryBuilder builderFrom(RemoteRepository from) {
        new RemoteRepositoryBuilder().blackedOut(from.blackedOut).description(from.description).excludesPattern(from.excludesPattern).handleReleases(from.handleReleases)
                .handleSnapshots(from.handleSnapshots).includesPattern(from.includesPattern).key(from.key).maxUniqueSnapshots(from.maxUniqueSnapshots).notes(from.notes)
                .propertySets(from.propertySets).snapshotVersionBehavior(from.snapshotVersionBehavior).suppressPomConsistencyChecks(from.suppressPomConsistencyChecks).url(from.url)
                .username(from.username).password(from.password).proxy(from.proxy).remoteRepoChecksumPolicyType(from.remoteRepoChecksumPolicyType).hardFail(from.hardFail)
                .offline(from.offline).storeArtifactsLocally(from.storeArtifactsLocally).socketTimeoutMillis(from.socketTimeoutMillis).localAddress(from.localAddress)
                .retrievalCachePeriodSecs(from.retrievalCachePeriodSecs).missedRetrievalCachePeriodSecs(from.missedRetrievalCachePeriodSecs)
                .failedRetrievalCachePeriodSecs(from.failedRetrievalCachePeriodSecs).unusedArtifactsCleanupEnabled(from.unusedArtifactsCleanupEnabled)
                .unusedArtifactsCleanupPeriodHours(from.unusedArtifactsCleanupPeriodHours).fetchJarsEagerly(from.fetchJarsEagerly).fetchSourcesEagerly(from.fetchSourcesEagerly)
                .shareConfiguration(from.shareConfiguration).synchronizeProperties(from.synchronizeProperties).repoLayoutRef(from.repoLayoutRef)
    }

    LocalRepositoryBuilder localRepositoryBuilder() {
        new LocalRepositoryBuilder()
    }

    LocalRepositoryBuilder builderFrom(LocalRepository from) {
        new LocalRepositoryBuilder().blackedOut(from.blackedOut).checksumPolicyType(from.checksumPolicyType).description(from.description)
                .excludesPattern(from.excludesPattern).handleReleases(from.handleReleases).handleSnapshots(from.handleSnapshots).includesPattern(from.includesPattern)
                .key(from.key).maxUniqueSnapshots(from.maxUniqueSnapshots).notes(from.notes).propertySets(from.propertySets).snapshotVersionBehavior(from.snapshotVersionBehavior)
                .suppressPomConsistencyChecks(from.suppressPomConsistencyChecks).repoLayoutRef(from.repoLayoutRef)
    }

    VirtualRepositoryBuilder virtualRepositoryBuilder() {
        new VirtualRepositoryBuilder()
    }

    VirtualRepositoryBuilder builderFrom(VirtualRepository from) {
        new VirtualRepositoryBuilder().description(from.description).excludesPattern(from.excludesPattern).includesPattern(from.includesPattern).key(from.key)
                .notes(from.notes).pomRepositoryReferencesCleanupPolicy(from.pomRepositoryReferencesCleanupPolicy)
                .artifactoryRequestsCanRetrieveRemoteArtifacts(from.artifactoryRequestsCanRetrieveRemoteArtifacts).keyPair(from.keyPair).repositories(from.repositories)
    }
}
