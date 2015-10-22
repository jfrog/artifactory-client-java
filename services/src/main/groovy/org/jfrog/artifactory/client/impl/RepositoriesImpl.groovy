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

/**
 *
 * @author jbaruch
 * @since 29/07/12
 */

class RepositoriesImpl implements Repositories {

    private String baseApiPath

    private ArtifactoryImpl artifactory

    static private RepositoryBuilders builders = RepositoryBuildersImpl.create()

    RepositoriesImpl(ArtifactoryImpl artifactory, String baseApiPath) {
        this.artifactory = artifactory
        this.baseApiPath = baseApiPath
    }

    @Override
    String create(int position, Repository configuration) {
        artifactory.put("${getRepositoriesApi()}${configuration.getKey()}", [pos: position], ContentType.TEXT, null, ContentType.JSON, configuration)
    }

    @Override
    String update(Repository configuration) {
        artifactory.post("${getRepositoriesApi()}${configuration.getKey()}", [:], ContentType.TEXT, null, ContentType.JSON, configuration)
    }

    @Override
    RepositoryHandle repository(String repo) {
        new RepositoryHandleImpl(artifactory, baseApiPath, this, repo)
    }

    @Override
    RepositoryBuilders builders() {
        builders
    }

    @Override
    List<LightweightRepository> list(RepositoryType repositoryType) {
        artifactory.get(getRepositoriesApi(), [type: repositoryType.toString()], ContentType.JSON, new TypeReference<List<LightweightRepositoryImpl>>() {})
    }

    @Override
    String getRepositoriesApi() {
        return baseApiPath + "/repositories/";
    }

    @Override
    String getReplicationApi() {
        return baseApiPath + "/replication/";
    }
}