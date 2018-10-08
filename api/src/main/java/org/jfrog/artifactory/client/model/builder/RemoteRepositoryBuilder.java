package org.jfrog.artifactory.client.model.builder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.jfrog.artifactory.client.model.ContentSync;
import org.jfrog.artifactory.client.model.RemoteRepository;

/**
 * @author jbaruch
 * @since 13/08/12
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface RemoteRepositoryBuilder extends NonVirtualRepositoryBuilder<RemoteRepositoryBuilder, RemoteRepository> {

    RemoteRepositoryBuilder url(String url);

    String getUrl();

    RemoteRepositoryBuilder username(String username);

    String getUsername();

    RemoteRepositoryBuilder password(String password);

    String getPassword();

    RemoteRepositoryBuilder proxy(String proxy);

    String getProxy();

    RemoteRepositoryBuilder hardFail(boolean hardFail);

    boolean isHardFail();

    RemoteRepositoryBuilder offline(boolean offline);

    boolean isOffline();

    RemoteRepositoryBuilder storeArtifactsLocally(boolean storeArtifactsLocally);

    boolean isStoreArtifactsLocally();

    RemoteRepositoryBuilder socketTimeoutMillis(int socketTimeoutMillis);

    int getSocketTimeoutMillis();

    RemoteRepositoryBuilder enableCookieManagement(boolean cookieManagementEnabled);

    boolean isEnableCookieManagement();

    RemoteRepositoryBuilder bypassHeadRequests(boolean cookieManagementEnabled);

    boolean isBypassHeadRequests();

    RemoteRepositoryBuilder allowAnyHostAuth(boolean allowAnyHostAuth);

    boolean isAllowAnyHostAuth();

    RemoteRepositoryBuilder localAddress(String localAddress);

    String getLocalAddress();

    RemoteRepositoryBuilder retrievalCachePeriodSecs(int retrievalCachePeriodSecs);

    int getRetrievalCachePeriodSecs();

    RemoteRepositoryBuilder missedRetrievalCachePeriodSecs(int missedRetrievalCachePeriodSecs);

    int getMissedRetrievalCachePeriodSecs();

    RemoteRepositoryBuilder failedRetrievalCachePeriodSecs(int failedRetrievalCachePeriodSecs);

    int getFailedRetrievalCachePeriodSecs();

    RemoteRepositoryBuilder unusedArtifactsCleanupEnabled(boolean unusedArtifactsCleanupEnabled);

    boolean isUnusedArtifactsCleanupEnabled();

    RemoteRepositoryBuilder unusedArtifactsCleanupPeriodHours(int unusedArtifactsCleanupPeriodHours);

    int getUnusedArtifactsCleanupPeriodHours();

    RemoteRepositoryBuilder shareConfiguration(boolean shareConfiguration);

    boolean isShareConfiguration();

    RemoteRepositoryBuilder synchronizeProperties(boolean synchronizeProperties);

    boolean isSynchronizeProperties();

    RemoteRepositoryBuilder assumedOfflinePeriodSecs(long assumedOfflinePeriodSecs);

    long getAssumedOfflinePeriodSecs();

    RemoteRepositoryBuilder listRemoteFolderItems(boolean listRemoteFolderItems);

    boolean isListRemoteFolderItems();

    RemoteRepositoryBuilder contentSync(ContentSync contentSync);

    ContentSync getContentSync();

    RemoteRepositoryBuilder clientTlsCertificate(String clientTlsCertificate);

    String getClientTlsCertificate();
}
