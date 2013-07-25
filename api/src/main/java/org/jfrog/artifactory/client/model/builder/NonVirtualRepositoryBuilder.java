package org.jfrog.artifactory.client.model.builder;

import org.jfrog.artifactory.client.model.NonVirtualRepository;
import org.jfrog.artifactory.client.model.SnapshotVersionBehavior;

import java.util.List;

/**
 * @author jbaruch
 * @since 13/08/12
 */
public interface NonVirtualRepositoryBuilder<B extends NonVirtualRepositoryBuilder, R extends NonVirtualRepository> extends RepositoryBuilder<B, R> {

    B blackedOut(boolean blackedOut);

    B handleReleases(boolean handleReleases);

    B handleSnapshots(boolean handleSnapshots);

    B maxUniqueSnapshots(int maxUniqueSnapshots);

    B propertySets(List<String> propertySets);

    B snapshotVersionBehavior(SnapshotVersionBehavior snapshotVersionBehavior);

    B suppressPomConsistencyChecks(boolean suppressPomConsistencyChecks);

    B archiveBrowsingEnabled(boolean archiveBrowsingEnabled);
}