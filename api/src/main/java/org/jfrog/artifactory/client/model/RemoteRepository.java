package org.jfrog.artifactory.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/**
 * @author jbaruch
 * @since 30/07/12
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface RemoteRepository extends Repository, NonVirtualRepository {

    String getUrl();

    String getUsername();

    String getPassword();

    String getProxy();

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

    boolean isShareConfiguration();

    boolean isSynchronizeProperties();

    long getAssumedOfflinePeriodSecs();

    boolean isListRemoteFolderItems();

    ContentSync getContentSync();

    String getClientTlsCertificate();
}
