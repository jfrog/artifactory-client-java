package org.jfrog.artifactory.client

import org.hamcrest.CoreMatchers
import org.jfrog.artifactory.client.model.repository.settings.impl.ChefRepositorySettingsImpl
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

/**
 * Test that client correctly sends and receives repository configuration with `chef` package type
 */
public class ChefPackageTypeRepositoryTests extends BaseRepositoryTests {

    @BeforeMethod
    protected void setUp() {
        settings = new ChefRepositorySettingsImpl()
        super.setUp()
    }

    @Test(groups = "chefPackageTypeRepo")
    public void testChefLocalRepo() {
        artifactory.repositories().create(0, localRepo)

        def resp = artifactory.repository(localRepo.getKey()).get()
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(settings.getPackageType()))
        }
    }

    @Test(groups = "chefPackageTypeRepo")
    public void testChefRemoteRepo() {
        artifactory.repositories().create(0, remoteRepo)

        def resp = artifactory.repository(remoteRepo.getKey()).get()
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(settings.getPackageType()))
        }
    }

    @Test(groups = "chefPackageTypeRepo")
    public void testChefVirtualRepo() {
        artifactory.repositories().create(0, virtualRepo)

        def resp = artifactory.repository(virtualRepo.getKey()).get()
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(settings.getPackageType()))
        }
    }
}
