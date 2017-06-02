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
        if (repository.rclass == RepositoryTypeImpl.VIRTUAL) {
            throw new UnsupportedOperationException("The method isn't supported for a ${repository.rclass} repository")
        }

        def path = "${getReplicationsApi()}${repoKey}"

        try {
            // The structure of the JSON response depends on the type of the repository
            def json = artifactory.get(path, [:], ContentType.JSON)

            if (repository.rclass == RepositoryTypeImpl.LOCAL) {
                // For a local repository, the REST service always returns an array of JSON objects
                return json.collect { new LocalReplicationImpl(it) }
            } else if (repository.rclass == RepositoryTypeImpl.REMOTE) {
                // For a remote repository, the REST service returns a JSON object (not an array)
                return [ new RemoteReplicationImpl(json) ]
            }
        } catch (HttpResponseException e) {
            if (e.statusCode == 404) {
                // The REST API returns a 404 status code when the repository defines no replication
                return []
            }

            throw e
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
        if (repository.rclass != RepositoryTypeImpl.LOCAL) {
            throw new UnsupportedOperationException("The method isn't supported for a ${repository.rclass} repository")
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

        artifactory.put(path, [:], ContentType.ANY, null, ContentType.JSON, payload)
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

    @Override
    String getReplicationsMultipleApi() {
        return baseApiPath + "/replications/multiple/";
    }
}