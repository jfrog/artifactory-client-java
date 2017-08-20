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
public class DockerPackageTypeRepositoryTests extends BaseRepositoryTests {

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
        super.setUp()
    }

    @Test(groups = "dockerPackageTypeRepo")
    public void testDockerLocalRepo() {
        artifactory.repositories().create(0, localRepo)
        def expectedSettings = localRepo.repositorySettings

        def resp = artifactory.repository(localRepo.getKey()).get()
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(expectedSettings.getPackageType()))

            // local
            assertThat(dockerApiVersion, CoreMatchers.is(expectedSettings.getDockerApiVersion()))

            // remote
            assertThat(enableTokenAuthentication, CoreMatchers.is(CoreMatchers.nullValue()))
            assertThat(listRemoteFolderItems, CoreMatchers.is(CoreMatchers.nullValue()))
        }
    }

    @Test(groups = "dockerPackageTypeRepo")
    public void testDockerRemoteRepo() {
        artifactory.repositories().create(0, remoteRepo)
        def expectedSettings = remoteRepo.repositorySettings

        def resp = artifactory.repository(remoteRepo.getKey()).get()
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(expectedSettings.getPackageType()))

            // local
            assertThat(dockerApiVersion, CoreMatchers.is(expectedSettings.getDockerApiVersion())) // always in resp payload

            // remote
            assertThat(enableTokenAuthentication, CoreMatchers.is(expectedSettings.getEnableTokenAuthentication()))
            assertThat(listRemoteFolderItems, CoreMatchers.is(expectedSettings.getListRemoteFolderItems()))
        }
    }

    @Test(groups = "dockerPackageTypeRepo")
    public void testDockerVirtualRepo() {
        artifactory.repositories().create(0, virtualRepo)
        def expectedSettings = virtualRepo.repositorySettings

        def resp = artifactory.repository(virtualRepo.getKey()).get()
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(expectedSettings.getPackageType()))

            // local
            assertThat(dockerApiVersion, CoreMatchers.is(expectedSettings.getDockerApiVersion()))

            // remote
            assertThat(enableTokenAuthentication, CoreMatchers.is(CoreMatchers.nullValue()))
            assertThat(listRemoteFolderItems, CoreMatchers.is(CoreMatchers.nullValue()))
        }
    }
}
