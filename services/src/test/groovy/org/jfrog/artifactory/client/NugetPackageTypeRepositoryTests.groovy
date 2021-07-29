package org.jfrog.artifactory.client

import org.hamcrest.CoreMatchers
import org.jfrog.artifactory.client.model.RepositoryType
import org.jfrog.artifactory.client.model.repository.settings.RepositorySettings
import org.jfrog.artifactory.client.model.repository.settings.impl.NugetRepositorySettingsImpl
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

/**
 * test that client correctly sends and receives repository configuration with `nuget` package type
 *
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
class NugetPackageTypeRepositoryTests extends BaseRepositoryTests {

    NugetPackageTypeRepositoryTests() {
        remoteRepoUrl = "https://www.nuget.org"
    }

    @Override
    RepositorySettings getRepositorySettings(RepositoryType repositoryType) {
        def settings = new NugetRepositorySettingsImpl()

        settings.with {
            // local
            maxUniqueSnapshots = rnd.nextInt()
            downloadContextPath = "api/v${rnd.nextInt()}/package"
            feedContextPath = "api/v${rnd.nextInt()}"
            v3FeedUrl = "api/v${rnd.nextInt()}/v3"

            // remote
            listRemoteFolderItems = rnd.nextBoolean()

            // local + remote
            forceNugetAuthentication = rnd.nextBoolean()
        }

        return settings
    }

    @BeforeMethod
    protected void setUp() {
        super.setUp()
    }

    @Test(groups = "nugetPackageTypeRepo")
    void testNugetLocalRepo() {
        artifactory.repositories().create(0, localRepo)
        def expectedSettings = localRepo.repositorySettings

        def resp = artifactory.repository(localRepo.getKey()).get()
        assertThat(resp, CoreMatchers.notNullValue())
        assertThat(resp.repoLayoutRef, CoreMatchers.is(NugetRepositorySettingsImpl.defaultLayout))
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(expectedSettings.getPackageType()))
            assertThat(repoLayout, CoreMatchers.is(expectedSettings.getRepoLayout()))

            // local
            assertThat(maxUniqueSnapshots, CoreMatchers.is(expectedSettings.getMaxUniqueSnapshots()))
            // TODO: property is not returned by the artifactory
            // assertThat(downloadContextPath, CoreMatchers.is(specRepo.getDownloadContextPath()))
            assertThat(downloadContextPath, CoreMatchers.is(CoreMatchers.nullValue()))
            // TODO: property is not returned by the artifactory
            // assertThat(feedContextPath, CoreMatchers.is(specRepo.getFeedContextPath()))
            assertThat(feedContextPath, CoreMatchers.is(CoreMatchers.nullValue()))

            // remote
            assertThat(listRemoteFolderItems, CoreMatchers.nullValue())

            // local + remote
            assertThat(forceNugetAuthentication, CoreMatchers.is(expectedSettings.getForceNugetAuthentication()))
        }
    }

    @Test(groups = "nugetPackageTypeRepo")
    void testNugetRemoteRepo() {
        artifactory.repositories().create(0, remoteRepo)
        def expectedSettings = remoteRepo.repositorySettings

        def resp = artifactory.repository(remoteRepo.getKey()).get()
        assertThat(resp, CoreMatchers.notNullValue())
        assertThat(resp.repoLayoutRef, CoreMatchers.is(NugetRepositorySettingsImpl.defaultLayout))
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(expectedSettings.getPackageType()))
            assertThat(repoLayout, CoreMatchers.is(expectedSettings.getRepoLayout()))

            // local
            // always in resp payload
            assertThat(maxUniqueSnapshots, CoreMatchers.is(expectedSettings.getMaxUniqueSnapshots()))
            assertThat(downloadContextPath, CoreMatchers.is(expectedSettings.getDownloadContextPath()))
            assertThat(feedContextPath, CoreMatchers.is(expectedSettings.getFeedContextPath()))
            assertThat(v3FeedUrl, CoreMatchers.is(expectedSettings.getV3FeedUrl()))

            // remote
            assertThat(listRemoteFolderItems, CoreMatchers.is(expectedSettings.getListRemoteFolderItems()))

            // local + remote
            assertThat(forceNugetAuthentication, CoreMatchers.is(expectedSettings.getForceNugetAuthentication()))
        }
    }

    @Test(groups = "nugetPackageTypeRepo")
    void testNugetVirtualRepo() {
        artifactory.repositories().create(0, virtualRepo)
        def expectedSettings = virtualRepo.repositorySettings

        def resp = artifactory.repository(virtualRepo.getKey()).get()
        assertThat(resp, CoreMatchers.notNullValue())
        assertThat(resp.repoLayoutRef, CoreMatchers.is(NugetRepositorySettingsImpl.defaultLayout))
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(expectedSettings.getPackageType()))
            assertThat(repoLayout, CoreMatchers.is(expectedSettings.getRepoLayout()))

            // local
            // always in resp payload
            assertThat(maxUniqueSnapshots, CoreMatchers.is(CoreMatchers.nullValue()))
            assertThat(downloadContextPath, CoreMatchers.is(CoreMatchers.nullValue()))
            assertThat(feedContextPath, CoreMatchers.is(CoreMatchers.nullValue()))

            // remote
            assertThat(listRemoteFolderItems, CoreMatchers.nullValue())

            // local + remote
            assertThat(forceNugetAuthentication, CoreMatchers.is(expectedSettings.getForceNugetAuthentication()))
        }
    }

}
