package org.artifactory.client.model.impl;

import org.artifactory.client.model.ChecksumPolicyType;
import org.artifactory.client.model.LocalRepository;

import java.util.List;

/**
 * @author jbaruch
 * @since 29/07/12
 */
public class LocalRepositoryImpl extends NonVirtualRepositoryBase implements LocalRepository {

    private ChecksumPolicyTypeImpl checksumPolicyType;


    private LocalRepositoryImpl() {
        checksumPolicyType = ChecksumPolicyTypeImpl.client_checksums;
        repoLayoutRef = MAVEN_2_REPO_LAYOUT;
    }

    private LocalRepositoryImpl(String description, String excludesPattern, String includesPattern, String key, String notes, boolean blackedOut, boolean handleReleases, boolean handleSnapshots, int maxUniqueSnapshots, List<String> propertySets, SnapshotVersionBehaviorImpl snapshotVersionBehavior, boolean suppressPomConsistencyChecks, ChecksumPolicyTypeImpl checksumPolicyType, String repoLayoutRef) {
        super(description, excludesPattern, includesPattern, key, notes, blackedOut, handleReleases, handleSnapshots, maxUniqueSnapshots, propertySets, snapshotVersionBehavior, suppressPomConsistencyChecks, repoLayoutRef);
        this.checksumPolicyType = checksumPolicyType;
    }

    @Override
    public ChecksumPolicyType getChecksumPolicyType() {
        return checksumPolicyType;
    }

    private void setChecksumPolicyType(ChecksumPolicyTypeImpl checksumPolicyType) {
        this.checksumPolicyType = checksumPolicyType;
    }

    @Override
    public RepositoryTypeImpl getRclass() {
        return RepositoryTypeImpl.LOCAL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        LocalRepositoryImpl that = (LocalRepositoryImpl) o;

        return checksumPolicyType == that.checksumPolicyType;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (checksumPolicyType != null ? checksumPolicyType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return super.toString() + "\nLocalRepository{" +
                "checksumPolicyType=" + checksumPolicyType +
                '}';
    }
}
