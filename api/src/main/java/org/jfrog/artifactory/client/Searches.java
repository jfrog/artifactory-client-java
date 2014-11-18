package org.jfrog.artifactory.client;

import org.jfrog.artifactory.client.model.RepoPath;

import java.util.List;

/**
 * @author jbaruch
 * @author rnaegele
 * @since 13/08/12
 */
public interface Searches {

    Searches repositories(String... repositories);

    Searches artifactsByName(String name);

    Searches artifactsCreatedSince(long sinceMillis);

    Searches artifactsCreatedInDateRange(long fromMillis, long toMillis);

    List<RepoPath> doSearch();

    PropertyFilters itemsByProperty();

}
