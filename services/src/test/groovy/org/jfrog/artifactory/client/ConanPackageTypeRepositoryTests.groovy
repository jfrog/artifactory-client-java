package org.jfrog.artifactory.client

import org.hamcrest.CoreMatchers
import org.jfrog.artifactory.client.model.repository.settings.impl.ConanRepositorySettingsImpl
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

/**
 * Test that client correctly sends and receives repository configuration with `conan` package type
 */
public class ConanPackageTypeRepositoryTests extends BaseRepositoryTests {
    @BeforeMethod
    protected void setUp() {
        settings = new ConanRepositorySettingsImpl()
        super.setUp()
    }

    @Test(groups = "conanPackageTypeRepo")
    public void testConanLocalRepo() {
        artifactory.repositories().create(0, localRepo)

        def resp = artifactory.repository(localRepo.getKey()).get()
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(settings.getPackageType()))
        }
    }

    @Test(groups = "conanPackageTypeRepo")
    public void testConanRemoteRepo() {
        artifactory.repositories().create(0, remoteRepo)

        def resp = artifactory.repository(remoteRepo.getKey()).get()
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(settings.getPackageType()))
        }
    }

    @Test(groups = "conanPackageTypeRepo")
    public void testConanVirtualRepo() {
        artifactory.repositories().create(0, virtualRepo)

        def resp = artifactory.repository(virtualRepo.getKey()).get()
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(settings.getPackageType()))
        }
    }
}
