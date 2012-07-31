package org.artifactory.client.model.builder

import org.artifactory.client.model.NonVirtualRepository

import static org.artifactory.client.model.NonVirtualRepository.SnapshotVersionBehavior.non_unique
import org.artifactory.client.model.RemoteRepository

import static org.artifactory.client.model.RemoteRepository.RemoteRepoChecksumPolicyType.generate_if_absent
import org.artifactory.client.model.impl.RemoteRepositoryImpl

/**
 * 
 * @author jbaruch
 * @since 31/07/12
 */
class RemoteRepositoryBuilder {

    private RemoteRepositoryBuilder() {
    }

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
    private String url
    private String username
    private String password
    private String proxy
    private RemoteRepository.RemoteRepoChecksumPolicyType remoteRepoChecksumPolicyType = generate_if_absent
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
    private String repoLayoutRef

    RemoteRepositoryBuilder blackedOut(boolean blackedOut) {
        this.blackedOut = blackedOut
        this
    }

    RemoteRepositoryBuilder description(String description) {
        this.description = description
        this
    }

    RemoteRepositoryBuilder excludesPattern(String excludesPattern) {
        this.excludesPattern = excludesPattern
        this
    }

    RemoteRepositoryBuilder handleReleases(boolean handleReleases) {
        this.handleReleases = handleReleases
        this
    }

    RemoteRepositoryBuilder handleSnapshots(boolean handleSnapshots) {
        this.handleSnapshots = handleSnapshots
        this
    }

    RemoteRepositoryBuilder includesPattern(String includesPattern) {
        this.includesPattern = includesPattern
        this
    }

    RemoteRepositoryBuilder key(String key) {
        this.key = key
        this
    }

    RemoteRepositoryBuilder maxUniqueSnapshots(int maxUniqueSnapshots) {
        this.maxUniqueSnapshots = maxUniqueSnapshots
        this
    }

    RemoteRepositoryBuilder notes(String notes) {
        this.notes = notes
        this
    }

    RemoteRepositoryBuilder propertySets(List<String> propertySets) {
        this.propertySets = propertySets
        this
    }

    RemoteRepositoryBuilder snapshotVersionBehavior(NonVirtualRepository.SnapshotVersionBehavior snapshotVersionBehavior) {
        this.snapshotVersionBehavior = snapshotVersionBehavior
        this
    }

    RemoteRepositoryBuilder suppressPomConsistencyChecks(boolean suppressPomConsistencyChecks) {
        this.suppressPomConsistencyChecks = suppressPomConsistencyChecks
        this
    }

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

    RemoteRepositoryBuilder remoteRepoChecksumPolicyType(RemoteRepository.RemoteRepoChecksumPolicyType remoteRepoChecksumPolicyType) {
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

    RemoteRepositoryBuilder repoLayoutRef(String repoLayoutRef) {
        this.repoLayoutRef = repoLayoutRef
        this
    }

    RemoteRepository build() {
        return new RemoteRepositoryImpl(description, excludesPattern, includesPattern, key, notes, blackedOut, handleReleases, handleSnapshots,
                maxUniqueSnapshots, propertySets, snapshotVersionBehavior, suppressPomConsistencyChecks, failedRetrievalCachePeriodSecs, fetchJarsEagerly, fetchSourcesEagerly,
                hardFail, localAddress, missedRetrievalCachePeriodSecs, offline, password, proxy, remoteRepoChecksumPolicyType, retrievalCachePeriodSecs, shareConfiguration, socketTimeoutMillis,
                storeArtifactsLocally, synchronizeProperties, unusedArtifactsCleanupEnabled, unusedArtifactsCleanupPeriodHours, url, username, repoLayoutRef)
    }
}
