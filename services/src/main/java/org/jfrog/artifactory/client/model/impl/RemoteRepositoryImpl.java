package org.jfrog.artifactory.client.model.impl;

import org.jfrog.artifactory.client.model.RemoteRepository;
import org.jfrog.artifactory.client.model.RepositoryType;
import org.jfrog.artifactory.client.model.repository.settings.RepositorySettings;
import org.jfrog.artifactory.client.model.repository.settings.XraySettings;

import java.util.List;

/**
 * @author jbaruch
 * @since 29/07/12
 */
public class RemoteRepositoryImpl extends NonVirtualRepositoryBase implements RemoteRepository {

    private String url;
    private String username;
    private String password;
    private String proxy;
    private boolean hardFail;
    private boolean offline;
    private boolean storeArtifactsLocally;
    private int socketTimeoutMillis;
    private boolean enableCookieManagement;
    private boolean allowAnyHostAuth;
    private String localAddress;
    private int retrievalCachePeriodSecs;
    private int missedRetrievalCachePeriodSecs;
    private int failedRetrievalCachePeriodSecs;
    private boolean unusedArtifactsCleanupEnabled;
    private int unusedArtifactsCleanupPeriodHours;
    private boolean shareConfiguration;
    private boolean synchronizeProperties;
    private long assumedOfflinePeriodSecs;

    private RemoteRepositoryImpl() {
        repoLayoutRef = MAVEN_2_REPO_LAYOUT;
    }

    RemoteRepositoryImpl(String key, RepositorySettings settings, XraySettings xraySettings, String description,
                         String excludesPattern, String includesPattern, String notes, boolean blackedOut,
                         List<String> propertySets,
                         int failedRetrievalCachePeriodSecs, boolean hardFail, String localAddress,
                         int missedRetrievalCachePeriodSecs, boolean offline, String password, String proxy,
                         int retrievalCachePeriodSecs, boolean shareConfiguration, int socketTimeoutMillis, boolean cookieManagementEnabled, boolean allowAnyHostAuth, boolean storeArtifactsLocally, boolean synchronizeProperties,
                         boolean unusedArtifactsCleanupEnabled, int unusedArtifactsCleanupPeriodHours, String url, String username, String repoLayoutRef,
                         long assumedOfflinePeriodSecs, boolean archiveBrowsingEnabled) {

        super(key, settings, xraySettings, description, excludesPattern, includesPattern,
            notes, blackedOut,
            propertySets,
            repoLayoutRef, archiveBrowsingEnabled);

        this.failedRetrievalCachePeriodSecs = failedRetrievalCachePeriodSecs;
        this.hardFail = hardFail;
        this.localAddress = localAddress;
        this.missedRetrievalCachePeriodSecs = missedRetrievalCachePeriodSecs;
        this.offline = offline;
        this.password = password;
        this.proxy = proxy;
        this.retrievalCachePeriodSecs = retrievalCachePeriodSecs;
        this.shareConfiguration = shareConfiguration;
        this.socketTimeoutMillis = socketTimeoutMillis;
        this.enableCookieManagement = cookieManagementEnabled;
        this.allowAnyHostAuth = allowAnyHostAuth;
        this.storeArtifactsLocally = storeArtifactsLocally;
        this.synchronizeProperties = synchronizeProperties;
        this.unusedArtifactsCleanupEnabled = unusedArtifactsCleanupEnabled;
        this.unusedArtifactsCleanupPeriodHours = unusedArtifactsCleanupPeriodHours;
        this.url = url;
        this.username = username;
        this.assumedOfflinePeriodSecs = assumedOfflinePeriodSecs;
    }

    @Override
    public String getUrl() {
        return url;
    }

    private void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String getUsername() {
        return username;
    }

    private void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    private void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getProxy() {
        return proxy;
    }

    private void setProxy(String proxy) {
        this.proxy = proxy;
    }

    @Override
    public boolean isHardFail() {
        return hardFail;
    }

    private void setHardFail(boolean hardFail) {
        this.hardFail = hardFail;
    }

    @Override
    public boolean isOffline() {
        return offline;
    }

    private void setOffline(boolean offline) {
        this.offline = offline;
    }

    @Override
    public boolean isStoreArtifactsLocally() {
        return storeArtifactsLocally;
    }

    private void setStoreArtifactsLocally(boolean storeArtifactsLocally) {
        this.storeArtifactsLocally = storeArtifactsLocally;
    }

    @Override
    public int getSocketTimeoutMillis() {
        return socketTimeoutMillis;
    }

    private void setSocketTimeoutMillis(int socketTimeoutMillis) {
        this.socketTimeoutMillis = socketTimeoutMillis;
    }

    @Override
    public boolean isEnableCookieManagement() {
        return enableCookieManagement;
    }

    public void setEnableCookieManagement(boolean cookieManagementEnbaled) {
        this.enableCookieManagement = cookieManagementEnbaled;
    }

    @Override
    public boolean isAllowAnyHostAuth() {
        return allowAnyHostAuth;
    }

    public void setAllowAnyHostAuth(boolean allowAnyHostAuth) {
        this.allowAnyHostAuth = allowAnyHostAuth;
    }

    @Override
    public String getLocalAddress() {
        return localAddress;
    }

    private void setLocalAddress(String localAddress) {
        this.localAddress = localAddress;
    }

    @Override
    public int getRetrievalCachePeriodSecs() {
        return retrievalCachePeriodSecs;
    }

    private void setRetrievalCachePeriodSecs(int retrievalCachePeriodSecs) {
        this.retrievalCachePeriodSecs = retrievalCachePeriodSecs;
    }

    @Override
    public int getMissedRetrievalCachePeriodSecs() {
        return missedRetrievalCachePeriodSecs;
    }

    private void setMissedRetrievalCachePeriodSecs(int missedRetrievalCachePeriodSecs) {
        this.missedRetrievalCachePeriodSecs = missedRetrievalCachePeriodSecs;
    }

    @Override
    public int getFailedRetrievalCachePeriodSecs() {
        return failedRetrievalCachePeriodSecs;
    }

    private void setFailedRetrievalCachePeriodSecs(int failedRetrievalCachePeriodSecs) {
        this.failedRetrievalCachePeriodSecs = failedRetrievalCachePeriodSecs;
    }

    @Override
    public boolean isUnusedArtifactsCleanupEnabled() {
        return unusedArtifactsCleanupEnabled;
    }

    private void setUnusedArtifactsCleanupEnabled(boolean unusedArtifactsCleanupEnabled) {
        this.unusedArtifactsCleanupEnabled = unusedArtifactsCleanupEnabled;
    }

    @Override
    public int getUnusedArtifactsCleanupPeriodHours() {
        return unusedArtifactsCleanupPeriodHours;
    }

    private void setUnusedArtifactsCleanupPeriodHours(int unusedArtifactsCleanupPeriodHours) {
        this.unusedArtifactsCleanupPeriodHours = unusedArtifactsCleanupPeriodHours;
    }

    @Override
    public boolean isShareConfiguration() {
        return shareConfiguration;
    }

    private void setShareConfiguration(boolean shareConfiguration) {
        this.shareConfiguration = shareConfiguration;
    }

    @Override
    public boolean isSynchronizeProperties() {
        return synchronizeProperties;
    }

    private void setSynchronizeProperties(boolean synchronizeProperties) {
        this.synchronizeProperties = synchronizeProperties;
    }

    @Override
    public RepositoryType getRclass() {
        return RepositoryTypeImpl.REMOTE;
    }

    @Override
    public long getAssumedOfflinePeriodSecs() {
        return assumedOfflinePeriodSecs;
    }

    private void setAssumedOfflinePeriodSecs(long assumedOfflinePeriodSecs) {
        this.assumedOfflinePeriodSecs = assumedOfflinePeriodSecs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        RemoteRepositoryImpl that = (RemoteRepositoryImpl) o;

        if (failedRetrievalCachePeriodSecs != that.failedRetrievalCachePeriodSecs) return false;
        if (hardFail != that.hardFail) return false;
        if (missedRetrievalCachePeriodSecs != that.missedRetrievalCachePeriodSecs) return false;
        if (offline != that.offline) return false;
        if (retrievalCachePeriodSecs != that.retrievalCachePeriodSecs) return false;
        if (shareConfiguration != that.shareConfiguration) return false;
        if (socketTimeoutMillis != that.socketTimeoutMillis) return false;
        if (allowAnyHostAuth != that.allowAnyHostAuth) return false;
        if (enableCookieManagement != that.allowAnyHostAuth) return false;
        if (storeArtifactsLocally != that.storeArtifactsLocally) return false;
        if (synchronizeProperties != that.synchronizeProperties) return false;
        if (unusedArtifactsCleanupEnabled != that.unusedArtifactsCleanupEnabled) return false;
        if (unusedArtifactsCleanupPeriodHours != that.unusedArtifactsCleanupPeriodHours) return false;
        if (localAddress != null ? !localAddress.equals(that.localAddress) : that.localAddress != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (proxy != null ? !proxy.equals(that.proxy) : that.proxy != null) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (proxy != null ? proxy.hashCode() : 0);
        result = 31 * result + (hardFail ? 1 : 0);
        result = 31 * result + (offline ? 1 : 0);
        result = 31 * result + (storeArtifactsLocally ? 1 : 0);
        result = 31 * result + socketTimeoutMillis;
        result = 31 * result + (allowAnyHostAuth ? 1 : 0);
        result = 31 * result + (enableCookieManagement ? 1 : 0);
        result = 31 * result + (localAddress != null ? localAddress.hashCode() : 0);
        result = 31 * result + retrievalCachePeriodSecs;
        result = 31 * result + missedRetrievalCachePeriodSecs;
        result = 31 * result + failedRetrievalCachePeriodSecs;
        result = 31 * result + (unusedArtifactsCleanupEnabled ? 1 : 0);
        result = 31 * result + unusedArtifactsCleanupPeriodHours;
        result = 31 * result + (shareConfiguration ? 1 : 0);
        result = 31 * result + (synchronizeProperties ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return super.toString() + "\nRemoteRepositoryImpl{" +
                "failedRetrievalCachePeriodSecs=" + failedRetrievalCachePeriodSecs +
                ", url='" + url + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", proxy='" + proxy + '\'' +
                ", hardFail=" + hardFail +
                ", offline=" + offline +
                ", storeArtifactsLocally=" + storeArtifactsLocally +
                ", socketTimeoutMillis=" + socketTimeoutMillis +
                ", allowAnyHostAuth=" + allowAnyHostAuth +
                ", enableCookieManagement=" + enableCookieManagement +
                ", localAddress='" + localAddress + '\'' +
                ", retrievalCachePeriodSecs=" + retrievalCachePeriodSecs +
                ", missedRetrievalCachePeriodSecs=" + missedRetrievalCachePeriodSecs +
                ", unusedArtifactsCleanupEnabled=" + unusedArtifactsCleanupEnabled +
                ", unusedArtifactsCleanupPeriodHours=" + unusedArtifactsCleanupPeriodHours +
                ", shareConfiguration=" + shareConfiguration +
                ", synchronizeProperties=" + synchronizeProperties +
                '}';
    }
}
