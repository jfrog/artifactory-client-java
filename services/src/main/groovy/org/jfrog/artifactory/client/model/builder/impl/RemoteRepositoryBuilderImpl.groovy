package org.jfrog.artifactory.client.model.builder.impl

import org.jfrog.artifactory.client.model.ContentSync
import org.jfrog.artifactory.client.model.RemoteRepository
import org.jfrog.artifactory.client.model.RepositoryType
import org.jfrog.artifactory.client.model.builder.RemoteRepositoryBuilder
import org.jfrog.artifactory.client.model.impl.RemoteRepositoryImpl
import org.jfrog.artifactory.client.model.impl.RepositoryTypeImpl

import static org.jfrog.artifactory.client.model.impl.PackageTypeImpl.*

/**
 *
 * @author jbaruch
 * @since 31/07/12
 */
class RemoteRepositoryBuilderImpl extends NonVirtualRepositoryBuilderBase<RemoteRepositoryBuilder, RemoteRepository> implements RemoteRepositoryBuilder {

    RemoteRepositoryBuilderImpl() {
        super([bower, cocoapods, debian, docker, gems, generic, gitlfs, gradle, ivy, maven, npm, nuget, opkg, p2,
               pypi, sbt, vcs, yum, rpm, composer, conan, chef, puppet] as Set)
    }

    private String url
    protected String description = ' (local file cache)'
    private String username = ''
    private String password
    private String proxy
    private boolean hardFail
    private boolean offline
    private boolean storeArtifactsLocally = true
    private int socketTimeoutMillis = 15000
    private boolean enableCookieManagement = false
    private boolean allowAnyHostAuth = false
    private String localAddress = ''
    private int retrievalCachePeriodSecs = 43200
    private int missedRetrievalCachePeriodSecs = 7200
    private int failedRetrievalCachePeriodSecs = 30
    private boolean unusedArtifactsCleanupEnabled
    private int unusedArtifactsCleanupPeriodHours
    private boolean shareConfiguration
    private boolean synchronizeProperties
    private long assumedOfflinePeriodSecs = 300
    private boolean listRemoteFolderItems = true
    private ContentSync contentSync
    private String clientTlsCertificate = ''


    RemoteRepositoryBuilder url(String url) {
        this.url = url
        this
    }

    @Override
    String getUrl() {
        url
    }

    @Override
    RemoteRepositoryBuilder description(String description) {
        this.description = !description ? ' (local file cache)' : description
        this
    }

    RemoteRepositoryBuilder username(String username) {
        this.username = username
        this
    }

    @Override
    String getUsername() {
        username
    }

    RemoteRepositoryBuilder password(String password) {
        this.password = password
        this
    }

    @Override
    String getPassword() {
        password
    }

    RemoteRepositoryBuilder proxy(String proxy) {
        this.proxy = proxy
        this
    }

    @Override
    String getProxy() {
        proxy
    }

    RemoteRepositoryBuilder hardFail(boolean hardFail) {
        this.hardFail = hardFail
        this
    }

    @Override
    boolean isHardFail() {
        hardFail
    }

    RemoteRepositoryBuilder offline(boolean offline) {
        this.offline = offline
        this
    }

    @Override
    boolean isOffline() {
        offline
    }

    RemoteRepositoryBuilder storeArtifactsLocally(boolean storeArtifactsLocally) {
        this.storeArtifactsLocally = storeArtifactsLocally
        this
    }

    @Override
    boolean isStoreArtifactsLocally() {
        storeArtifactsLocally
    }

    RemoteRepositoryBuilder socketTimeoutMillis(int socketTimeoutMillis) {
        this.socketTimeoutMillis = socketTimeoutMillis
        this
    }

    @Override
    int getSocketTimeoutMillis() {
        socketTimeoutMillis
    }

    RemoteRepositoryBuilder allowAnyHostAuth(boolean allowAnyHostAuth) {
        this.allowAnyHostAuth = allowAnyHostAuth
        this
    }

    @Override
    boolean isAllowAnyHostAuth() {
        allowAnyHostAuth
    }

    RemoteRepositoryBuilder enableCookieManagement(boolean cookieManagementEnabled){
        this.enableCookieManagement = cookieManagementEnabled
        this
    }

    @Override
    boolean isEnableCookieManagement() {
        enableCookieManagement
    }

    RemoteRepositoryBuilder localAddress(String localAddress) {
        this.localAddress = localAddress
        this
    }

    @Override
    String getLocalAddress() {
        localAddress
    }

    RemoteRepositoryBuilder retrievalCachePeriodSecs(int retrievalCachePeriodSecs) {
        this.retrievalCachePeriodSecs = retrievalCachePeriodSecs
        this
    }

    @Override
    int getRetrievalCachePeriodSecs() {
        retrievalCachePeriodSecs
    }

    RemoteRepositoryBuilder missedRetrievalCachePeriodSecs(int missedRetrievalCachePeriodSecs) {
        this.missedRetrievalCachePeriodSecs = missedRetrievalCachePeriodSecs
        this
    }

    @Override
    int getMissedRetrievalCachePeriodSecs() {
        missedRetrievalCachePeriodSecs
    }

    RemoteRepositoryBuilder failedRetrievalCachePeriodSecs(int failedRetrievalCachePeriodSecs) {
        this.failedRetrievalCachePeriodSecs = failedRetrievalCachePeriodSecs
        this
    }

    @Override
    int getFailedRetrievalCachePeriodSecs() {
        failedRetrievalCachePeriodSecs
    }

    RemoteRepositoryBuilder unusedArtifactsCleanupEnabled(boolean unusedArtifactsCleanupEnabled) {
        this.unusedArtifactsCleanupEnabled = unusedArtifactsCleanupEnabled
        this
    }

    @Override
    boolean isUnusedArtifactsCleanupEnabled() {
        unusedArtifactsCleanupEnabled
    }

    RemoteRepositoryBuilder unusedArtifactsCleanupPeriodHours(int unusedArtifactsCleanupPeriodHours) {
        this.unusedArtifactsCleanupPeriodHours = unusedArtifactsCleanupPeriodHours
        this
    }

    @Override
    int getUnusedArtifactsCleanupPeriodHours() {
        unusedArtifactsCleanupPeriodHours
    }

    RemoteRepositoryBuilder shareConfiguration(boolean shareConfiguration) {
        this.shareConfiguration = shareConfiguration
        this
    }

    @Override
    boolean isShareConfiguration() {
        shareConfiguration
    }

    RemoteRepositoryBuilder synchronizeProperties(boolean synchronizeProperties) {
        this.synchronizeProperties = synchronizeProperties
        this
    }

    @Override
    boolean isSynchronizeProperties() {
        synchronizeProperties
    }

    RemoteRepositoryBuilder assumedOfflinePeriodSecs(long assumedOfflinePeriodSecs) {
        this.assumedOfflinePeriodSecs = assumedOfflinePeriodSecs
        this
    }

    @Override
    long getAssumedOfflinePeriodSecs() {
        assumedOfflinePeriodSecs
    }

    RemoteRepositoryBuilder listRemoteFolderItems(boolean listRemoteFolderItems) {
        this.listRemoteFolderItems = listRemoteFolderItems
        this
    }

    @Override
    boolean isListRemoteFolderItems() {
        listRemoteFolderItems
    }

    RemoteRepositoryBuilder contentSync(ContentSync contentSync) {
        this.contentSync = contentSync
        this
    }

    @Override
    ContentSync getContentSync() {
        contentSync
    }

    @Override
    RemoteRepositoryBuilder clientTlsCertificate(String clientTlsCertificate) {
        this.clientTlsCertificate = clientTlsCertificate
        this
    }

    @Override
    String getClientTlsCertificate() {
        clientTlsCertificate
    }

    @SuppressWarnings("GroovyAccessibility")
    RemoteRepository build() {
        validate()
        setRepoLayoutFromSettings()

        new RemoteRepositoryImpl(key, settings, xraySettings, contentSync, description, excludesPattern,
                includesPattern, notes, blackedOut, propertySets, failedRetrievalCachePeriodSecs,
                hardFail, localAddress, missedRetrievalCachePeriodSecs,
                offline, password, proxy, retrievalCachePeriodSecs,
                shareConfiguration, socketTimeoutMillis, enableCookieManagement, allowAnyHostAuth,
                storeArtifactsLocally, synchronizeProperties, unusedArtifactsCleanupEnabled,
                unusedArtifactsCleanupPeriodHours, url, username, repoLayoutRef,
                assumedOfflinePeriodSecs, archiveBrowsingEnabled, listRemoteFolderItems, clientTlsCertificate,
                customProperties)
    }

    @Override
    RepositoryType getRepositoryType() {
        return RepositoryTypeImpl.REMOTE
    }

}
