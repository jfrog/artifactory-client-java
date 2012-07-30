package org.artifactory.client

import com.fasterxml.jackson.core.type.TypeReference
import groovy.json.JsonSlurper
import org.artifactory.client.model.*

import static org.artifactory.client.model.LocalRepository.ChecksumPolicyType.client_checksums
import static org.artifactory.client.model.NonVirtualRepository.SnapshotVersionBehavior.non_unique
import static org.artifactory.client.model.RemoteRepository.RemoteRepoChecksumPolicyType.generate_if_absent
import static org.artifactory.client.model.VirtualRepository.PomRepositoryReferencesCleanupPolicy.discard_active_reference

/**
 *
 * @author jbaruch
 * @since 29/07/12
 */
class Repositories {

    private Artifactory artifactory
    private String repoKey

    Repositories(Artifactory artifactory) {
        this.artifactory = artifactory
    }

    RemoteRepositoryBuilder remoteRepositoryBuilder() {
        return new RemoteRepositoryBuilder()
    }

    RemoteRepositoryBuilder builderFrom(RemoteRepository from) {
        return new RemoteRepositoryBuilder().blackedOut(from.blackedOut).description(from.description).excludesPattern(from.excludesPattern).handleReleases(from.handleReleases)
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
        return new LocalRepositoryBuilder().blackedOut(from.blackedOut).checksumPolicyType(from.checksumPolicyType).description(from.description)
                .excludesPattern(from.excludesPattern).handleReleases(from.handleReleases).handleSnapshots(from.handleSnapshots).includesPattern(from.includesPattern)
                .key(from.key).maxUniqueSnapshots(from.maxUniqueSnapshots).notes(from.notes).propertySets(from.propertySets).snapshotVersionBehavior(from.snapshotVersionBehavior)
                .suppressPomConsistencyChecks(from.suppressPomConsistencyChecks)
    }

    VirtualRepositoryBuilder virtualRepositoryBuilder() {
        new VirtualRepositoryBuilder()
    }

    VirtualRepositoryBuilder builderFrom(VirtualRepository from) {
        return new VirtualRepositoryBuilder().description(from.description).excludesPattern(from.excludesPattern).includesPattern(from.includesPattern).key(from.key)
                .notes(from.notes).pomRepositoryReferencesCleanupPolicy(from.pomRepositoryReferencesCleanupPolicy)
                .artifactoryRequestsCanRetrieveRemoteArtifacts(from.artifactoryRequestsCanRetrieveRemoteArtifacts).keyPair(from.keyPair).repositories(from.repositories)
    }

    Repositories repoKey(String repoKey) {
        this.repoKey = repoKey
        this
    }

    String create(int position, RepositoryBase configuration) {
        artifactory.put("/api/repositories/${repoKey}", String, [pos: position])
    }

    String update(RepositoryConfiguration) {
        artifactory.post("/api/repositories/${repoKey}", String)
    }

    String delete() {
        artifactory.delete("/api/repositories/${repoKey}", String, [pos: position])
    }

    List<LightweightRepository> list(RepositoryType repositoryType) {
        artifactory.getJson('/api/repositories/', new TypeReference<List<LightweightRepositoryImpl>>() {}, [type: repositoryType.toString()])
    }

    Repository get() {
        String repoJson = artifactory.getText("/api/repositories/${repoKey}")
        JsonSlurper slurper = new JsonSlurper()
        def repo = slurper.parseText(repoJson)
        artifactory.parseText(repoJson, RepositoryType.parseString(repo.rclass).typeClass)
    }

    static class LocalRepositoryBuilder {

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

        LocalRepository build() {
            return new LocalRepositoryImpl(description, excludesPattern, includesPattern, key, notes, blackedOut, handleReleases, handleSnapshots, maxUniqueSnapshots, propertySets, snapshotVersionBehavior, suppressPomConsistencyChecks, checksumPolicyType)
        }
    }

    static class VirtualRepositoryBuilder {

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

    static class RemoteRepositoryBuilder {

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

        RemoteRepositoryBuilder repoLayoutRef(String repoLayoutRef){
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


}
