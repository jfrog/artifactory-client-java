package org.jfrog.artifactory.client.impl

import groovyx.net.http.HttpResponseException
import org.jfrog.artifactory.client.Artifactory
import org.jfrog.artifactory.client.PropertyFilters
import org.jfrog.artifactory.client.Searches
import org.jfrog.artifactory.client.model.RepoPath
import org.jfrog.artifactory.client.model.SearchResult
import org.jfrog.artifactory.client.model.SearchResultImpl
import org.jfrog.artifactory.client.model.SearchResultReport
import org.jfrog.artifactory.client.model.impl.RepoPathImpl

/**
 *
 * @author jbaruch
 * @author rnaegele
 * @since 03/08/12
 */
class SearchesImpl implements Searches {

    private String baseApiPath
    private ArtifactoryImpl artifactory
    private List<String> reposFilter = []
    private String searchUrl
    private Map searchQuery

    SearchesImpl(Artifactory artifactory, String baseApiPath) {
        this.artifactory = artifactory as ArtifactoryImpl
        this.baseApiPath = baseApiPath
    }

    Searches repositories(String... repositories) {
        this.reposFilter.addAll(repositories)
        this
    }

    Searches artifactsByName(String name) {
        this.searchUrl = 'artifact'
        this.searchQuery = [name: name]
        this
    }

    Searches artifactsCreatedSince(final long sinceMillis) {
        artifactsCreatedInDateRange(sinceMillis, -1L)
    }

    Searches artifactsCreatedInDateRange(final long fromMillis, final long toMillis) {
        this.searchUrl = 'creation'
        this.searchQuery = [from: fromMillis as String]
        if (toMillis >= 0) {
            this.searchQuery << ['to': toMillis as String]
        }
        this
    }

    @Override
    Searches artifactsByGavc() {
        this.searchUrl = 'gavc'
        this.searchQuery = [:]
        this
    }

    @Override
    Searches artifactsLatestVersion() {
        this.searchUrl = 'latestVersion'
        this.searchQuery = [:]
        this
    }

    @Override
    Searches groupId(String groupId) {
        this.searchQuery << [g: groupId]
        this
    }

    @Override
    Searches artifactId(String artifactId) {
        this.searchQuery << [a: artifactId]
        this
    }

    @Override
    Searches version(String version) {
        this.searchQuery << [v: version]
        this
    }

    @Override
    Searches classifier(String classifier) {
        this.searchQuery << [c: classifier]
        this
    }

    List<RepoPath> doSearch() {
        if (!searchUrl) {
            throw new IllegalArgumentException("Search url wasn't set. Please call one of the 'artifacts...' methods before calling 'search()'")
        }
        search(searchUrl, searchQuery)
    }

    String doRawSearch() {
        if (!searchUrl) {
            throw new IllegalArgumentException("Search url wasn't set. Please call one of the 'artifacts...' methods before calling 'search()'")
        }
        rawSearch(searchUrl, searchQuery)
    }

    private List<RepoPath> search(String url, Map query) {
        if (url.equals("latestVersion")) {
            throw new IllegalArgumentException("For search 'latestVersion' use doRawSearch.")
        }

        if (reposFilter) {
            query.repos = reposFilter.join(',')
        }
        try {
            StringBuilder path = new StringBuilder();
            path.append("?");
            for (Map.Entry<String, Object> entry : query.entrySet()) {
                path.append(entry.getKey()).append("=").append(entry.getValue()).append("&")
            }
            SearchResultImpl searchResults = artifactory.get("${getSearcherApi()}$url/$path", SearchResultImpl, SearchResult)
            List<RepoPath> pathList = new ArrayList<>();
            for (SearchResultReport searchResultReport : searchResults.getResults()) {
                String uri = searchResultReport.getUri();
                String fullPath = uri.split(baseApiPath + '/storage/')[1]
                String repo = fullPath.substring(0,fullPath.indexOf('/'))
                pathList.add(new RepoPathImpl(repo, fullPath - (repo + '/')))
            }

            return pathList;
        } catch (HttpResponseException e) {
            return []
        }
    }

    private String rawSearch(String url, Map query) {
        if (reposFilter) {
            query.repos = reposFilter.join(',')
        }
        try {
            StringBuilder path = new StringBuilder();
            path.append("?");
            for (Map.Entry<String, Object> entry : query.entrySet()) {
                path.append(entry.getKey()).append("=").append(entry.getValue()).append("&")
            }
            return artifactory.get("${getSearcherApi()}$url/$path", String, null)
        } catch (HttpResponseException e) {
            return ""
        }
    }

    PropertyFilters itemsByProperty() {
        new PropertyFiltersImpl(this)
    }

    String getSearcherApi() {
        return baseApiPath + "/search/";
    }
}
