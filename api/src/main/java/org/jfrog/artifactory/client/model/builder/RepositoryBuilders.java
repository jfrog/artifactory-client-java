package org.jfrog.artifactory.client.model.builder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.jfrog.artifactory.client.model.FederatedRepository;
import org.jfrog.artifactory.client.model.LocalRepository;
import org.jfrog.artifactory.client.model.RemoteRepository;
import org.jfrog.artifactory.client.model.VirtualRepository;

/**
 * @author jbaruch
 * @since 13/08/12
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface RepositoryBuilders {

    RemoteRepositoryBuilder remoteRepositoryBuilder();

    RemoteRepositoryBuilder builderFrom(RemoteRepository from);

    LocalRepositoryBuilder localRepositoryBuilder();

    LocalRepositoryBuilder builderFrom(LocalRepository from);

    FederatedRepositoryBuilder federatedRepositoryBuilder();

    FederatedRepositoryBuilder builderFrom(FederatedRepository from);

    VirtualRepositoryBuilder virtualRepositoryBuilder();

    VirtualRepositoryBuilder builderFrom(VirtualRepository from);
}