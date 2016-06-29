package org.jfrog.artifactory.client

import org.hamcrest.CoreMatchers
import org.jfrog.artifactory.client.model.*
import org.jfrog.artifactory.client.model.repository.settings.impl.VagrantRepositorySettingsImpl
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

/**
 * test that client correctly sends and receives repository configuration with `vagrant` package type
 * 
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
public class VagrantPackageTypeRepositoryTests extends BaseRepositoryTests {

    @BeforeMethod
    protected void setUp() {
        settings = new VagrantRepositorySettingsImpl()

        settings.with {
        }

        // only local repository supported
        prepareRemoteRepo = false
        prepareVirtualRepo = false

        super.setUp()
    }

    @Test(groups = "vagrantPackageTypeRepo")
    public void testVagrantLocalRepo() {
        artifactory.repositories().create(0, localRepo)

        def resp = artifactory.repository(localRepo.getKey()).get()
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(settings.getPackageType()))
        }
    }

}
