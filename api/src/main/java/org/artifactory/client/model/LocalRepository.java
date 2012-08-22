package org.artifactory.client.model;

/**
 * @author jbaruch
 * @since 30/07/12
 */
public interface LocalRepository extends Repository, NonVirtualRepository {
    ChecksumPolicyType getChecksumPolicyType();

    boolean isCalculateYumMetadata();

    int getYumRootDepth();
}
