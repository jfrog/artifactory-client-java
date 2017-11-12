package org.jfrog.artifactory.client.model.builder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.jfrog.artifactory.client.model.VirtualRepository;
import java.util.Collection;

/**
 * @author jbaruch
 * @since 13/08/12
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface VirtualRepositoryBuilder extends RepositoryBuilder<VirtualRepositoryBuilder, VirtualRepository> {

    VirtualRepositoryBuilder repositories(Collection<String> repositories);

    Collection<String> getRepositories();

    VirtualRepositoryBuilder artifactoryRequestsCanRetrieveRemoteArtifacts(boolean artifactoryRequestsCanRetrieveRemoteArtifacts);

    boolean isArtifactoryRequestsCanRetrieveRemoteArtifacts();

    VirtualRepositoryBuilder defaultDeploymentRepo(String deploymentRepo);

    String getDefaultDeploymentRepo();

}