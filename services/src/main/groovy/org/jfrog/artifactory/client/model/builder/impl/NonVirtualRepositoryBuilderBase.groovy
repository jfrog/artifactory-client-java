package org.jfrog.artifactory.client.model.builder.impl

import org.jfrog.artifactory.client.model.NonVirtualRepository
import org.jfrog.artifactory.client.model.SnapshotVersionBehavior
import org.jfrog.artifactory.client.model.builder.NonVirtualRepositoryBuilder
import org.jfrog.artifactory.client.model.impl.SnapshotVersionBehaviorImpl

import static SnapshotVersionBehaviorImpl.non_unique
/**
 *
 * @author jbaruch
 * @since 31/07/12
 */
abstract class NonVirtualRepositoryBuilderBase<B extends NonVirtualRepositoryBuilder, R extends NonVirtualRepository> extends RepositoryBuilderBase<B, R> implements NonVirtualRepositoryBuilder<B, R> {
    protected boolean blackedOut = false
    protected boolean handleReleases = true
    protected boolean handleSnapshots = true
    protected int maxUniqueSnapshots = 0
    protected List<String> propertySets = new ArrayList<String>()
    protected boolean suppressPomConsistencyChecks = false
    protected SnapshotVersionBehavior snapshotVersionBehavior = non_unique
    protected boolean archiveBrowsingEnabled = false

    NonVirtualRepositoryBuilderBase(Set<String> supportedTypes) {
        super(supportedTypes)
    }

    @Override
    B blackedOut(boolean blackedOut) {
        this.blackedOut = blackedOut
        this as B
    }

    @Override
    B handleReleases(boolean handleReleases) {
        this.handleReleases = handleReleases
        this as B
    }

    @Override
    B handleSnapshots(boolean handleSnapshots) {
        this.handleSnapshots = handleSnapshots
        this as B
    }

    @Override
    B maxUniqueSnapshots(int maxUniqueSnapshots) {
        this.maxUniqueSnapshots = maxUniqueSnapshots
        this as B
    }

    @Override
    B snapshotVersionBehavior(SnapshotVersionBehavior snapshotVersionBehavior) {
        this.snapshotVersionBehavior = snapshotVersionBehavior
        this as B
    }

    @Override
    B suppressPomConsistencyChecks(boolean suppressPomConsistencyChecks) {
        this.suppressPomConsistencyChecks = suppressPomConsistencyChecks
        this as B
    }

    @Override
    B propertySets(List<String> propertySets) {
        this.propertySets = propertySets
        this as B
    }

    B archiveBrowsingEnabled(boolean archiveBrowsingEnabled) {
        this.archiveBrowsingEnabled = archiveBrowsingEnabled
        this as B
    }
}
