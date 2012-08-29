package org.artifactory.client;

import java.io.IOException;
import java.util.List;

/**
 * @author jbaruch
 * @since 13/08/12
 */
public interface Searches {

    Searches repositories(String... repositories);

    Searches artifactsByName(String name);

    List<String> doSearch() throws IOException;

    PropertyFilters itemsByProperty();

}
