package org.jfrog.artifactory.client.model.impl;

import org.jfrog.artifactory.client.model.ChecksumPolicyType;
import org.jfrog.artifactory.client.model.LocalRepository;
import org.jfrog.artifactory.client.model.PackageType;
import org.jfrog.artifactory.client.model.SnapshotVersionBehavior;

import java.util.List;

/**
 * @author jbaruch
 * @since 29/07/12
 */
public class LocalRepositoryImpl extends NonVirtualRepositoryBase implements LocalRepository {

    private ChecksumPolicyType checksumPolicyType;
    private boolean calculateYumMetadata;
    private int yumRootDepth;


    private LocalRepositoryImpl() {
        checksumPolicyType = ChecksumPolicyTypeImpl.client_checksums;
        repoLayoutRef = MAVEN_2_REPO_LAYOUT;
    }

    public LocalRepositoryImpl(String description, String excludesPattern, String includesPattern, String key, String notes,
                               boolean blackedOut, boolean handleReleases, boolean handleSnapshots, int maxUniqueSnapshots,
                               List<String> propertySets, SnapshotVersionBehavior snapshotVersionBehavior, boolean suppressPomConsistencyChecks, ChecksumPolicyType checksumPolicyType, String repoLayoutRef, PackageType packageType, boolean enableNuGetSupport, boolean archiveBrowsingEnabled, boolean calculateYumMetadata, int yumRootDepth, boolean enableGemsSupport, boolean enableNpmSupport, boolean enableVagrantSupport,
                               boolean enableBowerSupport, boolean enableGitLfsSupport, boolean enableDebianSupport,
                               boolean enableDockerSupport, boolean enablePypiSupport, boolean debianTrivialLayout) {

        super(description, excludesPattern, includesPattern, key, notes, blackedOut, handleReleases,
            handleSnapshots, maxUniqueSnapshots, propertySets, snapshotVersionBehavior, suppressPomConsistencyChecks,
            repoLayoutRef, packageType, enableNuGetSupport, archiveBrowsingEnabled, enableGemsSupport, enableNpmSupport,
            enableVagrantSupport, enableBowerSupport, enableGitLfsSupport, enableDebianSupport,
            enableDockerSupport, enablePypiSupport, debianTrivialLayout);

        this.checksumPolicyType = checksumPolicyType;
        this.calculateYumMetadata = calculateYumMetadata;
        this.yumRootDepth = yumRootDepth;
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
    public boolean isCalculateYumMetadata() {
        return calculateYumMetadata;
    }

    private void setCalculateYumMetadata(boolean calculateYumMetadata) {
        this.calculateYumMetadata = calculateYumMetadata;
    }

    @Override
    public int getYumRootDepth() {
        return yumRootDepth;
    }

    private void setYumRootDepth(int yumRootDepth) {
        this.yumRootDepth = yumRootDepth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        LocalRepositoryImpl that = (LocalRepositoryImpl) o;

        if (calculateYumMetadata != that.calculateYumMetadata) {
            return false;
        }
        if (yumRootDepth != that.yumRootDepth) {
            return false;
        }
        if (checksumPolicyType != that.checksumPolicyType) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (checksumPolicyType != null ? checksumPolicyType.hashCode() : 0);
        result = 31 * result + (calculateYumMetadata ? 1 : 0);
        result = 31 * result + yumRootDepth;
        return result;
    }

    @Override
    public String toString() {
        return "LocalRepositoryImpl{" +
                "checksumPolicyType=" + checksumPolicyType +
                ", calculateYumMetadata=" + calculateYumMetadata +
                ", yumRootDepth=" + yumRootDepth +
                '}';
    }
}
