package org.jfrog.artifactory.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * @author Siva Singaravelan
 * @since 01/07/2022
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface FederatedRepository extends Repository, NonVirtualRepository {

    List<FederatedMember> getMembers();
}
