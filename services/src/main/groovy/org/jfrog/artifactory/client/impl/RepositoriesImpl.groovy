package org.jfrog.artifactory.client.impl

import com.fasterxml.jackson.core.type.TypeReference
import groovyx.net.http.ContentType
import org.jfrog.artifactory.client.Repositories
import org.jfrog.artifactory.client.RepositoryHandle
import org.jfrog.artifactory.client.model.LightweightRepository
import org.jfrog.artifactory.client.model.Repository
import org.jfrog.artifactory.client.model.RepositoryType
import org.jfrog.artifactory.client.model.builder.RepositoryBuilders
import org.jfrog.artifactory.client.model.builder.impl.RepositoryBuildersImpl
import org.jfrog.artifactory.client.model.impl.LightweightRepositoryImpl
import org.jfrog.artifactory.client.impl.ArtifactoryImpl

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
        artifactory.put("$REPOSITORIES_API${configuration.getKey()}", [pos: position], ContentType.TEXT, null, ContentType.JSON, configuration)
    }

    String update(Repository configuration) {
        artifactory.post("$REPOSITORIES_API${configuration.getKey()}", [:], ContentType.TEXT, null, ContentType.JSON, configuration)
    }

    RepositoryHandle repository(String repo) {
        new RepositoryHandleImpl(artifactory, repo)
    }

    RepositoryBuilders builders() {
        builders
    }

    List<LightweightRepository> list(RepositoryType repositoryType) {
        artifactory.get(REPOSITORIES_API, [type: repositoryType.toString()], ContentType.JSON, new TypeReference<List<LightweightRepositoryImpl>>() {})
    }

}