package org.artifactory.client.model.builder

import org.artifactory.client.model.NonVirtualRepository

import static org.artifactory.client.model.NonVirtualRepository.SnapshotVersionBehavior.non_unique

/**
 *
 * @author jbaruch
 * @since 31/07/12
 */
abstract class NonVirtualRepositoryBuilderBase<T extends NonVirtualRepositoryBuilderBase> extends RepositoryBuilderBase<T> {

    private boolean blackedOut = false
    private boolean handleReleases = true
    private boolean handleSnapshots = true
    private int maxUniqueSnapshots = 0
    private List<String> propertySets = new ArrayList<String>()
    private boolean suppressPomConsistencyChecks = false
    private NonVirtualRepository.SnapshotVersionBehavior snapshotVersionBehavior = non_unique

    T blackedOut(boolean blackedOut) {
        this.blackedOut = blackedOut
        this as T
    }

    T handleReleases(boolean handleReleases) {
        this.handleReleases = handleReleases
        this as T
    }

    T handleSnapshots(boolean handleSnapshots) {
        this.handleSnapshots = handleSnapshots
        this as T
    }

    T maxUniqueSnapshots(int maxUniqueSnapshots) {
        this.maxUniqueSnapshots = maxUniqueSnapshots
        this as T
    }

    T propertySets(List<String> propertySets) {
        this.propertySets = propertySets
        this as T
    }

    T snapshotVersionBehavior(NonVirtualRepository.SnapshotVersionBehavior snapshotVersionBehavior) {
        this.snapshotVersionBehavior = snapshotVersionBehavior
        this as T
    }

    T suppressPomConsistencyChecks(boolean suppressPomConsistencyChecks) {
        this.suppressPomConsistencyChecks = suppressPomConsistencyChecks
        this as T
    }


}
