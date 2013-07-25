package org.jfrog.artifactory.client;

import org.jfrog.artifactory.client.model.RepoPath;

import java.util.List;

/**
 * @author jbaruch
 * @since 13/08/12
 */
public interface Searches {

    Searches repositories(String... repositories);

    Searches artifactsByName(String name);

    List<RepoPath> doSearch();

    PropertyFilters itemsByProperty();

}
