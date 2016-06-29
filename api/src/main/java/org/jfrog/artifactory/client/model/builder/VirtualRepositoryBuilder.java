package org.jfrog.artifactory.client.model.builder;

import org.jfrog.artifactory.client.model.VirtualRepository;

import java.util.Collection;

/**
 * @author jbaruch
 * @since 13/08/12
 */
public interface VirtualRepositoryBuilder extends RepositoryBuilder<VirtualRepositoryBuilder, VirtualRepository> {

    VirtualRepositoryBuilder repositories(Collection<String> repositories);

    VirtualRepositoryBuilder artifactoryRequestsCanRetrieveRemoteArtifacts(boolean artifactoryRequestsCanRetrieveRemoteArtifacts);

    VirtualRepositoryBuilder defaultDeploymentRepo(String deploymentRepo);

}