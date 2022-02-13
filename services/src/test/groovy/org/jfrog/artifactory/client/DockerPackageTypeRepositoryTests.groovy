package org.jfrog.artifactory.client

import org.hamcrest.CoreMatchers
import org.jfrog.artifactory.client.model.RepositoryType
import org.jfrog.artifactory.client.model.repository.settings.RepositorySettings
import org.jfrog.artifactory.client.model.repository.settings.docker.DockerApiVersion
import org.jfrog.artifactory.client.model.repository.settings.impl.DockerRepositorySettingsImpl
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

/**
 * test that client correctly sends and receives repository configuration with `docker` package type
 *
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
class DockerPackageTypeRepositoryTests extends BaseRepositoryTests {

    DockerPackageTypeRepositoryTests() {
        remoteRepoUrl = "https://registry-1.docker.io"
    }

    @Override
    RepositorySettings getRepositorySettings(RepositoryType repositoryType) {
        def settings = new DockerRepositorySettingsImpl()

        settings.with {
            // local
            dockerApiVersion = DockerApiVersion.values()[rnd.nextInt(DockerApiVersion.values().length)]

            // remote
            enableTokenAuthentication = rnd.nextBoolean()
            listRemoteFolderItems = rnd.nextBoolean()
        }

        return settings
    }

    @BeforeMethod
    protected void setUp() {
        storeArtifactsLocallyInRemoteRepo = true
        super.setUp()
    }

    @Test(groups = "dockerPackageTypeRepo")
    void testDockerLocalRepo() {
        artifactory.repositories().create(0, localRepo)
        def expectedSettings = localRepo.repositorySettings

        def resp = artifactory.repository(localRepo.getKey()).get()
        assertThat(resp, CoreMatchers.notNullValue())
        assertThat(resp.repoLayoutRef, CoreMatchers.is(DockerRepositorySettingsImpl.defaultLayout))
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(expectedSettings.getPackageType()))
            assertThat(repoLayout, CoreMatchers.is(expectedSettings.getRepoLayout()))

            // local
            assertThat(dockerApiVersion, CoreMatchers.is(expectedSettings.getDockerApiVersion()))

            // remote
            assertThat(enableTokenAuthentication, CoreMatchers.is(CoreMatchers.nullValue()))
            assertThat(listRemoteFolderItems, CoreMatchers.is(CoreMatchers.nullValue()))
        }
    }

    @Test(groups = "dockerPackageTypeRepo")
    void testDockerFederatedRepo() {
        artifactory.repositories().create(0, federatedRepo)
        def expectedSettings = federatedRepo.repositorySettings

        def resp = artifactory.repository(federatedRepo.getKey()).get()
        assertThat(resp, CoreMatchers.notNullValue())
        assertThat(resp.repoLayoutRef, CoreMatchers.is(DockerRepositorySettingsImpl.defaultLayout))
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(expectedSettings.getPackageType()))
            assertThat(repoLayout, CoreMatchers.is(expectedSettings.getRepoLayout()))

            // local
            assertThat(dockerApiVersion, CoreMatchers.is(expectedSettings.getDockerApiVersion()))

            // remote
            assertThat(enableTokenAuthentication, CoreMatchers.is(CoreMatchers.nullValue()))
            assertThat(listRemoteFolderItems, CoreMatchers.is(CoreMatchers.nullValue()))
        }
    }

    @Test(groups = "dockerPackageTypeRepo")
    void testDockerRemoteRepo() {
        artifactory.repositories().create(0, remoteRepo)
        def expectedSettings = remoteRepo.repositorySettings

        def resp = artifactory.repository(remoteRepo.getKey()).get()
        assertThat(resp, CoreMatchers.notNullValue())
        assertThat(resp.repoLayoutRef, CoreMatchers.is(DockerRepositorySettingsImpl.defaultLayout))
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(expectedSettings.getPackageType()))
            assertThat(repoLayout, CoreMatchers.is(expectedSettings.getRepoLayout()))

            // local
            assertThat(dockerApiVersion, CoreMatchers.is(expectedSettings.getDockerApiVersion()))
            // always in resp payload

            // remote
            assertThat(enableTokenAuthentication, CoreMatchers.is(expectedSettings.getEnableTokenAuthentication()))
            assertThat(listRemoteFolderItems, CoreMatchers.is(expectedSettings.getListRemoteFolderItems()))
        }
    }

    @Test(groups = "dockerPackageTypeRepo")
    void testDockerVirtualRepo() {
        artifactory.repositories().create(0, virtualRepo)
        def expectedSettings = virtualRepo.repositorySettings

        def resp = artifactory.repository(virtualRepo.getKey()).get()
        assertThat(resp, CoreMatchers.notNullValue())
        assertThat(resp.repoLayoutRef, CoreMatchers.is(DockerRepositorySettingsImpl.defaultLayout))
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(expectedSettings.getPackageType()))
            assertThat(repoLayout, CoreMatchers.is(expectedSettings.getRepoLayout()))

            // local
            assertThat(dockerApiVersion, CoreMatchers.is(expectedSettings.getDockerApiVersion()))

            // remote
            assertThat(enableTokenAuthentication, CoreMatchers.is(CoreMatchers.nullValue()))
            assertThat(listRemoteFolderItems, CoreMatchers.is(CoreMatchers.nullValue()))
        }
    }
}
