package org.jfrog.artifactory.client

import groovyx.net.http.HttpResponseException
import org.jfrog.artifactory.client.model.impl.LocalReplicationImpl
import org.jfrog.artifactory.client.model.impl.RemoteReplicationImpl
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

import static org.testng.Assert.*

public class ReplicationTests extends BaseRepositoryTests {

    @BeforeMethod
    protected void setUp() {
        super.setUp()
    }

    // ======================== //
    // === Local repository === //
    // ======================== //

    @Test(groups = "replication")
    void localRepositoryWithNoReplication_ListReplications() {

        // === Setup === //

        artifactory.repositories().create(0, localRepo)

        // === Test === //

        def replications = artifactory.repository(localRepo.getKey()).replications

        def result = replications.list()

        assertNotNull(result)
        assertEquals(result.size(), 0)
    }

    @Test(groups = "replication")
    void localRepositoryWithNoReplication_CreateOneReplication() {

        // === Setup === //

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
        replication1.repoKey = localRepo.key

        replications.createOrReplace(replication1)

        def result = replications.list()

        assertNotNull(result)
        assertEquals(result.size(), 1)
        assertEquals(result[0], replication1)
    }

    @Test(groups = "replication")
    void localRepositoryWithNoReplication_CreateTwoReplications() {

        // === Setup === //

        artifactory.repositories().create(0, localRepo)

        // === Test === //

        def replications = artifactory.repository(localRepo.getKey()).replications

        def replication1 = new LocalReplicationImpl()
        replication1.url = "http://localhost:12345/artifactory/${localRepo.key}"
        replication1.socketTimeoutMillis = 30000
        replication1.username = 'john.doe'
        replication1.password = 'secret1'
        replication1.enableEventReplication = false
        replication1.enabled = false
        replication1.cronExp = '0 0 0/2 * * ?'
        replication1.syncDeletes = false
        replication1.syncProperties = false
        replication1.syncStatistics = false
        replication1.repoKey = localRepo.key

        def replication2 = new LocalReplicationImpl()
        replication2.url = "http://localhost:54321/artifactory/${localRepo.key}"
        replication2.socketTimeoutMillis = 60000
        replication2.username = 'jane.doe'
        replication2.password = 'secret2'
        replication2.enableEventReplication = true
        replication2.enabled = true
        replication2.cronExp = '0 0 0/4 * * ?'
        replication2.syncDeletes = true
        replication2.syncProperties = true
        replication2.syncStatistics = true
        replication2.repoKey = localRepo.key

        replications.createOrReplace([replication1, replication2 ])

        def result = replications.list()

        assertNotNull(result)
        assertEquals(result.size(), 2)

        // The first replication returns unaltered
        assertTrue(result.contains(replication1))

        // The second replication has some of its properties (the shared ones) overwritten by the first one !
        def expected = new LocalReplicationImpl()
        expected.url = replication2.url
        expected.socketTimeoutMillis = replication2.socketTimeoutMillis
        expected.username = replication2.username
        expected.password = replication2.password
        expected.enableEventReplication = false // <-- Overwritten !!
        expected.enabled = replication2.enabled
        expected.cronExp = '0 0 0/2 * * ?'      // <-- Overwritten !!
        expected.syncDeletes = replication2.syncDeletes
        expected.syncProperties = replication2.syncProperties
        expected.syncStatistics = replication2.syncStatistics
        expected.repoKey = replication2.repoKey

        assertTrue(result.contains(expected))
    }

    @Test(groups = "replication", expectedExceptions = [ HttpResponseException ], expectedExceptionsMessageRegExp = "Bad Request")
    void localRepositoryWithNoReplication_DeleteReplications() {

        // === Setup === //

        artifactory.repositories().create(0, localRepo)

        // === Test === //

        def replications = artifactory.repository(localRepo.getKey()).replications

        // This call throws a groovyx.net.http.HttpResponseException with the message "Bad Request"
        replications.delete()
    }

    @Test(groups = "replication")
    void localRepositoryWithOneReplication_DeleteReplications() {

        // === Setup === //

        artifactory.repositories().create(0, localRepo)

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
        replication1.repoKey = localRepo.key

        replications.createOrReplace(replication1)

        def result = replications.list()

        assertNotNull(result)
        assertEquals(result.size(), 1)

        // === Test === //

        replications.delete()

        def result2 = replications.list()

        assertNotNull(result2)
        assertEquals(result2.size(), 0)
    }

    @Test(groups = "replication")
    void localRepositoryWithOneReplication_ReplaceReplication() {

        // === Setup === //

        artifactory.repositories().create(0, localRepo)

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
        replication1.repoKey = localRepo.key

        replications.createOrReplace(replication1)

        def result = replications.list()

        assertNotNull(result)
        assertEquals(result.size(), 1)
        assertEquals(result[0], replication1)

        // === Test === //

        // Alter some properties from the replication
        replication1.enabled = !replication1.enabled
        replication1.syncDeletes = !replication1.syncDeletes

        replications.createOrReplace(replication1)

        def result2 = replications.list()

        assertNotNull(result2)
        assertEquals(result2.size(), 1)
        assertEquals(result2[0], replication1)
    }

    @Test(groups = "replication")
    void localRepositoryWithTwoReplications_DeleteReplications() {

        // === Setup === //

        artifactory.repositories().create(0, localRepo)

        def replications = artifactory.repository(localRepo.getKey()).replications

        def replication1 = new LocalReplicationImpl()
        replication1.url = "http://localhost:12345/artifactory/${localRepo.key}"
        replication1.socketTimeoutMillis = 30000
        replication1.username = 'john.doe'
        replication1.password = 'secret1'
        replication1.enableEventReplication = false
        replication1.enabled = false
        replication1.cronExp = '0 0 0/2 * * ?'
        replication1.syncDeletes = false
        replication1.syncProperties = false
        replication1.syncStatistics = false
        replication1.repoKey = localRepo.key

        def replication2 = new LocalReplicationImpl()
        replication2.url = "http://localhost:54321/artifactory/${localRepo.key}"
        replication2.socketTimeoutMillis = 60000
        replication2.username = 'jane.doe'
        replication2.password = 'secret2'
        replication2.enableEventReplication = true
        replication2.enabled = true
        replication2.cronExp = '0 0 0/4 * * ?'
        replication2.syncDeletes = true
        replication2.syncProperties = true
        replication2.syncStatistics = true
        replication2.repoKey = localRepo.key

        replications.createOrReplace([replication1, replication2])

        def result = replications.list()

        assertNotNull(result)
        assertEquals(result.size(), 2)

        // === Test === //

        replications.delete()

        def result2 = replications.list()

        assertNotNull(result2)
        assertEquals(result2.size(), 0)
    }

    // ========================= //
    // === Remote repository === //
    // ========================= //

    @Test(groups = "replication")
    void remoteRepositoryWithNoReplication_ListReplications() {

        // === Setup === //

        artifactory.repositories().create(0, remoteRepo)

        // === Test === //

        def replications = artifactory.repository(remoteRepo.getKey()).replications

        def result = replications.list()

        assertNotNull(result)
        assertEquals(result.size(), 0)
    }

    @Test(groups = "replication")
    void remoteRepositoryWithNoReplication_CreateOneReplication() {

        // === Setup === //

        artifactory.repositories().create(0, remoteRepo)

        // === Test === //

        def replications = artifactory.repository(remoteRepo.getKey()).replications

        def replication1 = new RemoteReplicationImpl()
        replication1.enabled = false
        replication1.cronExp = '0 0 0/2 * * ?'
        replication1.syncDeletes = true
        replication1.syncProperties = true
        replication1.repoKey = remoteRepo.key

        replications.createOrReplace(replication1)

        def result = replications.list()

        assertNotNull(result)
        assertEquals(result.size(), 1)
        assertEquals(result[0], replication1)
    }

    @Test(groups = "replication", expectedExceptions = [ HttpResponseException ], expectedExceptionsMessageRegExp = "Bad Request")
    void remoteRepositoryWithNoReplication_DeleteReplications() {

        // === Setup === //

        artifactory.repositories().create(0, remoteRepo)

        // === Test === //

        def replications = artifactory.repository(remoteRepo.getKey()).replications

        // This call throws a groovyx.net.http.HttpResponseException with the message "Bad Request"
        replications.delete()
    }

    @Test(groups = "replication")
    void remoteRepositoryWithOneReplication_DeleteReplications() {

        // === Setup === //

        artifactory.repositories().create(0, remoteRepo)

        def replications = artifactory.repository(remoteRepo.getKey()).replications

        def replication1 = new RemoteReplicationImpl()
        replication1.enabled = false
        replication1.cronExp = '0 0 0/2 * * ?'
        replication1.syncDeletes = true
        replication1.syncProperties = true
        replication1.repoKey = remoteRepo.key

        replications.createOrReplace(replication1)

        def result = replications.list()

        assertNotNull(result)
        assertEquals(result.size(), 1)

        // === Test === //

        replications.delete()

        def result2 = replications.list()

        assertNotNull(result2)
        assertEquals(result2.size(), 0)
    }

    @Test(groups = "replication")
    void remoteRepositoryWithOneReplication_ReplaceReplication() {

        // === Setup === //

        artifactory.repositories().create(0, remoteRepo)

        def replications = artifactory.repository(remoteRepo.getKey()).replications

        def replication1 = new RemoteReplicationImpl()
        replication1.enabled = false
        replication1.cronExp = '0 0 0/2 * * ?'
        replication1.syncDeletes = true
        replication1.syncProperties = true
        replication1.repoKey = remoteRepo.key

        replications.createOrReplace(replication1)

        def result = replications.list()

        assertNotNull(result)
        assertEquals(result.size(), 1)
        assertEquals(result[0], replication1)

        // === Test === //

        // Alter some properties from the replication
        replication1.enabled = !replication1.enabled
        replication1.syncDeletes = !replication1.syncDeletes

        replications.createOrReplace(replication1)

        def result2 = replications.list()

        assertNotNull(result2)
        assertEquals(result2.size(), 1)
        assertEquals(result2[0], replication1)
    }
}