package org.jfrog.artifactory.client.impl

import com.fasterxml.jackson.core.type.TypeReference
import org.apache.http.entity.ContentType
import org.jfrog.artifactory.client.Repositories
import org.jfrog.artifactory.client.RepositoryHandle
import org.jfrog.artifactory.client.impl.util.Util
import org.jfrog.artifactory.client.model.LightweightRepository
import org.jfrog.artifactory.client.model.Repository
import org.jfrog.artifactory.client.model.RepositoryType
import org.jfrog.artifactory.client.model.builder.RepositoryBuilders
import org.jfrog.artifactory.client.model.impl.RepositoryBuildersImpl
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
        String result = Util.getStringFromObject(configuration);
        String queryPath = "?pos=" + position;
        artifactory.put("${getRepositoriesApi()}${configuration.getKey()}" + queryPath, ContentType.APPLICATION_JSON, result, new HashMap<String, String>(), null, -1, String, null )
    }

    @Override
    String update(Repository configuration) {
        String toString = Util.getStringFromObject(configuration);
        artifactory.post("${getRepositoriesApi()}${configuration.getKey()}", ContentType.APPLICATION_JSON, toString, null, String, null)
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
        StringBuilder queryPath = new StringBuilder("?type=");
        if (repositoryType != null) {
            queryPath.append(repositoryType.toString());
        }
        String result = artifactory.get(getRepositoriesApi() + queryPath.toString(), String, null);
        return Util.parseObjectWithTypeReference(result, new TypeReference<List<LightweightRepositoryImpl>>() {
        });
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