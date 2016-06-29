package org.jfrog.artifactory.client.model.builder.impl

import org.jfrog.artifactory.client.model.LocalRepository
import org.jfrog.artifactory.client.model.RemoteRepository
import org.jfrog.artifactory.client.model.VirtualRepository
import org.jfrog.artifactory.client.model.builder.LocalRepositoryBuilder
import org.jfrog.artifactory.client.model.builder.RemoteRepositoryBuilder
import org.jfrog.artifactory.client.model.builder.RepositoryBuilders
import org.jfrog.artifactory.client.model.builder.VirtualRepositoryBuilder

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
        new RemoteRepositoryBuilderImpl().repositorySettings(from.repositorySettings).blackedOut(from.blackedOut).description(from.description).excludesPattern(from.excludesPattern)
                .includesPattern(from.includesPattern).key(from.key).notes(from.notes)
                .propertySets(from.propertySets).url(from.url)
                .username(from.username).password(from.password).proxy(from.proxy).hardFail(from.hardFail)
                .offline(from.offline).storeArtifactsLocally(from.storeArtifactsLocally).socketTimeoutMillis(from.socketTimeoutMillis).allowAnyHostAuth(from.allowAnyHostAuth)
                .enableCookieManagement(from.enableCookieManagement).localAddress(from.localAddress)
                .retrievalCachePeriodSecs(from.retrievalCachePeriodSecs).missedRetrievalCachePeriodSecs(from.missedRetrievalCachePeriodSecs)
                .failedRetrievalCachePeriodSecs(from.failedRetrievalCachePeriodSecs).unusedArtifactsCleanupEnabled(from.unusedArtifactsCleanupEnabled)
                .unusedArtifactsCleanupPeriodHours(from.unusedArtifactsCleanupPeriodHours)
                .shareConfiguration(from.shareConfiguration).synchronizeProperties(from.synchronizeProperties).repoLayoutRef(from.repoLayoutRef)
                .assumedOfflinePeriodSecs(from.assumedOfflinePeriodSecs).archiveBrowsingEnabled(from.archiveBrowsingEnabled)
    }

    LocalRepositoryBuilder localRepositoryBuilder() {
        new LocalRepositoryBuilderImpl()
    }

    LocalRepositoryBuilder builderFrom(LocalRepository from) {
        new LocalRepositoryBuilderImpl().repositorySettings(from.repositorySettings).blackedOut(from.blackedOut).description(from.description)
                .excludesPattern(from.excludesPattern).includesPattern(from.includesPattern)
                .key(from.key).notes(from.notes).propertySets(from.propertySets)
                .repoLayoutRef(from.repoLayoutRef).archiveBrowsingEnabled(from.archiveBrowsingEnabled)
    }

    VirtualRepositoryBuilder virtualRepositoryBuilder() {
        new VirtualRepositoryBuilderImpl()
    }

    VirtualRepositoryBuilder builderFrom(VirtualRepository from) {
        new VirtualRepositoryBuilderImpl().repositorySettings(from.repositorySettings).description(from.description).excludesPattern(from.excludesPattern).includesPattern(from.includesPattern).key(from.key)
                .notes(from.notes)
                .artifactoryRequestsCanRetrieveRemoteArtifacts(from.artifactoryRequestsCanRetrieveRemoteArtifacts).repositories(from.repositories)
                .defaultDeploymentRepo(from.defaultDeploymentRepo)
    }
}
