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

    private Searches(Artifactory artifactory) {
        this.artifactory = artifactory
    }

    Searches repositories(String... repositories) {
        this.reposFilter << repositories
        this
    }

    List<String> search(String name) throws HttpResponseException {
        doSearch('artifact', [name: name])
    }

    private List<String> doSearch(String url, Map query) throws HttpResponseException {
        if (reposFilter) {
            query.repos = reposFilter.join(',')
        }
        def result = artifactory.getSlurper("${SEARCHES_API}$url", query)
        result.results.collect { it.uri }
    }

    PropertyFilters filterBy() {
        def propertyFilters = [:]
        propertyFilters.filters = []
        propertyFilters.property = {String name, Object... values ->
            def filter = [:]
            filter.name = name
            filter.values = values
            propertyFilters.filters << filter
            propertyFilters as PropertyFilters
        }
        propertyFilters.search = {
            def propertiesQuery = (propertyFilters.filters as List).collectEntries {filter ->
                [filter.name, filter.values.join(',')]
            }
            doSearch('prop', propertiesQuery) as List<String>
        }
        propertyFilters as PropertyFilters
    }

    static interface PropertyFilters {
        PropertyFilters property(String name, Object... values)

        List<String> search() throws HttpResponseException
    }

}
