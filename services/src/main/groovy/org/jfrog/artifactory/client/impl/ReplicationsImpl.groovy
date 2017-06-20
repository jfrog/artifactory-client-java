package org.jfrog.artifactory.client.impl

import groovyx.net.http.ContentType
import groovyx.net.http.HttpResponseException
import net.sf.json.JSONArray
import net.sf.json.JSONObject
import org.jfrog.artifactory.client.Replications
import org.jfrog.artifactory.client.model.LocalReplication
import org.jfrog.artifactory.client.model.Replication
import org.jfrog.artifactory.client.model.impl.LocalReplicationImpl
import org.jfrog.artifactory.client.model.impl.RemoteReplicationImpl
import org.jfrog.artifactory.client.model.impl.RepositoryTypeImpl

class ReplicationsImpl implements Replications {

    private String baseApiPath

    private ArtifactoryImpl artifactory

    private String repoKey

    ReplicationsImpl(ArtifactoryImpl artifactory, String baseApiPath, String repoKey) {
        this.artifactory = artifactory
        this.baseApiPath = baseApiPath
        this.repoKey = repoKey
    }

    @Override
    void createOrReplace(Replication replication) {
        artifactory.put("${getReplicationsApi()}${repoKey}", [:], ContentType.ANY, null, ContentType.JSON, replication)
    }

    @Override
    void delete() {
        // Determine the type of the repository (not all repository types support replications)
        def repository = artifactory.repository(repoKey).get()

        if (!repository) {
            throw new RuntimeException("The repository '${repoKey}' doesn't exist")
        }
        if (repository.rclass == RepositoryTypeImpl.VIRTUAL) {
            throw new UnsupportedOperationException("The method isn't supported for a ${repository.rclass} repository")
        }

        def path = "${getReplicationsApi()}${repoKey}"

        artifactory.delete(path, [:], ContentType.JSON)
    }

    @Override
    String getReplicationsApi() {
        return baseApiPath + "/replications/";
    }
}