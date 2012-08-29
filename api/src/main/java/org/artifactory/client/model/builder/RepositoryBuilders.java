package org.artifactory.client.model.builder;

import org.artifactory.client.model.LocalRepository;
import org.artifactory.client.model.RemoteRepository;
import org.artifactory.client.model.VirtualRepository;

/**
 * @author jbaruch
 * @since 13/08/12
 */
public interface RepositoryBuilders {

    RemoteRepositoryBuilder remoteRepositoryBuilder();

    RemoteRepositoryBuilder builderFrom(RemoteRepository from);

    LocalRepositoryBuilder localRepositoryBuilder();

    LocalRepositoryBuilder builderFrom(LocalRepository from);

    VirtualRepositoryBuilder virtualRepositoryBuilder();

    VirtualRepositoryBuilder builderFrom(VirtualRepository from);
}