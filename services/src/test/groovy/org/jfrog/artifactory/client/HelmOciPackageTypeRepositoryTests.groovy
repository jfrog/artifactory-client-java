package org.jfrog.artifactory.client

import org.hamcrest.CoreMatchers
import org.jfrog.artifactory.client.model.RepositoryType
import org.jfrog.artifactory.client.model.repository.settings.RepositorySettings
import org.jfrog.artifactory.client.model.repository.settings.docker.DockerApiVersion
import org.jfrog.artifactory.client.model.repository.settings.impl.HelmOciRepositorySettingsImpl
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

/**
 * test that client correctly sends and receives repository configuration with `helmoci` package type
 *
 */
class HelmOciPackageTypeRepositoryTests extends BaseRepositoryTests {

    HelmOciPackageTypeRepositoryTests() {
        remoteRepoUrl = "https://registry-1.docker.io"
    }

    @Override
    RepositorySettings getRepositorySettings(RepositoryType repositoryType) {
        def settings = new HelmOciRepositorySettingsImpl()

        settings.with {
            // local
            dockerApiVersion = DockerApiVersion.V2
            dockerTagRetention = Math.abs(rnd.nextInt())

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

    @Test(groups = "helmOciPackageTypeRepo")
    void testOciLocalRepo() {
        artifactory.repositories().create(0, localRepo)
        def expectedSettings = localRepo.repositorySettings

        def resp = artifactory.repository(localRepo.getKey()).get()
        assertThat(resp, CoreMatchers.notNullValue())
        assertThat(resp.repoLayoutRef, CoreMatchers.is(HelmOciRepositorySettingsImpl.defaultLayout))
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(expectedSettings.getPackageType()))
            assertThat(repoLayout, CoreMatchers.is(expectedSettings.getRepoLayout()))

            // local
            assertThat(dockerApiVersion, CoreMatchers.is(expectedSettings.getDockerApiVersion()))
            assertThat(dockerTagRetention, CoreMatchers.is(expectedSettings.getDockerTagRetention()))

            // remote
            assertThat(enableTokenAuthentication, CoreMatchers.is(CoreMatchers.nullValue()))
            assertThat(listRemoteFolderItems, CoreMatchers.is(CoreMatchers.nullValue()))
        }
    }

    @Test(groups = "helmOciPackageTypeRepo")
    void testOciFederatedRepo() {
        artifactory.repositories().create(0, federatedRepo)
        def expectedSettings = federatedRepo.repositorySettings

        def resp = artifactory.repository(federatedRepo.getKey()).get()
        assertThat(resp, CoreMatchers.notNullValue())
        assertThat(resp.repoLayoutRef, CoreMatchers.is(HelmOciRepositorySettingsImpl.defaultLayout))
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

    @Test(groups = "helmOciPackageTypeRepo")
    void testOciRemoteRepo() {
        artifactory.repositories().create(0, remoteRepo)
        def expectedSettings = remoteRepo.repositorySettings

        def resp = artifactory.repository(remoteRepo.getKey()).get()
        assertThat(resp, CoreMatchers.notNullValue())
        assertThat(resp.repoLayoutRef, CoreMatchers.is(HelmOciRepositorySettingsImpl.defaultLayout))
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

    @Test(groups = "helmOciPackageTypeRepo")
    void testDockerVirtualRepo() {
        artifactory.repositories().create(0, virtualRepo)
        def expectedSettings = virtualRepo.repositorySettings

        def resp = artifactory.repository(virtualRepo.getKey()).get()
        assertThat(resp, CoreMatchers.notNullValue())
        assertThat(resp.repoLayoutRef, CoreMatchers.is(HelmOciRepositorySettingsImpl.defaultLayout))
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
