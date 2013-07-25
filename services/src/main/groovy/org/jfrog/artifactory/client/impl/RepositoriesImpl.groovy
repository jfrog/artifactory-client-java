package org.jfrog.artifactory.client.impl

import com.fasterxml.jackson.core.type.TypeReference
import org.jfrog.artifactory.client.Repositories
import org.jfrog.artifactory.client.RepositoryHandle
import org.jfrog.artifactory.client.model.LightweightRepository
import org.jfrog.artifactory.client.model.Repository
import org.jfrog.artifactory.client.model.RepositoryType
import org.jfrog.artifactory.client.model.builder.RepositoryBuilders
import org.jfrog.artifactory.client.model.builder.impl.RepositoryBuildersImpl
import org.jfrog.artifactory.client.model.impl.LightweightRepositoryImpl

import static groovyx.net.http.ContentType.JSON
import static groovyx.net.http.ContentType.TEXT
/**
 *
 * @author jbaruch
 * @since 29/07/12
 */

class RepositoriesImpl implements Repositories {

    private ArtifactoryImpl artifactory

    static private RepositoryBuilders builders = RepositoryBuildersImpl.create()

    RepositoriesImpl(ArtifactoryImpl artifactory) {
        this.artifactory = artifactory
    }

    //TODO overload without index
    String create(int position, Repository configuration) {
        artifactory.put("$REPOSITORIES_API${configuration.key}", [pos: position], TEXT, null, JSON, configuration)
    }

    String update(Repository configuration) {
        artifactory.post("$REPOSITORIES_API${configuration.key}", [:], TEXT, null, JSON, configuration)
    }

    RepositoryHandle repository(String repo) {
        new RepositoryHandleImpl(artifactory, repo)
    }

    RepositoryBuilders builders() {
        builders
    }

    List<LightweightRepository> list(RepositoryType repositoryType) {
        artifactory.get(REPOSITORIES_API, [type: repositoryType.toString()], JSON, new TypeReference<List<LightweightRepositoryImpl>>() {})
    }

}