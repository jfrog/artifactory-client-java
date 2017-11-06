package org.jfrog.artifactory.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Collection;

/**
 * @author jbaruch
 * @since 30/07/12
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface VirtualRepository extends Repository {

    Collection<String> getRepositories();

    boolean isArtifactoryRequestsCanRetrieveRemoteArtifacts();

    String getDefaultDeploymentRepo();

}
