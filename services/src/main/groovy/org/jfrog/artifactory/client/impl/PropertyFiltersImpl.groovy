package org.jfrog.artifactory.client.impl

import org.jfrog.artifactory.client.PropertyFilters
import org.jfrog.artifactory.client.model.RepoPath

/**
 *
 * @author jbaruch
 * @since 13/08/12
 */
class PropertyFiltersImpl implements PropertyFilters {

    def filters = []
    SearchesImpl searches

    private PropertyFiltersImpl(SearchesImpl searches) {
        this.searches = searches
    }

    @Override
    PropertyFilters property(String name, Object... values) {
        def filter = [:]
        filter.name = name
        filter.values = values
        filters << filter
        this
    }

    @Override
    PropertyFilters properties(Map<String, ?> property) {
        def filter = [:]
        def name = property.keySet().toList()[0]
        filter.name = name
        def values = property[name]
        filter.values = values instanceof Collection ? values : [values]
        filters << filter
        this
    }

    @Override
    PropertyFilters repositories(String... repositories) {
        searches.reposFilter.addAll(repositories)
        this
    }

    @Override
    List<RepoPath> doSearch() {
        def propertiesQuery = (filters as List).collectEntries {filter ->
            [filter.name, filter.values.join(',')]
        }
        searches.search('prop', propertiesQuery) as List<RepoPath>

    }
}
