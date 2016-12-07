package org.jfrog.artifactory.client.model.builder.impl

import org.jfrog.artifactory.client.model.RemoteRepository
import org.jfrog.artifactory.client.model.RepositoryType
import org.jfrog.artifactory.client.model.builder.RemoteRepositoryBuilder
import org.jfrog.artifactory.client.model.impl.RemoteRepositoryImpl
import org.jfrog.artifactory.client.model.impl.RepositoryTypeImpl

import static org.jfrog.artifactory.client.model.PackageType.*
import static org.jfrog.artifactory.client.model.Repository.MAVEN_2_REPO_LAYOUT
import static org.jfrog.artifactory.client.model.impl.RemoteRepoChecksumPolicyTypeImpl.generate_if_absent

/**
 *
 * @author jbaruch
 * @since 31/07/12
 */
class RemoteRepositoryBuilderImpl extends NonVirtualRepositoryBuilderBase<RemoteRepositoryBuilder, RemoteRepository> implements RemoteRepositoryBuilder {

    RemoteRepositoryBuilderImpl() {
        super([bower, cocoapods, debian, docker, gems, generic, gitlfs, gradle, ivy, maven, npm, nuget, opkg, p2,
               pypi, sbt, vcs, yum, composer, conan])
        repoLayoutRef = MAVEN_2_REPO_LAYOUT
    }

    private String url
    private String username
    private String password
    private String proxy
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
    private boolean shareConfiguration
    private boolean synchronizeProperties
    private long assumedOfflinePeriodSecs = 300

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

    @SuppressWarnings("GroovyAccessibility")
    RemoteRepository build() {
        validate()

        new RemoteRepositoryImpl(key, settings, xraySettings, description, excludesPattern,
                includesPattern, notes, blackedOut,
                propertySets,
                failedRetrievalCachePeriodSecs,
                hardFail, localAddress, missedRetrievalCachePeriodSecs,
                offline, password, proxy, retrievalCachePeriodSecs,
                shareConfiguration, socketTimeoutMillis, enableCookieManagement, allowAnyHostAuth,
                storeArtifactsLocally, synchronizeProperties, unusedArtifactsCleanupEnabled,
                unusedArtifactsCleanupPeriodHours, url, username, repoLayoutRef,
                assumedOfflinePeriodSecs, archiveBrowsingEnabled)
    }

    @Override
    RepositoryType getRepositoryType() {
        return RepositoryTypeImpl.REMOTE
    }

}
