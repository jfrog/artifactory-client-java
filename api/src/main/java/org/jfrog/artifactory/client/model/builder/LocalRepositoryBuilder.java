package org.jfrog.artifactory.client.model.builder;

import org.jfrog.artifactory.client.model.ChecksumPolicyType;
import org.jfrog.artifactory.client.model.LocalRepository;

/**
 * @author jbaruch
 * @since 13/08/12
 */
public interface LocalRepositoryBuilder extends NonVirtualRepositoryBuilder<LocalRepositoryBuilder, LocalRepository> {

    LocalRepositoryBuilder checksumPolicyType(ChecksumPolicyType checksumPolicyType);

    LocalRepositoryBuilder calculateYumMetadata(boolean calculateYumMetadata);

    LocalRepositoryBuilder yumRootDepth(int yumRootDepth);


}

