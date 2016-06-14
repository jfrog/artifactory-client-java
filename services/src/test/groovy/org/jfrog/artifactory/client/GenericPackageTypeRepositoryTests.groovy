package org.jfrog.artifactory.client

import org.hamcrest.CoreMatchers
import org.jfrog.artifactory.client.model.*
import org.jfrog.artifactory.client.model.repository.settings.impl.GenericRepositorySettingsImpl
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

/**
 * test that client correctly sends and receives repository configuration with `generic` package type
 * 
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
public class GenericPackageTypeRepositoryTests extends BaseRepositoryTests {

    @BeforeMethod
    protected void setUp() {
        settings = new GenericRepositorySettingsImpl()

        settings.with {
            // remote
            listRemoteFolderItems = rnd.nextBoolean()
        }

        super.setUp()
    }

    @Test(groups = "genericPackageTypeRepo")
    public void testGenericLocalRepo() {
        artifactory.repositories().create(0, localRepo)

        def resp = artifactory.repository(localRepo.getKey()).get()
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(settings.getPackageType()))
            assertThat(listRemoteFolderItems, CoreMatchers.nullValue())
        }
    }

    @Test(groups = "genericPackageTypeRepo")
    public void testGenericRemoteRepo() {
        artifactory.repositories().create(0, remoteRepo)

        def resp = artifactory.repository(remoteRepo.getKey()).get()
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(settings.getPackageType()))
            assertThat(listRemoteFolderItems, CoreMatchers.is(settings.getListRemoteFolderItems()))
        }
    }

    @Test(groups = "genericPackageTypeRepo")
    public void testGenericVirtualRepo() {
        artifactory.repositories().create(0, virtualRepo)

        def resp = artifactory.repository(virtualRepo.getKey()).get()
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(settings.getPackageType()))
            assertThat(listRemoteFolderItems, CoreMatchers.nullValue())
        }
    }

}
