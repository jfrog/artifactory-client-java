package org.artifactory.client.model.builder.impl

import org.artifactory.client.model.RemoteRepoChecksumPolicyType
import org.artifactory.client.model.impl.RemoteRepoChecksumPolicyTypeImpl
import org.artifactory.client.model.RemoteRepository
import org.artifactory.client.model.builder.RemoteRepositoryBuilder
import org.artifactory.client.model.impl.RemoteRepositoryImpl

import static RemoteRepoChecksumPolicyTypeImpl.generate_if_absent
import static org.artifactory.client.model.Repository.MAVEN_2_REPO_LAYOUT
/**
 *
 * @author jbaruch
 * @since 31/07/12
 */
class RemoteRepositoryBuilderImpl extends NonVirtualRepositoryBuilderBase<RemoteRepositoryBuilder, RemoteRepository> implements RemoteRepositoryBuilder {

    private RemoteRepositoryBuilderImpl() {
        remoteRepoChecksumPolicyType = generate_if_absent
        repoLayoutRef = MAVEN_2_REPO_LAYOUT
    }

    private String url
    private String username
    private String password
    private String proxy
    private RemoteRepoChecksumPolicyType remoteRepoChecksumPolicyType
    private boolean hardFail
    private boolean offline
    private boolean storeArtifactsLocally = true
    private int socketTimeoutMillis = 15000
    private String localAddress
    private int retrievalCachePeriodSecs = 43200
    private int missedRetrievalCachePeriodSecs = 7200
    private int failedRetrievalCachePeriodSecs = 30
    private boolean unusedArtifactsCleanupEnabled
    private int unusedArtifactsCleanupPeriodHours
    private boolean fetchJarsEagerly
    private boolean fetchSourcesEagerly
    private boolean shareConfiguration
    private boolean synchronizeProperties
    private long assumedOfflinePeriodSecs = 300
    private boolean listRemoteFolderItems = true
    private boolean rejectInvalidJars = false
    private boolean p2Support = false

    RemoteRepositoryBuilder url(String url) {
        this.url = url
        this
    }

    RemoteRepositoryBuilder username(String username) {
        this.username = username
        this
    }

    RemoteRepositoryBuilder password(String password) {
        this.password = password
        this
    }

    RemoteRepositoryBuilder proxy(String proxy) {
        this.proxy = proxy
        this
    }

    RemoteRepositoryBuilder remoteRepoChecksumPolicyType(RemoteRepoChecksumPolicyType remoteRepoChecksumPolicyType) {
        this.remoteRepoChecksumPolicyType = remoteRepoChecksumPolicyType
        this
    }

    RemoteRepositoryBuilder hardFail(boolean hardFail) {
        this.hardFail = hardFail
        this
    }

    RemoteRepositoryBuilder offline(boolean offline) {
        this.offline = offline
        this
    }

    RemoteRepositoryBuilder storeArtifactsLocally(boolean storeArtifactsLocally) {
        this.storeArtifactsLocally = storeArtifactsLocally
        this
    }

    RemoteRepositoryBuilder socketTimeoutMillis(int socketTimeoutMillis) {
        this.socketTimeoutMillis = socketTimeoutMillis
        this
    }

    RemoteRepositoryBuilder localAddress(String localAddress) {
        this.localAddress = localAddress
        this
    }

    RemoteRepositoryBuilder retrievalCachePeriodSecs(int retrievalCachePeriodSecs) {
        this.retrievalCachePeriodSecs = retrievalCachePeriodSecs
        this
    }

    RemoteRepositoryBuilder missedRetrievalCachePeriodSecs(int missedRetrievalCachePeriodSecs) {
        this.missedRetrievalCachePeriodSecs = missedRetrievalCachePeriodSecs
        this
    }

    RemoteRepositoryBuilder failedRetrievalCachePeriodSecs(int failedRetrievalCachePeriodSecs) {
        this.failedRetrievalCachePeriodSecs = failedRetrievalCachePeriodSecs
        this
    }

    RemoteRepositoryBuilder unusedArtifactsCleanupEnabled(boolean unusedArtifactsCleanupEnabled) {
        this.unusedArtifactsCleanupEnabled = unusedArtifactsCleanupEnabled
        this
    }

    RemoteRepositoryBuilder unusedArtifactsCleanupPeriodHours(int unusedArtifactsCleanupPeriodHours) {
        this.unusedArtifactsCleanupPeriodHours = unusedArtifactsCleanupPeriodHours
        this
    }

    RemoteRepositoryBuilder fetchJarsEagerly(boolean fetchJarsEagerly) {
        this.fetchJarsEagerly = fetchJarsEagerly
        this
    }

    RemoteRepositoryBuilder fetchSourcesEagerly(boolean fetchSourcesEagerly) {
        this.fetchJarsEagerly = fetchSourcesEagerly
        this
    }

    RemoteRepositoryBuilder shareConfiguration(boolean shareConfiguration) {
        this.shareConfiguration = shareConfiguration
        this
    }

    RemoteRepositoryBuilder synchronizeProperties(boolean synchronizeProperties) {
        this.synchronizeProperties = synchronizeProperties
        this
    }

    RemoteRepositoryBuilder assumedOfflinePeriodSecs(long assumedOfflinePeriodSecs) {
        this.assumedOfflinePeriodSecs = assumedOfflinePeriodSecs
        this
    }

    RemoteRepositoryBuilder listRemoteFolderItems(boolean listRemoteFolderItems) {
        this.listRemoteFolderItems = listRemoteFolderItems
        this
    }

    RemoteRepositoryBuilder rejectInvalidJars(boolean rejectInvalidJars) {
        this.rejectInvalidJars = rejectInvalidJars
        this
    }

    RemoteRepositoryBuilder p2Support(boolean p2Support) {
        this.p2Support = p2Support
        this
    }

    RemoteRepository build() {
        return new RemoteRepositoryImpl(description, excludesPattern, includesPattern, key, notes, blackedOut,handleReleases, handleSnapshots, maxUniqueSnapshots,propertySets,
        snapshotVersionBehavior, suppressPomConsistencyChecks, failedRetrievalCachePeriodSecs, fetchJarsEagerly, fetchSourcesEagerly,hardFail, localAddress, missedRetrievalCachePeriodSecs,
        offline, password, proxy, remoteRepoChecksumPolicyType, retrievalCachePeriodSecs, shareConfiguration, socketTimeoutMillis, storeArtifactsLocally, synchronizeProperties,
        unusedArtifactsCleanupEnabled, unusedArtifactsCleanupPeriodHours, url, username, repoLayoutRef, enableNuGetSupport, assumedOfflinePeriodSecs, archiveBrowsingEnabled, listRemoteFolderItems, rejectInvalidJars, p2Support)
    }
}
