package org.jfrog.artifactory.client

import org.hamcrest.CoreMatchers
import org.jfrog.artifactory.client.model.repository.settings.impl.PuppetRepositorySettingsImpl
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

/**
 * Test that client correctly sends and receives repository configuration with `puppet` package type
 */
public class PuppetPackageTypeRepositoryTests extends BaseRepositoryTests {

    @BeforeMethod
    protected void setUp() {
        settings = new PuppetRepositorySettingsImpl()
        super.setUp()
    }

    @Test(groups = "puppetPackageTypeRepo")
    public void testPuppetLocalRepo() {
        artifactory.repositories().create(0, localRepo)

        def resp = artifactory.repository(localRepo.getKey()).get()
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(settings.getPackageType()))
        }
    }

    @Test(groups = "puppetPackageTypeRepo")
    public void testPuppetRemoteRepo() {
        artifactory.repositories().create(0, remoteRepo)

        def resp = artifactory.repository(remoteRepo.getKey()).get()
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(settings.getPackageType()))
        }
    }

    @Test(groups = "puppetPackageTypeRepo")
    public void testPuppetVirtualRepo() {
        artifactory.repositories().create(0, virtualRepo)

        def resp = artifactory.repository(virtualRepo.getKey()).get()
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(settings.getPackageType()))
        }
    }
}
