package org.jfrog.artifactory.client.model.repository.settings.impl;

import org.jfrog.artifactory.client.model.PackageType;
import org.jfrog.artifactory.client.model.impl.LocalRepoChecksumPolicyTypeImpl;
import org.jfrog.artifactory.client.model.impl.PackageTypeImpl;
import org.jfrog.artifactory.client.model.impl.RemoteRepoChecksumPolicyTypeImpl;
import org.jfrog.artifactory.client.model.impl.SnapshotVersionBehaviorImpl;
import org.jfrog.artifactory.client.model.repository.PomCleanupPolicy;
import org.jfrog.artifactory.client.model.repository.settings.AbstractRepositorySettings;
import org.jfrog.artifactory.client.model.repository.settings.MavenRepositorySettings;

/**
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
public class MavenRepositorySettingsImpl extends AbstractRepositorySettings implements MavenRepositorySettings {
    public static String defaultLayout = "maven-2-default";
    private Integer maxUniqueSnapshots;
    private Boolean handleReleases;
    private Boolean handleSnapshots;
    private Boolean suppressPomConsistencyChecks;
    private SnapshotVersionBehaviorImpl snapshotVersionBehavior;
    private LocalRepoChecksumPolicyTypeImpl checksumPolicyType;
    private Boolean fetchJarsEagerly;
    private Boolean fetchSourcesEagerly;
    private RemoteRepoChecksumPolicyTypeImpl remoteRepoChecksumPolicyType;
    private Boolean listRemoteFolderItems;
    private Boolean rejectInvalidJars;
    private PomCleanupPolicy pomRepositoryReferencesCleanupPolicy;
    private String keyPair;
    private Boolean forceMavenAuthentication;

    public MavenRepositorySettingsImpl() {
        this(defaultLayout);
    }

    protected MavenRepositorySettingsImpl(String repoLayout) {
        super(repoLayout);
    }

    @Override
    public PackageType getPackageType() {
        return PackageTypeImpl.maven;
    }

    public Integer getMaxUniqueSnapshots() {
        return maxUniqueSnapshots;
    }

    public void setMaxUniqueSnapshots(Integer maxUniqueSnapshots) {
        this.maxUniqueSnapshots = maxUniqueSnapshots;
    }

    public Boolean getHandleReleases() {
        return handleReleases;
    }

    public void setHandleReleases(Boolean handleReleases) {
        this.handleReleases = handleReleases;
    }

    public Boolean getHandleSnapshots() {
        return handleSnapshots;
    }

    public void setHandleSnapshots(Boolean handleSnapshots) {
        this.handleSnapshots = handleSnapshots;
    }

    public Boolean getSuppressPomConsistencyChecks() {
        return suppressPomConsistencyChecks;
    }

    public void setSuppressPomConsistencyChecks(Boolean suppressPomConsistencyChecks) {
        this.suppressPomConsistencyChecks = suppressPomConsistencyChecks;
    }

    public SnapshotVersionBehaviorImpl getSnapshotVersionBehavior() {
        return snapshotVersionBehavior;
    }

    public void setSnapshotVersionBehavior(SnapshotVersionBehaviorImpl snapshotVersionBehavior) {
        this.snapshotVersionBehavior = snapshotVersionBehavior;
    }

    public LocalRepoChecksumPolicyTypeImpl getChecksumPolicyType() {
        return checksumPolicyType;
    }

    public void setChecksumPolicyType(LocalRepoChecksumPolicyTypeImpl checksumPolicyType) {
        this.checksumPolicyType = checksumPolicyType;
    }

    public Boolean getFetchJarsEagerly() {
        return fetchJarsEagerly;
    }

    public void setFetchJarsEagerly(Boolean fetchJarsEagerly) {
        this.fetchJarsEagerly = fetchJarsEagerly;
    }

    public Boolean getFetchSourcesEagerly() {
        return fetchSourcesEagerly;
    }

    public void setFetchSourcesEagerly(Boolean fetchSourcesEagerly) {
        this.fetchSourcesEagerly = fetchSourcesEagerly;
    }

    public RemoteRepoChecksumPolicyTypeImpl getRemoteRepoChecksumPolicyType() {
        return remoteRepoChecksumPolicyType;
    }

    public void setRemoteRepoChecksumPolicyType(RemoteRepoChecksumPolicyTypeImpl remoteRepoChecksumPolicyType) {
        this.remoteRepoChecksumPolicyType = remoteRepoChecksumPolicyType;
    }

    public Boolean getListRemoteFolderItems() {
        return listRemoteFolderItems;
    }

    public void setListRemoteFolderItems(Boolean listRemoteFolderItems) {
        this.listRemoteFolderItems = listRemoteFolderItems;
    }

    public Boolean getRejectInvalidJars() {
        return rejectInvalidJars;
    }

    public void setRejectInvalidJars(Boolean rejectInvalidJars) {
        this.rejectInvalidJars = rejectInvalidJars;
    }

    public PomCleanupPolicy getPomRepositoryReferencesCleanupPolicy() {
        return pomRepositoryReferencesCleanupPolicy;
    }

    public void setPomRepositoryReferencesCleanupPolicy(PomCleanupPolicy pomRepositoryReferencesCleanupPolicy) {
        this.pomRepositoryReferencesCleanupPolicy = pomRepositoryReferencesCleanupPolicy;
    }

    public Boolean getForceMavenAuthentication() { return forceMavenAuthentication; }

    public void setForceMavenAuthentication(Boolean forceMavenAuthentication) {
        this.forceMavenAuthentication = forceMavenAuthentication;
    }

    public String getKeyPair() {
        return keyPair;
    }

    public void setKeyPair(String keyPair) {
        this.keyPair = keyPair;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MavenRepositorySettingsImpl)) return false;

        MavenRepositorySettingsImpl that = (MavenRepositorySettingsImpl) o;

        if (maxUniqueSnapshots != null ? !maxUniqueSnapshots.equals(that.maxUniqueSnapshots) : that.maxUniqueSnapshots != null)
            return false;
        if (handleReleases != null ? !handleReleases.equals(that.handleReleases) : that.handleReleases != null)
            return false;
        if (handleSnapshots != null ? !handleSnapshots.equals(that.handleSnapshots) : that.handleSnapshots != null)
            return false;
        if (suppressPomConsistencyChecks != null ? !suppressPomConsistencyChecks.equals(that.suppressPomConsistencyChecks) : that.suppressPomConsistencyChecks != null)
            return false;
        if (snapshotVersionBehavior != that.snapshotVersionBehavior) return false;
        if (checksumPolicyType != that.checksumPolicyType) return false;
        if (fetchJarsEagerly != null ? !fetchJarsEagerly.equals(that.fetchJarsEagerly) : that.fetchJarsEagerly != null)
            return false;
        if (fetchSourcesEagerly != null ? !fetchSourcesEagerly.equals(that.fetchSourcesEagerly) : that.fetchSourcesEagerly != null)
            return false;
        if (remoteRepoChecksumPolicyType != that.remoteRepoChecksumPolicyType) return false;
        if (listRemoteFolderItems != null ? !listRemoteFolderItems.equals(that.listRemoteFolderItems) : that.listRemoteFolderItems != null)
            return false;
        if (rejectInvalidJars != null ? !rejectInvalidJars.equals(that.rejectInvalidJars) : that.rejectInvalidJars != null)
            return false;
        if (pomRepositoryReferencesCleanupPolicy != that.pomRepositoryReferencesCleanupPolicy) return false;
        if (forceMavenAuthentication != null ? !forceMavenAuthentication.equals(that.forceMavenAuthentication) : that.forceMavenAuthentication != null)
            return false;
        return keyPair != null ? keyPair.equals(that.keyPair) : that.keyPair == null;
    }

    @Override
    public int hashCode() {
        int result = maxUniqueSnapshots != null ? maxUniqueSnapshots.hashCode() : 0;
        result = 31 * result + (handleReleases != null ? handleReleases.hashCode() : 0);
        result = 31 * result + (handleSnapshots != null ? handleSnapshots.hashCode() : 0);
        result = 31 * result + (suppressPomConsistencyChecks != null ? suppressPomConsistencyChecks.hashCode() : 0);
        result = 31 * result + (snapshotVersionBehavior != null ? snapshotVersionBehavior.hashCode() : 0);
        result = 31 * result + (checksumPolicyType != null ? checksumPolicyType.hashCode() : 0);
        result = 31 * result + (fetchJarsEagerly != null ? fetchJarsEagerly.hashCode() : 0);
        result = 31 * result + (fetchSourcesEagerly != null ? fetchSourcesEagerly.hashCode() : 0);
        result = 31 * result + (remoteRepoChecksumPolicyType != null ? remoteRepoChecksumPolicyType.hashCode() : 0);
        result = 31 * result + (listRemoteFolderItems != null ? listRemoteFolderItems.hashCode() : 0);
        result = 31 * result + (rejectInvalidJars != null ? rejectInvalidJars.hashCode() : 0);
        result = 31 * result + (pomRepositoryReferencesCleanupPolicy != null ? pomRepositoryReferencesCleanupPolicy.hashCode() : 0);
        result = 31 * result + (keyPair != null ? keyPair.hashCode() : 0);
        result = 31 * result + (forceMavenAuthentication != null ? forceMavenAuthentication.hashCode() : 0);
        return result;
    }
}
