package org.jfrog.artifactory.client

import org.hamcrest.CoreMatchers
import org.jfrog.artifactory.client.model.RepositoryType
import org.jfrog.artifactory.client.model.repository.settings.RepositorySettings
import org.jfrog.artifactory.client.model.repository.settings.impl.OpkgRepositorySettingsImpl
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

/**
 * test that client correctly sends and receives repository configuration with `opkg` package type
 *
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
class OpkgPackageTypeRepositoryTests extends BaseRepositoryTests {

    OpkgPackageTypeRepositoryTests() {
        remoteRepoUrl = "https://www.nuget.org"
    }

    @Override
    RepositorySettings getRepositorySettings(RepositoryType repositoryType) {
        def settings = new OpkgRepositorySettingsImpl()

        settings.with {
            // remote
            listRemoteFolderItems = rnd.nextBoolean()
        }

        return settings
    }

    @BeforeMethod
    protected void setUp() {
        // only local and remote repository supported
        prepareVirtualRepo = false

        super.setUp()
    }

    @Test(groups = "opkgPackageTypeRepo")
    void testOpkgLocalRepo() {
        artifactory.repositories().create(0, localRepo)
        def expectedSettings = localRepo.repositorySettings

        def resp = artifactory.repository(localRepo.getKey()).get()
        assertThat(resp, CoreMatchers.notNullValue())
        assertThat(resp.repoLayoutRef, CoreMatchers.is(OpkgRepositorySettingsImpl.defaultLayout))
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(expectedSettings.getPackageType()))
            assertThat(repoLayout, CoreMatchers.is(expectedSettings.getRepoLayout()))
            assertThat(listRemoteFolderItems, CoreMatchers.nullValue())
        }
    }

    @Test(groups = "opkgPackageTypeRepo")
    void testOpkgRemoteRepo() {
        artifactory.repositories().create(0, remoteRepo)
        def expectedSettings = remoteRepo.repositorySettings

        def resp = artifactory.repository(remoteRepo.getKey()).get()
        assertThat(resp, CoreMatchers.notNullValue())
        assertThat(resp.repoLayoutRef, CoreMatchers.is(OpkgRepositorySettingsImpl.defaultLayout))
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(expectedSettings.getPackageType()))
            assertThat(repoLayout, CoreMatchers.is(expectedSettings.getRepoLayout()))
            assertThat(listRemoteFolderItems, CoreMatchers.is(expectedSettings.getListRemoteFolderItems()))
        }
    }

}
