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
                // changes depending on the number of replications returned by the server. This changed in version 4.15
                // (see the release notes) but is still required to properly process the response from old versions
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
    void createOrReplace(Replication replication) {
        artifactory.put("${getReplicationsApi()}${repoKey}", [:], ContentType.ANY, null, ContentType.JSON, replication)
    }

    @Override
    void createOrReplace(Collection<LocalReplication> replications) {
        assert replications

        // Determine the type of the repository (not all repository types support replications)
        def repository = artifactory.repository(repoKey).get()

        if (!repository) {
            throw new RuntimeException("The repository '${repoKey}' doesn't exist")
        }

        def path = "${getReplicationsMultipleApi()}${repoKey}"

        // The code below adapts a 'Get Repository Replication Configuration' service response into a
        // 'Create or Replace Local Multi-push Replication' service request

        // Use the first replication to infer the shared properties
        def first = replications.iterator().next() as LocalReplication

        // Convert the incoming list of replications into the correct request
        def array = new JSONArray()

        replications.each { replication ->
            def object = new JSONObject()
            object.put('url', replication.url)
            object.put('socketTimeoutMillis', replication.socketTimeoutMillis)
            object.put('username', replication.username)
            object.put('password', replication.password)
            object.put('enableEventReplication', replication.enableEventReplication)
            object.put('enabled', replication.enabled)
            object.put('syncDeletes', replication.syncDeletes)
            object.put('syncProperties', replication.syncProperties)
            object.put('syncStatistics', replication.syncStatistics)
            object.put('repoKey', replication.repoKey)

            array.add(object)
        }

        def payload = new JSONObject()
        payload.put('cronExp', first.cronExp)
        payload.put('enableEventReplication', first.enableEventReplication)
        payload.put('replications', array)

        if (repository.rclass == RepositoryTypeImpl.LOCAL) {
            artifactory.put(path, [:], ContentType.ANY, null, ContentType.JSON, payload)
        } else {
            throw new UnsupportedOperationException("The method isn't supported for a ${repository.rclass} repository")
        }
    }

    @Override
    void delete() {
        // Determine the type of the repository (not all repository types support replications)
        def repository = artifactory.repository(repoKey).get()

        if (!repository) {
            throw new RuntimeException("The repository '${repoKey}' doesn't exist")
        }

        def path = "${getReplicationsApi()}${repoKey}"

        if ((repository.rclass == RepositoryTypeImpl.LOCAL) || (repository.rclass == RepositoryTypeImpl.REMOTE)) {
            artifactory.delete(path, [:], ContentType.JSON)
        } else {
            throw new UnsupportedOperationException("The method isn't supported for a ${repository.rclass} repository")
        }
    }

    @Override
    String getReplicationsApi() {
        return baseApiPath + "/replications/";
    }

    @Override
    String getReplicationsMultipleApi() {
        return baseApiPath + "/replications/multiple/";
    }
}