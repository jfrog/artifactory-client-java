package org.artifactory.client.impl

import groovyx.net.http.HttpResponseException
import org.artifactory.client.Artifactory
import org.artifactory.client.PropertyFilters
import org.artifactory.client.Searches
/**
 *
 * @author jbaruch
 * @since 03/08/12
 */
class SearchesImpl implements Searches {

    private String SEARCHES_API = "/api/search/"
    private ArtifactoryImpl artifactory
    private List<String> reposFilter = []
    private String quickSearchTerm

    private SearchesImpl(Artifactory artifactory) {
        this.artifactory = artifactory as ArtifactoryImpl
    }

    Searches repositories(String... repositories) {
        this.reposFilter.addAll(repositories)
        this
    }

    Searches artifactsByName(String name) {
        this.quickSearchTerm = name
        this
    }

    List<String> doSearch() throws HttpResponseException {
        if (!quickSearchTerm) {
            throw new IllegalArgumentException("Search term wasn't set. Please call 'artifactsByName(name to search)' before calling 'search()'")
        }
        search('artifact', [name: quickSearchTerm])
    }

    private List<String> search(String url, Map query) throws HttpResponseException {
        if (reposFilter) {
            query.repos = reposFilter.join(',')
        }
        def result = artifactory.getSlurper("${SEARCHES_API}$url", query)
        result.results.collect { it.uri }
    }

    PropertyFilters itemsByProperty() {
        new PropertyFiltersImpl(this)
    }

}
