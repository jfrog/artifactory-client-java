package org.jfrog.artifactory.client.model.impl;

import org.jfrog.artifactory.client.model.ContentSync;
import org.jfrog.artifactory.client.model.PackageType;
import org.jfrog.artifactory.client.model.RemoteRepository;
import org.jfrog.artifactory.client.model.RepositoryType;
import org.jfrog.artifactory.client.model.builder.RemoteRepositoryBuilder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.jfrog.artifactory.client.model.impl.PackageTypeImpl.*;

/**
 * @author jbaruch
 * @since 31/07/12
 */
public class RemoteRepositoryBuilderImpl extends NonVirtualRepositoryBuilderBase<RemoteRepositoryBuilder, RemoteRepository> implements RemoteRepositoryBuilder {
    private static Set<PackageType> remoteRepositorySupportedTypes = new HashSet<PackageType>(Arrays.asList(
            bower, cocoapods, cran, debian, docker, gems, generic, gitlfs, gradle, ivy, maven, npm, nuget, opkg, p2, pypi, sbt, vcs, yum, rpm, composer, conan, chef, puppet
    ));

    private String url;
    private String username = "";
    private String password;
    private String proxy;
    private boolean hardFail;
    private boolean offline;
    private boolean storeArtifactsLocally = true;
    private int socketTimeoutMillis = 15000;
    private boolean enableCookieManagement = false;
    private boolean allowAnyHostAuth = false;
    private String localAddress = "";
    private int retrievalCachePeriodSecs = 43200;
    private int missedRetrievalCachePeriodSecs = 7200;
    private int failedRetrievalCachePeriodSecs = 30;
    private boolean unusedArtifactsCleanupEnabled;
    private int unusedArtifactsCleanupPeriodHours;
    private boolean shareConfiguration;
    private boolean synchronizeProperties;
    private long assumedOfflinePeriodSecs = 300;
    private boolean listRemoteFolderItems = true;
    private ContentSync contentSync;
    private String clientTlsCertificate = "";

    protected RemoteRepositoryBuilderImpl() {
        super(remoteRepositorySupportedTypes);
    }

    public RemoteRepositoryBuilder url(String url) {
        this.url = url;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public RemoteRepositoryBuilder username(String username) {
        this.username = username;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public RemoteRepositoryBuilder password(String password) {
        this.password = password;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RemoteRepositoryBuilder proxy(String proxy) {
        this.proxy = proxy;
        return this;
    }

    public String getProxy() {
        return proxy;
    }

    public RemoteRepositoryBuilder hardFail(boolean hardFail) {
        this.hardFail = hardFail;
        return this;
    }

    public boolean isHardFail() {
        return hardFail;
    }

    public RemoteRepositoryBuilder offline(boolean offline) {
        this.offline = offline;
        return this;
    }

    public boolean isOffline() {
        return offline;
    }

    public RemoteRepositoryBuilder storeArtifactsLocally(boolean storeArtifactsLocally) {
        this.storeArtifactsLocally = storeArtifactsLocally;
        return this;
    }

    public boolean isStoreArtifactsLocally() {
        return storeArtifactsLocally;
    }

    public RemoteRepositoryBuilder socketTimeoutMillis(int socketTimeoutMillis) {
        this.socketTimeoutMillis = socketTimeoutMillis;
        return this;
    }

    public int getSocketTimeoutMillis() {
        return socketTimeoutMillis;
    }

    public RemoteRepositoryBuilder allowAnyHostAuth(boolean allowAnyHostAuth) {
        this.allowAnyHostAuth = allowAnyHostAuth;
        return this;
    }

    public boolean isAllowAnyHostAuth() {
        return allowAnyHostAuth;
    }

    public RemoteRepositoryBuilder enableCookieManagement(boolean cookieManagementEnabled) {
        this.enableCookieManagement = cookieManagementEnabled;
        return this;
    }

    public boolean isEnableCookieManagement() {
        return enableCookieManagement;
    }

    public RemoteRepositoryBuilder localAddress(String localAddress) {
        this.localAddress = localAddress;
        return this;
    }

    public String getLocalAddress() {
        return localAddress;
    }

    public RemoteRepositoryBuilder retrievalCachePeriodSecs(int retrievalCachePeriodSecs) {
        this.retrievalCachePeriodSecs = retrievalCachePeriodSecs;
        return this;
    }

    public int getRetrievalCachePeriodSecs() {
        return retrievalCachePeriodSecs;
    }

    public RemoteRepositoryBuilder missedRetrievalCachePeriodSecs(int missedRetrievalCachePeriodSecs) {
        this.missedRetrievalCachePeriodSecs = missedRetrievalCachePeriodSecs;
        return this;
    }

    public int getMissedRetrievalCachePeriodSecs() {
        return missedRetrievalCachePeriodSecs;
    }

    public RemoteRepositoryBuilder failedRetrievalCachePeriodSecs(int failedRetrievalCachePeriodSecs) {
        this.failedRetrievalCachePeriodSecs = failedRetrievalCachePeriodSecs;
        return this;
    }

    public int getFailedRetrievalCachePeriodSecs() {
        return failedRetrievalCachePeriodSecs;
    }

    public RemoteRepositoryBuilder unusedArtifactsCleanupEnabled(boolean unusedArtifactsCleanupEnabled) {
        this.unusedArtifactsCleanupEnabled = unusedArtifactsCleanupEnabled;
        return this;
    }

    public boolean isUnusedArtifactsCleanupEnabled() {
        return unusedArtifactsCleanupEnabled;
    }

    public RemoteRepositoryBuilder unusedArtifactsCleanupPeriodHours(int unusedArtifactsCleanupPeriodHours) {
        this.unusedArtifactsCleanupPeriodHours = unusedArtifactsCleanupPeriodHours;
        return this;
    }

    public int getUnusedArtifactsCleanupPeriodHours() {
        return unusedArtifactsCleanupPeriodHours;
    }

    public RemoteRepositoryBuilder shareConfiguration(boolean shareConfiguration) {
        this.shareConfiguration = shareConfiguration;
        return this;
    }

    public boolean isShareConfiguration() {
        return shareConfiguration;
    }

    public RemoteRepositoryBuilder synchronizeProperties(boolean synchronizeProperties) {
        this.synchronizeProperties = synchronizeProperties;
        return this;
    }

    public boolean isSynchronizeProperties() {
        return synchronizeProperties;
    }

    public RemoteRepositoryBuilder assumedOfflinePeriodSecs(long assumedOfflinePeriodSecs) {
        this.assumedOfflinePeriodSecs = assumedOfflinePeriodSecs;
        return this;
    }

    public long getAssumedOfflinePeriodSecs() {
        return assumedOfflinePeriodSecs;
    }

    public RemoteRepositoryBuilder listRemoteFolderItems(boolean listRemoteFolderItems) {
        this.listRemoteFolderItems = listRemoteFolderItems;
        return this;
    }

    public boolean isListRemoteFolderItems() {
        return listRemoteFolderItems;
    }

    public RemoteRepositoryBuilder contentSync(ContentSync contentSync) {
        this.contentSync = contentSync;
        return this;
    }

    public ContentSync getContentSync() {
        return contentSync;
    }

    public RemoteRepositoryBuilder clientTlsCertificate(String clientTlsCertificate) {
        this.clientTlsCertificate = clientTlsCertificate;
        return this;
    }

    public String getClientTlsCertificate() {
        return clientTlsCertificate;
    }

    public RemoteRepository build() {
        validate();
        setRepoLayoutFromSettings();

        return new RemoteRepositoryImpl(key, settings, xraySettings, contentSync, description,
                excludesPattern, includesPattern, notes, blackedOut, propertySets,
                failedRetrievalCachePeriodSecs, hardFail, localAddress, missedRetrievalCachePeriodSecs, offline,
                password, proxy, retrievalCachePeriodSecs, shareConfiguration, socketTimeoutMillis,
                enableCookieManagement, allowAnyHostAuth, storeArtifactsLocally, synchronizeProperties,
                unusedArtifactsCleanupEnabled, unusedArtifactsCleanupPeriodHours, url, username, repoLayoutRef,
                assumedOfflinePeriodSecs, archiveBrowsingEnabled, listRemoteFolderItems, clientTlsCertificate, customProperties);
    }

    @Override
    public RepositoryType getRepositoryType() {
        return RepositoryTypeImpl.REMOTE;
    }
}
