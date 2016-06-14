package org.jfrog.artifactory.client

import org.hamcrest.CoreMatchers
import org.jfrog.artifactory.client.model.*
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

    @BeforeMethod
    protected void setUp() {
        settings = new DockerRepositorySettingsImpl()

        settings.with {
            // local
            dockerApiVersion = DockerApiVersion.values()[rnd.nextInt(DockerApiVersion.values().length)]

            // remote
            enableTokenAuthentication = rnd.nextBoolean()
            listRemoteFolderItems = rnd.nextBoolean()

            // local, remote, virtual
            forceDockerAuthentication = rnd.nextBoolean()
        }

        super.setUp()
    }

    @Test(groups = "dockerPackageTypeRepo")
    public void testDockerLocalRepo() {
        artifactory.repositories().create(0, localRepo)

        def resp = artifactory.repository(localRepo.getKey()).get()
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(settings.getPackageType()))

            // local
            assertThat(dockerApiVersion, CoreMatchers.is(settings.getDockerApiVersion()))

            // remote
            assertThat(enableTokenAuthentication, CoreMatchers.is(CoreMatchers.nullValue()))
            assertThat(listRemoteFolderItems, CoreMatchers.is(CoreMatchers.nullValue()))

            // local, remote, virtual
            assertThat(forceDockerAuthentication, CoreMatchers.is(settings.getForceDockerAuthentication()))
        }
    }

    @Test(groups = "dockerPackageTypeRepo")
    public void testDockerRemoteRepo() {
        artifactory.repositories().create(0, remoteRepo)

        def resp = artifactory.repository(remoteRepo.getKey()).get()
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(settings.getPackageType()))

            // local
            assertThat(dockerApiVersion, CoreMatchers.is(settings.getDockerApiVersion())) // always in resp payload

            // remote
            assertThat(enableTokenAuthentication, CoreMatchers.is(settings.getEnableTokenAuthentication()))
            assertThat(listRemoteFolderItems, CoreMatchers.is(settings.getListRemoteFolderItems()))

            // local, remote, virtual
            assertThat(forceDockerAuthentication, CoreMatchers.is(settings.getForceDockerAuthentication()))
        }
    }


    @Test(groups = "dockerPackageTypeRepo")
    public void testDockerVirtualRepo() {
        artifactory.repositories().create(0, virtualRepo)

        def resp = artifactory.repository(virtualRepo.getKey()).get()
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(settings.getPackageType()))

            // local
            assertThat(dockerApiVersion, CoreMatchers.is(settings.getDockerApiVersion()))

            // remote
            assertThat(enableTokenAuthentication, CoreMatchers.is(CoreMatchers.nullValue()))
            assertThat(listRemoteFolderItems, CoreMatchers.is(CoreMatchers.nullValue()))

            // local, remote, virtual
            assertThat(forceDockerAuthentication, CoreMatchers.is(settings.getForceDockerAuthentication()))
        }
    }

}
