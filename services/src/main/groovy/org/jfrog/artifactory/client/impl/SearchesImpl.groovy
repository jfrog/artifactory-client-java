package org.jfrog.artifactory.client.impl

import groovyx.net.http.ContentType
import groovyx.net.http.HttpResponseException
import org.jfrog.artifactory.client.Artifactory
import org.jfrog.artifactory.client.PropertyFilters
import org.jfrog.artifactory.client.Searches
import org.jfrog.artifactory.client.model.RepoPath
import org.jfrog.artifactory.client.model.impl.RepoPathImpl

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

    SearchesImpl(Artifactory artifactory) {
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

    List<RepoPath> doSearch() {
        if (!quickSearchTerm) {
            throw new IllegalArgumentException("Search term wasn't set. Please call 'artifactsByName(name to search)' before calling 'search()'")
        }
        search('artifact', [name: quickSearchTerm])
    }

    private List<RepoPath> search(String url, Map query) {
        if (reposFilter) {
            query.repos = reposFilter.join(',')
        }
        try {
            def result = artifactory.get("${SEARCHES_API}$url", query, ContentType.JSON)
            result.results.collect {
                String path = it.uri.split('/api/storage/')[1]
                String repo = path.split('/')[0]
                new RepoPathImpl(repo, path - (repo + '/'))
            }
        } catch (HttpResponseException e) {
            return []
        }
    }

    PropertyFilters itemsByProperty() {
        new PropertyFiltersImpl(this)
    }

}
