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

    Searches groupId(String groupId);

    Searches artifactId(String artifactId);

    Searches version(String version);

    Searches classifier(String classifier);

    Searches artifactsByName(String name);

    Searches artifactsCreatedSince(long sinceMillis);

    Searches artifactsCreatedInDateRange(long fromMillis, long toMillis);

    Searches artifactVersion();

    Searches artifactGAVC();

    List<RepoPath> doSearch();

    PropertyFilters itemsByProperty();

}
