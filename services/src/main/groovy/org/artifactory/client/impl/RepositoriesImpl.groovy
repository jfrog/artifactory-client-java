package org.artifactory.client.impl

import com.fasterxml.jackson.core.type.TypeReference
import org.artifactory.client.Repositories
import org.artifactory.client.RepositoryHandle
import org.artifactory.client.model.LightweightRepository
import org.artifactory.client.model.Repository
import org.artifactory.client.model.RepositoryType
import org.artifactory.client.model.builder.impl.RepositoryBuildersImpl
import org.artifactory.client.model.impl.LightweightRepositoryImpl
/**
 *
 * @author jbaruch
 * @since 29/07/12
 */

class RepositoriesImpl implements Repositories {

    private ArtifactoryImpl artifactory

    static private RepositoryBuildersImpl builders = new RepositoryBuildersImpl()

    RepositoriesImpl(ArtifactoryImpl artifactory) {
        this.artifactory = artifactory
    }

    String create(int position, Repository configuration) {
        artifactory.put("$REPOSITORIES_API${configuration.getKey()}", [pos: position], configuration)
    }

    String update(Repository configuration) {
        artifactory.post("$REPOSITORIES_API${configuration.getKey()}", configuration)
    }

    RepositoryHandle repository(String repo) {
        new RepositoryHandleImpl(artifactory, repo)
    }

    RepositoryBuildersImpl builders() {
        builders
    }

    List<LightweightRepository> list(RepositoryType repositoryType) {
        artifactory.getJson(REPOSITORIES_API, new TypeReference<List<LightweightRepositoryImpl>>() {}, [type: repositoryType.toString()])
    }

}