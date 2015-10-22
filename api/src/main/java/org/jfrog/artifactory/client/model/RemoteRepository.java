package org.jfrog.artifactory.client.model;

/**
 * @author jbaruch
 * @since 30/07/12
 */
public interface RemoteRepository extends Repository, NonVirtualRepository {
    String getUrl();

    String getUsername();

    String getPassword();

    String getProxy();

    RemoteRepoChecksumPolicyType getRemoteRepoChecksumPolicyType();

    boolean isHardFail();

    boolean isOffline();

    boolean isStoreArtifactsLocally();

    int getSocketTimeoutMillis();

    boolean isEnableCookieManagement();

    boolean isAllowAnyHostAuth();

    String getLocalAddress();

    int getRetrievalCachePeriodSecs();

    int getMissedRetrievalCachePeriodSecs();

    int getFailedRetrievalCachePeriodSecs();

    boolean isUnusedArtifactsCleanupEnabled();

    int getUnusedArtifactsCleanupPeriodHours();

    boolean isFetchJarsEagerly();

    boolean isFetchSourcesEagerly();

    boolean isShareConfiguration();

    boolean isSynchronizeProperties();

    long getAssumedOfflinePeriodSecs();

    boolean isListRemoteFolderItems();

    boolean isRejectInvalidJars();

    boolean isP2Support();
}
