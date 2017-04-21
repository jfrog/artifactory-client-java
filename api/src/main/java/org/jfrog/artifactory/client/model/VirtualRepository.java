package org.jfrog.artifactory.client.model;

import java.util.Collection;

/**
 * @author jbaruch
 * @since 30/07/12
 */
public interface VirtualRepository extends Repository {

    Collection<String> getRepositories();

    boolean isArtifactoryRequestsCanRetrieveRemoteArtifacts();

    String getDefaultDeploymentRepo();

}
