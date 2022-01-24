package org.jfrog.artifactory.client.model.impl;

import org.jfrog.artifactory.client.model.FederatedRepository;
import org.jfrog.artifactory.client.model.LocalRepository;
import org.jfrog.artifactory.client.model.RemoteRepository;
import org.jfrog.artifactory.client.model.VirtualRepository;
import org.jfrog.artifactory.client.model.builder.*;

/**
 * @author jbaruch
 * @since 31/07/12
 */
public class RepositoryBuildersImpl implements RepositoryBuilders {
    private RepositoryBuildersImpl() {
    }

    public static RepositoryBuilders create() {
        return new RepositoryBuildersImpl();
    }

    public RemoteRepositoryBuilder remoteRepositoryBuilder() {
        return new RemoteRepositoryBuilderImpl();
    }

    public RemoteRepositoryBuilder builderFrom(RemoteRepository from) {
        return new RemoteRepositoryBuilderImpl().
                repositorySettings(from.getRepositorySettings()).blackedOut(from.isBlackedOut()).description(from.getDescription())
                .excludesPattern(from.getExcludesPattern()).includesPattern(from.getIncludesPattern()).key(from.getKey())
                .notes(from.getNotes()).propertySets(from.getPropertySets()).url(from.getUrl()).username(from.getUsername())
                .password(from.getPassword()).proxy(from.getProxy()).hardFail(from.isHardFail()).offline(from.isOffline())
                .storeArtifactsLocally(from.isStoreArtifactsLocally()).socketTimeoutMillis(from.getSocketTimeoutMillis())
                .allowAnyHostAuth(from.isAllowAnyHostAuth()).enableCookieManagement(from.isEnableCookieManagement())
                .bypassHeadRequests(from.isBypassHeadRequests())
                .localAddress(from.getLocalAddress()).retrievalCachePeriodSecs(from.getRetrievalCachePeriodSecs())
                .missedRetrievalCachePeriodSecs(from.getMissedRetrievalCachePeriodSecs()).failedRetrievalCachePeriodSecs(from.getFailedRetrievalCachePeriodSecs())
                .unusedArtifactsCleanupEnabled(from.isUnusedArtifactsCleanupEnabled()).unusedArtifactsCleanupPeriodHours(from.getUnusedArtifactsCleanupPeriodHours())
                .shareConfiguration(from.isShareConfiguration()).synchronizeProperties(from.isSynchronizeProperties()).repoLayoutRef(from.getRepoLayoutRef())
                .assumedOfflinePeriodSecs(from.getAssumedOfflinePeriodSecs()).archiveBrowsingEnabled(from.isArchiveBrowsingEnabled())
                .listRemoteFolderItems(from.isListRemoteFolderItems()).contentSync(from.getContentSync()).customProperties(from.getCustomProperties())
                .clientTlsCertificate(from.getClientTlsCertificate()).xraySettings(from.getXraySettings());
    }

    public LocalRepositoryBuilder localRepositoryBuilder() {
        return new LocalRepositoryBuilderImpl();
    }

    public LocalRepositoryBuilder builderFrom(LocalRepository from) {
        return new LocalRepositoryBuilderImpl().repositorySettings(from.getRepositorySettings())
                .blackedOut(from.isBlackedOut()).description(from.getDescription()).excludesPattern(from.getExcludesPattern())
                .includesPattern(from.getIncludesPattern()).key(from.getKey()).notes(from.getNotes()).propertySets(from.getPropertySets())
                .archiveBrowsingEnabled(from.isArchiveBrowsingEnabled()).customProperties(from.getCustomProperties()).xraySettings(from.getXraySettings());
    }

    public FederatedRepositoryBuilder federatedRepositoryBuilder() {
        return new FederatedRepositoryBuilderImpl();
    }

    public FederatedRepositoryBuilder builderFrom(FederatedRepository from) {
        return new FederatedRepositoryBuilderImpl().repositorySettings(from.getRepositorySettings()).members(from.getMembers())
                .blackedOut(from.isBlackedOut()).description(from.getDescription()).excludesPattern(from.getExcludesPattern())
                .includesPattern(from.getIncludesPattern()).key(from.getKey()).notes(from.getNotes()).propertySets(from.getPropertySets())
                .archiveBrowsingEnabled(from.isArchiveBrowsingEnabled()).customProperties(from.getCustomProperties()).xraySettings(from.getXraySettings());
    }

    public VirtualRepositoryBuilder virtualRepositoryBuilder() {
        return new VirtualRepositoryBuilderImpl();
    }

    public VirtualRepositoryBuilder builderFrom(VirtualRepository from) {
        return new VirtualRepositoryBuilderImpl().repositorySettings(from.getRepositorySettings()).description(from.getDescription())
                .excludesPattern(from.getExcludesPattern()).includesPattern(from.getIncludesPattern()).key(from.getKey()).notes(from.getNotes())
                .artifactoryRequestsCanRetrieveRemoteArtifacts(from.isArtifactoryRequestsCanRetrieveRemoteArtifacts())
                .repositories(from.getRepositories()).defaultDeploymentRepo(from.getDefaultDeploymentRepo())
                .customProperties(from.getCustomProperties()).xraySettings(from.getXraySettings());
    }
}
