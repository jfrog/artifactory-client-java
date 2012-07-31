package org.artifactory.client.model.impl;

import org.artifactory.client.model.NonVirtualRepository;

import java.util.List;

/**
 * @author jbaruch
 * @since 29/07/12
 */
public abstract class NonVirtualRepositoryBase extends RepositoryBase implements NonVirtualRepository {

    private boolean handleReleases;
    private boolean handleSnapshots;
    private int maxUniqueSnapshots;
    private SnapshotVersionBehavior snapshotVersionBehavior;
    private boolean suppressPomConsistencyChecks;
    private boolean blackedOut;
    private List<String> propertySets;
    protected String repoLayoutRef;

    protected NonVirtualRepositoryBase() {
    }

    protected NonVirtualRepositoryBase(String description, String excludesPattern, String includesPattern, String key, String notes, boolean blackedOut, boolean handleReleases, boolean handleSnapshots, int maxUniqueSnapshots, List<String> propertySets, SnapshotVersionBehavior snapshotVersionBehavior, boolean suppressPomConsistencyChecks, String repoLayoutRef) {
        super(description, excludesPattern, includesPattern, key, notes);
        this.blackedOut = blackedOut;
        this.handleReleases = handleReleases;
        this.handleSnapshots = handleSnapshots;
        this.maxUniqueSnapshots = maxUniqueSnapshots;
        this.propertySets = propertySets;
        this.snapshotVersionBehavior = snapshotVersionBehavior;
        this.suppressPomConsistencyChecks = suppressPomConsistencyChecks;
        this.repoLayoutRef = repoLayoutRef;
    }

    @Override
    public boolean isHandleReleases() {
        return handleReleases;
    }

    private void setHandleReleases(boolean handleReleases) {
        this.handleReleases = handleReleases;
    }

    @Override
    public boolean isHandleSnapshots() {
        return handleSnapshots;
    }

    private void setHandleSnapshots(boolean handleSnapshots) {
        this.handleSnapshots = handleSnapshots;
    }

    @Override
    public int getMaxUniqueSnapshots() {
        return maxUniqueSnapshots;
    }

    private void setMaxUniqueSnapshots(int maxUniqueSnapshots) {
        this.maxUniqueSnapshots = maxUniqueSnapshots;
    }

    @Override
    public SnapshotVersionBehavior getSnapshotVersionBehavior() {
        return snapshotVersionBehavior;
    }

    private void setSnapshotVersionBehavior(SnapshotVersionBehavior snapshotVersionBehavior) {
        this.snapshotVersionBehavior = snapshotVersionBehavior;
    }

    @Override
    public boolean isSuppressPomConsistencyChecks() {
        return suppressPomConsistencyChecks;
    }

    private void setSuppressPomConsistencyChecks(boolean suppressPomConsistencyChecks) {
        this.suppressPomConsistencyChecks = suppressPomConsistencyChecks;
    }

    @Override
    public boolean isBlackedOut() {
        return blackedOut;
    }

    private void setBlackedOut(boolean blackedOut) {
        this.blackedOut = blackedOut;
    }

    @Override
    public List<String> getPropertySets() {
        return propertySets;
    }

    private void setPropertySets(List<String> propertySets) {
        this.propertySets = propertySets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        NonVirtualRepositoryBase that = (NonVirtualRepositoryBase) o;

        if (blackedOut != that.blackedOut) return false;
        if (handleReleases != that.handleReleases) return false;
        if (handleSnapshots != that.handleSnapshots) return false;
        if (maxUniqueSnapshots != that.maxUniqueSnapshots) return false;
        if (suppressPomConsistencyChecks != that.suppressPomConsistencyChecks) return false;
        if (propertySets != null ? !propertySets.equals(that.propertySets) : that.propertySets != null) return false;
        if (snapshotVersionBehavior != that.snapshotVersionBehavior) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (handleReleases ? 1 : 0);
        result = 31 * result + (handleSnapshots ? 1 : 0);
        result = 31 * result + maxUniqueSnapshots;
        result = 31 * result + (snapshotVersionBehavior != null ? snapshotVersionBehavior.hashCode() : 0);
        result = 31 * result + (suppressPomConsistencyChecks ? 1 : 0);
        result = 31 * result + (blackedOut ? 1 : 0);
        result = 31 * result + (propertySets != null ? propertySets.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return super.toString() + "\nNonVirtualRepository{" +
                "blackedOut=" + blackedOut +
                ", handleReleases=" + handleReleases +
                ", handleSnapshots=" + handleSnapshots +
                ", maxUniqueSnapshots=" + maxUniqueSnapshots +
                ", snapshotVersionBehavior=" + snapshotVersionBehavior +
                ", suppressPomConsistencyChecks=" + suppressPomConsistencyChecks +
                ", propertySets=" + propertySets +
                '}';
    }

    @Override
    public String getRepoLayoutRef() {
        return repoLayoutRef;
    }

    private void setRepoLayoutRef(String repoLayoutRef) {
        this.repoLayoutRef = repoLayoutRef;
    }
}
