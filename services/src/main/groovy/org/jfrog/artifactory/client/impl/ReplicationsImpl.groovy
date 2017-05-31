package org.jfrog.artifactory.client.impl

import groovyx.net.http.ContentType
import groovyx.net.http.HttpResponseException
import org.jfrog.artifactory.client.Replications
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
    List<Replication> list() {
        // Determine the type of the repository (not all repository types support replications)
        def repository = artifactory.repository(repoKey).get()

        if (!repository) {
            throw new RuntimeException("The repository '${repoKey}' doesn't exist")
        }

        def path = "${getReplicationsApi()}${repoKey}"

        if ((repository.rclass == RepositoryTypeImpl.LOCAL) || (repository.rclass == RepositoryTypeImpl.REMOTE)) {
            try {
                // We can't use Jackson to convert the JSON into java objects because the structure of the JSON response
                // changes depending on the number of replications returned by the server
                // One replication -> The API returns a JSON object
                // Two replications -> The API returns an array of JSON objects
                def json = artifactory.get(path, [:], ContentType.JSON)

                if (repository.rclass == RepositoryTypeImpl.LOCAL) {
                    if (json instanceof List) {
                        return json.collect { new LocalReplicationImpl(it) }
                    } else {
                        return [ new LocalReplicationImpl(json) ]
                    }
                } else if (repository.rclass == RepositoryTypeImpl.REMOTE) {
                    if (json instanceof List) {
                        return json.collect { new RemoteReplicationImpl(it) }
                    } else {
                        return [ new RemoteReplicationImpl(json) ]
                    }
                }
            } catch (HttpResponseException e) {
                if (e.statusCode == 404) {
                    // The REST API returns a 404 status code when the repository defines no replication
                    return []
                }

                throw e
            }
        } else {
            throw new UnsupportedOperationException("The method isn't supported for a ${repository.rclass} repository")
        }
    }

    @Override
    String getReplicationsApi() {
        return baseApiPath + "/replications/";
    }
}