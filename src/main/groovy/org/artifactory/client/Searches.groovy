package org.artifactory.client

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

    Searches limitToRepository(String repositories) {
        this.reposFilter << repositories
        this
    }

    List<String> searchArtifact(String name) {
        def query = [:]
        query.name = name
        if (reposFilter) {
            query.repos = reposFilter.join(',')
        }
        def result = artifactory.getSlurper("${SEARCHES_API}artifact", query)
        result.results.collect { it.uri }
    }


}
