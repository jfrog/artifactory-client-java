package org.jfrog.artifactory.client

import org.hamcrest.CoreMatchers
import org.jfrog.artifactory.client.model.*
import org.jfrog.artifactory.client.model.repository.settings.impl.PypiRepositorySettingsImpl
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

/**
 * test that client correctly sends and receives repository configuration with `pypi` package type
 * 
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
public class PypiPackageTypeRepositoryTests extends BaseRepositoryTests {

    @BeforeMethod
    protected void setUp() {
        settings = new PypiRepositorySettingsImpl()

        settings.with {
            // remote
            listRemoteFolderItems = rnd.nextBoolean()
        }

        super.setUp()
    }

    @Test(groups = "pypiPackageTypeRepo")
    public void testPypiLocalRepo() {
        artifactory.repositories().create(0, localRepo)

        def resp = artifactory.repository(localRepo.getKey()).get()
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(settings.getPackageType()))
            assertThat(listRemoteFolderItems, CoreMatchers.nullValue())
        }
    }

    @Test(groups = "pypiPackageTypeRepo")
    public void testPypiRemoteRepo() {
        artifactory.repositories().create(0, remoteRepo)

        def resp = artifactory.repository(remoteRepo.getKey()).get()
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(settings.getPackageType()))
            assertThat(listRemoteFolderItems, CoreMatchers.is(settings.getListRemoteFolderItems()))
        }
    }

    @Test(groups = "pypiPackageTypeRepo")
    public void testPypiVirtualRepo() {
        artifactory.repositories().create(0, virtualRepo)

        def resp = artifactory.repository(virtualRepo.getKey()).get()
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(settings.getPackageType()))
            assertThat(listRemoteFolderItems, CoreMatchers.nullValue())
        }
    }

}
