package org.jfrog.artifactory.client.model.builder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.jfrog.artifactory.client.model.FederatedMember;
import org.jfrog.artifactory.client.model.FederatedRepository;

import java.util.List;

/**
 * @author Siva Singaravelan
 * @since 13/08/12
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface FederatedRepositoryBuilder extends NonVirtualRepositoryBuilder<FederatedRepositoryBuilder, FederatedRepository> {

    FederatedRepositoryBuilder members(List<FederatedMember> members);

    List<FederatedMember> getMembers();
}
