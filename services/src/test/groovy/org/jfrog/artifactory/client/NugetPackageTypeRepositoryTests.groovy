package org.jfrog.artifactory.client

import org.hamcrest.CoreMatchers
import org.jfrog.artifactory.client.model.*
import org.jfrog.artifactory.client.model.repository.settings.impl.NugetRepositorySettingsImpl
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

/**
 * test that client correctly sends and receives repository configuration with `nuget` package type
 * 
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
public class NugetPackageTypeRepositoryTests extends BaseRepositoryTests {

    @BeforeMethod
    protected void setUp() {
        settings = new NugetRepositorySettingsImpl()

        settings.with {
            // local
            maxUniqueSnapshots = rnd.nextInt()
            downloadContextPath = "api/v${rnd.nextInt()}/package"
            feedContextPath = "api/v${rnd.nextInt()}"

            // remote
            listRemoteFolderItems = rnd.nextBoolean()

            // local + remote
            forceNugetAuthentication = rnd.nextBoolean()
        }

        super.setUp()
    }

    @Test(groups = "nugetPackageTypeRepo")
    public void testNugetLocalRepo() {
        artifactory.repositories().create(0, localRepo)

        def resp = artifactory.repository(localRepo.getKey()).get()
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(settings.getPackageType()))

            // local
            assertThat(maxUniqueSnapshots, CoreMatchers.is(settings.getMaxUniqueSnapshots()))
            // TODO: property is not returned by the artifactory
            // assertThat(downloadContextPath, CoreMatchers.is(specRepo.getDownloadContextPath()))
            assertThat(downloadContextPath, CoreMatchers.is(CoreMatchers.nullValue()))
            // TODO: property is not returned by the artifactory
            // assertThat(feedContextPath, CoreMatchers.is(specRepo.getFeedContextPath()))
            assertThat(feedContextPath, CoreMatchers.is(CoreMatchers.nullValue()))

            // remote
            assertThat(listRemoteFolderItems, CoreMatchers.nullValue())

            // local + remote
            assertThat(forceNugetAuthentication, CoreMatchers.is(settings.getForceNugetAuthentication()))
        }
    }

    @Test(groups = "nugetPackageTypeRepo")
    public void testNugetRemoteRepo() {
        artifactory.repositories().create(0, remoteRepo)

        def resp = artifactory.repository(remoteRepo.getKey()).get()
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(settings.getPackageType()))

            // local
            assertThat(maxUniqueSnapshots, CoreMatchers.is(settings.getMaxUniqueSnapshots())) // always in resp payload
            assertThat(downloadContextPath, CoreMatchers.is(CoreMatchers.nullValue()))
            assertThat(feedContextPath, CoreMatchers.is(CoreMatchers.nullValue()))

            // remote
            assertThat(listRemoteFolderItems, CoreMatchers.is(settings.getListRemoteFolderItems()))

            // local + remote
            assertThat(forceNugetAuthentication, CoreMatchers.is(settings.getForceNugetAuthentication()))
        }
    }

    @Test(groups = "nugetPackageTypeRepo")
    public void testNugetVirtualRepo() {
        artifactory.repositories().create(0, virtualRepo)

        def resp = artifactory.repository(virtualRepo.getKey()).get()
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(settings.getPackageType()))

            // local
            assertThat(maxUniqueSnapshots, CoreMatchers.is(CoreMatchers.nullValue())) // always in resp payload
            assertThat(downloadContextPath, CoreMatchers.is(CoreMatchers.nullValue()))
            assertThat(feedContextPath, CoreMatchers.is(CoreMatchers.nullValue()))


            // remote
            assertThat(listRemoteFolderItems, CoreMatchers.nullValue())

            // local + remote
            assertThat(forceNugetAuthentication, CoreMatchers.is(settings.getForceNugetAuthentication())) // always in resp payload
        }
    }

}
