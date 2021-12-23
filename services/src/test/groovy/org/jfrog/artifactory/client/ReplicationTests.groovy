package org.jfrog.artifactory.client

import org.apache.http.client.HttpResponseException
import org.jfrog.artifactory.client.model.RepositoryType
import org.jfrog.artifactory.client.model.impl.LocalReplicationImpl
import org.jfrog.artifactory.client.model.repository.settings.RepositorySettings
import org.testng.annotations.Test

import static org.testng.Assert.assertTrue;

class ReplicationTests extends BaseRepositoryTests {

    @Override
    RepositorySettings getRepositorySettings(RepositoryType repositoryType) {
        return null
    }

    @Test(groups = "replication")
    void localRepositoryWithNoReplication_CreateReplication() {
        // === Setup === //
        deleteRepoIfExists(localRepo.getKey())
        artifactory.repositories().create(0, localRepo)

        // === Test === //

        def replications = artifactory.repository(localRepo.getKey()).replications

        def replication1 = new LocalReplicationImpl()
        replication1.url = "http://localhost:12345/artifactory/${localRepo.key}"
        replication1.socketTimeoutMillis = 30000
        replication1.username = 'john.doe'
        replication1.password = 'secret'
        replication1.enableEventReplication = false
        replication1.enabled = false
        replication1.cronExp = '0 0 0/2 * * ?'
        replication1.syncDeletes = true
        replication1.syncProperties = true
        replication1.syncStatistics = true
        replication1.pathPrefix = 'pathPrefix'
        replication1.repoKey = localRepo.key

        replications.createOrReplace(replication1)
    }

    @Test(groups = "replication")
    void localRepositoryWithNoReplication_DeleteReplications() {

        // === Setup === //
        deleteRepoIfExists(localRepo.getKey())
        artifactory.repositories().create(0, localRepo)

        // === Test === //

        def replications = artifactory.repository(localRepo.getKey()).replications

        // This call throws a groovyx.net.http.HttpResponseException with the message "Bad Request"
        try {
            replications.delete()
        } catch (Exception e) {
            assertTrue(e instanceof HttpResponseException)
            assertTrue(((HttpResponseException) e).getStatusCode() == 400)
        }
    }
}