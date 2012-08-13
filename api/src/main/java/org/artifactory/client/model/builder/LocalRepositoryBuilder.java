package org.artifactory.client.model.builder;

import org.artifactory.client.model.ChecksumPolicyType;
import org.artifactory.client.model.LocalRepository;

/**
 * @author jbaruch
 * @since 13/08/12
 */
public interface LocalRepositoryBuilder extends NonVirtualRepositoryBuilder<LocalRepositoryBuilder, LocalRepository> {

    LocalRepositoryBuilder checksumPolicyType(ChecksumPolicyType checksumPolicyType);
}

