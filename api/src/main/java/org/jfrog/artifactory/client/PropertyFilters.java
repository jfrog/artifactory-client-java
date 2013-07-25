package org.jfrog.artifactory.client;

import org.jfrog.artifactory.client.model.RepoPath;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author jbaruch
 * @since 13/08/12
 */
public interface PropertyFilters {
    PropertyFilters property(String name, Object... values);

    PropertyFilters properties(Map<String, ?> property);

    PropertyFilters repositories(String... repositories);

    List<RepoPath> doSearch() throws IOException;
}