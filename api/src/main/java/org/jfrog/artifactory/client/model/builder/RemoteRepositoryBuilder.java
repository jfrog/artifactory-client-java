package org.jfrog.artifactory.client.model.builder;

import org.jfrog.artifactory.client.model.ContentSync;
import org.jfrog.artifactory.client.model.RemoteRepository;

/**
 * @author jbaruch
 * @since 13/08/12
 */
public interface RemoteRepositoryBuilder extends NonVirtualRepositoryBuilder<RemoteRepositoryBuilder, RemoteRepository> {

    RemoteRepositoryBuilder url(String url);

    RemoteRepositoryBuilder username(String username);

    RemoteRepositoryBuilder password(String password);

    RemoteRepositoryBuilder proxy(String proxy);

    RemoteRepositoryBuilder hardFail(boolean hardFail);

    RemoteRepositoryBuilder offline(boolean offline);

    RemoteRepositoryBuilder storeArtifactsLocally(boolean storeArtifactsLocally);

    RemoteRepositoryBuilder socketTimeoutMillis(int socketTimeoutMillis);

    RemoteRepositoryBuilder enableCookieManagement(boolean cookieManagementEnabled);

    RemoteRepositoryBuilder allowAnyHostAuth(boolean allowAnyHostAuth);

    RemoteRepositoryBuilder localAddress(String localAddress);

    RemoteRepositoryBuilder retrievalCachePeriodSecs(int retrievalCachePeriodSecs);

    RemoteRepositoryBuilder missedRetrievalCachePeriodSecs(int missedRetrievalCachePeriodSecs);

    RemoteRepositoryBuilder failedRetrievalCachePeriodSecs(int failedRetrievalCachePeriodSecs);

    RemoteRepositoryBuilder unusedArtifactsCleanupEnabled(boolean unusedArtifactsCleanupEnabled);

    RemoteRepositoryBuilder unusedArtifactsCleanupPeriodHours(int unusedArtifactsCleanupPeriodHours);

    RemoteRepositoryBuilder shareConfiguration(boolean shareConfiguration);

    RemoteRepositoryBuilder synchronizeProperties(boolean synchronizeProperties);

    RemoteRepositoryBuilder assumedOfflinePeriodSecs(long assumedOfflinePeriodSecs);

    RemoteRepositoryBuilder listRemoteFolderItems(boolean listRemoteFolderItems);

    RemoteRepositoryBuilder contentSync(ContentSync contentSync);

}
