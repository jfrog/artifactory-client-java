package org.jfrog.artifactory.client

import org.apache.commons.codec.digest.DigestUtils
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager
import org.jfrog.artifactory.client.model.Repository
import org.jfrog.artifactory.client.model.repository.settings.RepositorySettings
import org.jfrog.artifactory.client.model.repository.settings.impl.GenericRepositorySettingsImpl
import org.testng.Assert
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * test that client supports Async rest calls
 *
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
public class AsyncTests {
    Artifactory artifactory

    @Test
    public void testChecksumDeployAsync() {
        init()
        def repoName = 'testrepo'
        createRepo(repoName)
        def stream = this.class.getResourceAsStream("/sample.zip")
        def sha1 = DigestUtils.sha1(stream.bytes).encodeHex().toString()
        def futures = []
        ExecutorService executor = Executors.newFixedThreadPool(30)
        for (int i = 0; i < 1000; i++) {
            futures.add(executor.submit(new ArtifactUploadRunnable(sha1, i)))
        }
        sleep(3000)
        def exceptions = futures.collect { it.get() }.findAll { it != null }
        Assert.assertEquals(exceptions.empty, true)
        artifactory.repository(repoName).delete()
    }

    @Test
    public void testUploadAsync() {
        init()
        def repoName = 'testrepo'
        createRepo(repoName)
        def stream = this.class.getResourceAsStream("/sample.zip")
        def sha1 = DigestUtils.sha1(stream.bytes).encodeHex().toString()
        def futures = []
        ExecutorService executor = Executors.newFixedThreadPool(30)
        for (int i = 0; i < 1000; i++) {
            futures.add(executor.submit(new ArtifactUploadRunnable(i)))
        }
        sleep(3000)
        def exceptions = futures.collect { it.get() }.findAll { it != null }
        Assert.assertEquals(exceptions.empty, true)
        artifactory.repository(repoName).delete()
    }

    @BeforeMethod
    void init() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager()
        connectionManager.setDefaultMaxPerRoute(20)
        connectionManager.setMaxTotal(20)

        ArtifactoryClientBuilder artifactoryClientBuilder = ArtifactoryClientBuilder.create()
        artifactoryClientBuilder
                .setUrl("http://ci1:7071/artifactory")
                .setConnectionManager(connectionManager)

                .setUsername('admin')
                .setPassword('password')

        artifactory = artifactoryClientBuilder.build()

    }

    public class ArtifactUploadRunnable implements Callable<Exception> {
        InputStream stream
        String sha1
        int i

        ArtifactUploadRunnable(int i) {
            //just upload
            this.i = i
        }

        ArtifactUploadRunnable(String sha1, int i) {
            //checksum upload
            this.sha1 = sha1
            this.i = i
        }

        @Override
        Exception call() throws Exception {
            try {
                stream = this.class.getResourceAsStream("/sample.zip")
                def targetPath = 'a/b' + i
                UploadableArtifact uploadableArtifact = artifactory.repository("testrepo")
                        .upload(targetPath, stream)
                        .withProperty("propA", 'x')
                        .withProperty("propB", 'y')


                uploadableArtifact.bySha1Checksum(sha1)

                uploadableArtifact.doUpload()
                return null
            } catch (Exception e) {
                return e
            }
        }
    }

    def createRepo(String name) {
        RepositorySettings genericRepo = new GenericRepositorySettingsImpl();

        Repository repository = artifactory.repositories().builders().localRepositoryBuilder()
                .key(name)
                .repositorySettings(genericRepo)
                .build()
        artifactory.repositories().create(0, repository)
    }
}




