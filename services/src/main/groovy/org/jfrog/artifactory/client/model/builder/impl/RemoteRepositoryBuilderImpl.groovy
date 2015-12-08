package org.jfrog.artifactory.client.model.builder.impl

import org.jfrog.artifactory.client.model.RemoteRepoChecksumPolicyType
import org.jfrog.artifactory.client.model.RemoteRepository
import org.jfrog.artifactory.client.model.RepositoryType
import org.jfrog.artifactory.client.model.builder.RemoteRepositoryBuilder
import org.jfrog.artifactory.client.model.impl.RemoteRepoChecksumPolicyTypeImpl
import org.jfrog.artifactory.client.model.impl.RemoteRepositoryImpl
import org.jfrog.artifactory.client.model.impl.RepositoryTypeImpl

import static RemoteRepoChecksumPolicyTypeImpl.generate_if_absent
import static org.jfrog.artifactory.client.model.Repository.MAVEN_2_REPO_LAYOUT

/**
 *
 * @author jbaruch
 * @since 31/07/12
 */
class RemoteRepositoryBuilderImpl extends NonVirtualRepositoryBuilderBase<RemoteRepositoryBuilder, RemoteRepository> implements RemoteRepositoryBuilder {

    RemoteRepositoryBuilderImpl() {
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
    private boolean enableCookieManagement = false
    private boolean allowAnyHostAuth = false
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

    RemoteRepositoryBuilder allowAnyHostAuth(boolean allowAnyHostAuth) {
        this.allowAnyHostAuth = allowAnyHostAuth;
        this
    }

    RemoteRepositoryBuilder enableCookieManagement(boolean cookieManagementEnabled){
        this.enableCookieManagement = cookieManagementEnabled;
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
        this.fetchSourcesEagerly = fetchSourcesEagerly
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

    @SuppressWarnings("GroovyAccessibility")
    RemoteRepository build() {
        validate()
        new RemoteRepositoryImpl(description, excludesPattern, includesPattern, key, notes, blackedOut, handleReleases, handleSnapshots, maxUniqueSnapshots, propertySets,
                snapshotVersionBehavior, suppressPomConsistencyChecks, failedRetrievalCachePeriodSecs, fetchJarsEagerly, fetchSourcesEagerly, hardFail, localAddress, missedRetrievalCachePeriodSecs,
                offline, password, proxy, remoteRepoChecksumPolicyType, retrievalCachePeriodSecs, shareConfiguration, socketTimeoutMillis, enableCookieManagement, allowAnyHostAuth,
                storeArtifactsLocally, synchronizeProperties, unusedArtifactsCleanupEnabled, unusedArtifactsCleanupPeriodHours, url, username, repoLayoutRef, enableNuGetSupport, assumedOfflinePeriodSecs, archiveBrowsingEnabled,
                listRemoteFolderItems, rejectInvalidJars, p2Support, packageType, enableGemsSupport, enableNpmSupport, enableVagrantSupport, enableBowerSupport, enableGitLfsSupport, enableDebianSupport, enableDockerSupport, enablePypiSupport, debianTrivialLayout)
    }

    @Override
    RepositoryType getRclass() {
        return RepositoryTypeImpl.REMOTE
    }

    @Override
    Set<String> supportedTypes() {
        return ["maven", "gradle", "ivy", "sbt", "nuget", "gems", "npm", "bower", "debian", "pypi", "docker", "yum"]
    }
}
