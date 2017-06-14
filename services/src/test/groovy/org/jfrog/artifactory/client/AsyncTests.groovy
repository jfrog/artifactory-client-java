package org.jfrog.artifactory.client

import org.apache.commons.codec.digest.DigestUtils
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager
import org.jfrog.artifactory.client.model.Repository
import org.jfrog.artifactory.client.model.repository.settings.impl.GenericRepositorySettingsImpl
import org.testng.Assert
import org.testng.annotations.AfterClass
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * test that client supports Async rest calls
 *
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
public class AsyncTests {
    private static final String REPO_NAME = "java-client-AsyncTests"
    Artifactory artifactory

    @Test
    public void testChecksumDeployAsync() {
        createRepoIfNotExists(REPO_NAME)
        def stream = this.class.getResourceAsStream("/sample.zip")
        def sha1 = DigestUtils.sha1(stream.bytes).encodeHex().toString()
        def futures = []
        ExecutorService executor = Executors.newFixedThreadPool(30)
        for (int i = 0; i < 1000; i++) {
            futures.add(executor.submit(new ArtifactUploadRunnable(sha1, i)))
        }
        executor.awaitTermination(3000, TimeUnit.MILLISECONDS)
        def exceptions = futures.collect { it.get() }.findAll { it != null }
        Assert.assertEquals(exceptions.empty, true)
        artifactory.repository(REPO_NAME).delete()
    }

    @Test(dependsOnMethods = "testChecksumDeployAsync")
    public void testUploadAsync() {
        createRepoIfNotExists(REPO_NAME)
        def futures = []
        ExecutorService executor = Executors.newFixedThreadPool(30)
        for (int i = 0; i < 1000; i++) {
            futures.add(executor.submit(new ArtifactUploadRunnable(i)))
        }
        executor.awaitTermination(3000, TimeUnit.MILLISECONDS)
        def exceptions = futures.collect { it.get() }.findAll { it != null }
        Assert.assertEquals(exceptions.empty, true)
    }

    @BeforeMethod
    void init() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager()
        connectionManager.setDefaultMaxPerRoute(20)
        connectionManager.setMaxTotal(20)

        Properties props = new Properties();
        // This file is not in GitHub. Create your own in src/test/resources.
        InputStream inputStream = this.getClass().getResourceAsStream("/artifactory-client.properties")
        if (inputStream != null) {
            props.load(inputStream)
        }

        String url = ArtifactoryTestsBase.readParam(props, "url")
        if (!url.endsWith("/")) {
            url += "/"
        }

        ArtifactoryClientBuilder artifactoryClientBuilder = ArtifactoryClientBuilder.create()
        artifactoryClientBuilder
                .setUrl(url)
                .setConnectionManager(connectionManager)
                .setUsername('admin')
                .setPassword('password')

        artifactory = artifactoryClientBuilder.build()
    }

    @AfterClass
    public void clean() throws IOException {
        if (artifactory.repository(REPO_NAME).exists()) {
            artifactory.repository(REPO_NAME).delete()
        }
        artifactory.close()
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
                UploadableArtifact uploadableArtifact = artifactory.repository(REPO_NAME)
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

    def createRepoIfNotExists(String name) {
        if (!artifactory.repository(REPO_NAME).exists()) {
            Repository repository = artifactory.repositories().builders().localRepositoryBuilder()
                    .key(name)
                    .repositorySettings(new GenericRepositorySettingsImpl())
                    .build()
            artifactory.repositories().create(0, repository)
        }
    }
}




