package org.jfrog.artifactory.client.model.builder;

import org.jfrog.artifactory.client.model.VirtualRepository;

import java.util.List;

/**
 * @author jbaruch
 * @since 13/08/12
 */
public interface VirtualRepositoryBuilder extends RepositoryBuilder<VirtualRepositoryBuilder, VirtualRepository> {

    VirtualRepositoryBuilder repositories(List<String> repositories);

    VirtualRepositoryBuilder artifactoryRequestsCanRetrieveRemoteArtifacts(boolean artifactoryRequestsCanRetrieveRemoteArtifacts);

    VirtualRepositoryBuilder keyPair(String keyPair);

    VirtualRepositoryBuilder pomRepositoryReferencesCleanupPolicy(VirtualRepository.PomRepositoryReferencesCleanupPolicy pomRepositoryReferencesCleanupPolicy);
}