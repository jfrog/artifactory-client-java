package org.artifactory.client

import groovyx.net.http.HttpResponseException

/**
 *
 * @author jbaruch
 * @since 03/08/12
 */
class Searches {

    private String SEARCHES_API = "/api/search/"
    private Artifactory artifactory
    private List<String> reposFilter = []
    private String quickSearchTerm

    private Searches(Artifactory artifactory) {
        this.artifactory = artifactory
    }

    Searches repositories(String... repositories) {
        this.reposFilter.addAll(repositories)
        this
    }

    Searches artifactsByName(String name){
        this.quickSearchTerm = name
        this
    }

    List<String> search() throws HttpResponseException {
        if (!quickSearchTerm){
            throw new IllegalArgumentException("Search term wasn't set. Please call 'artifactsByName(name to search)' before calling 'search()'")
        }
        doSearch('artifact', [name: quickSearchTerm])
    }

    private List<String> doSearch(String url, Map query) throws HttpResponseException {
        if (reposFilter) {
            query.repos = reposFilter.join(',')
        }
        def result = artifactory.getSlurper("${SEARCHES_API}$url", query)
        result.results.collect { it.uri }
    }

    PropertyFilters properties() {
        new PropertyFiltersImpl()
    }

    interface PropertyFilters {
        PropertyFilters property(String name, Object... values)

        PropertyFilters property(Map<String, ?> property)

        PropertyFilters repositories(String... repositories)

        List<String> search() throws HttpResponseException
    }

    class PropertyFiltersImpl implements PropertyFilters{

        def filters = []

        @Override
        PropertyFilters property(String name, Object... values) {
            def filter = [:]
            filter.name = name
            filter.values = values
            filters << filter
            this
        }

        @Override
        PropertyFilters property(Map<String, ?> property) {
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
            reposFilter.addAll(repositories)
            this
        }

        @Override
        List<String> search() {
            def propertiesQuery = (filters as List).collectEntries {filter ->
                [filter.name, filter.values.join(',')]
            }
            doSearch('prop', propertiesQuery) as List<String>

        }
    }

}
