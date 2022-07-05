package org.jfrog.artifactory.client

import org.hamcrest.CoreMatchers
import org.jfrog.artifactory.client.model.RepositoryType
import org.jfrog.artifactory.client.model.repository.settings.RepositorySettings
import org.jfrog.artifactory.client.model.repository.settings.impl.GenericRepositorySettingsImpl
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

/**
 * test that client correctly sends and receives repository configuration with `generic` package type
 *
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
class GenericPackageTypeRepositoryTests extends BaseRepositoryTests {

    @Override
    RepositorySettings getRepositorySettings(RepositoryType repositoryType) {
        def settings = new GenericRepositorySettingsImpl()

        settings.with {
            // remote
            listRemoteFolderItems = rnd.nextBoolean()
        }

        return settings
    }

    @BeforeMethod
    protected void setUp() {
        super.setUp()
    }

    @Test(groups = "genericPackageTypeRepo")
    void testGenericLocalRepo() {
        artifactory.repositories().create(0, localRepo)
        def expectedSettings = localRepo.repositorySettings

        def resp = artifactory.repository(localRepo.getKey()).get()
        assertThat(resp, CoreMatchers.notNullValue())
        assertThat(resp.repoLayoutRef, CoreMatchers.is(GenericRepositorySettingsImpl.defaultLayout))
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(expectedSettings.getPackageType()))
            assertThat(repoLayout, CoreMatchers.is(expectedSettings.getRepoLayout()))
            assertThat(listRemoteFolderItems, CoreMatchers.nullValue())
        }
    }

    @Test(groups = "genericPackageTypeRepo")
    void testGenericFederatedRepo() {
        artifactory.repositories().create(0, federatedRepo)
        def expectedSettings = federatedRepo.repositorySettings

        def resp = artifactory.repository(federatedRepo.getKey()).get()
        assertThat(resp, CoreMatchers.notNullValue())
        assertThat(resp.repoLayoutRef, CoreMatchers.is(GenericRepositorySettingsImpl.defaultLayout))
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(expectedSettings.getPackageType()))
            assertThat(repoLayout, CoreMatchers.is(expectedSettings.getRepoLayout()))
            assertThat(listRemoteFolderItems, CoreMatchers.nullValue())
        }
    }

    @Test(groups = "genericPackageTypeRepo")
    void testGenericRemoteRepo() {
        artifactory.repositories().create(0, remoteRepo)
        def expectedSettings = remoteRepo.repositorySettings

        def resp = artifactory.repository(remoteRepo.getKey()).get()
        assertThat(resp, CoreMatchers.notNullValue())
        assertThat(resp.repoLayoutRef, CoreMatchers.is(GenericRepositorySettingsImpl.defaultLayout))
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(expectedSettings.getPackageType()))
            assertThat(repoLayout, CoreMatchers.is(expectedSettings.getRepoLayout()))
            assertThat(listRemoteFolderItems, CoreMatchers.is(expectedSettings.getListRemoteFolderItems()))
        }
    }

    @Test(groups = "genericPackageTypeRepo")
    void testGenericVirtualRepo() {
        artifactory.repositories().create(0, virtualRepo)
        def expectedSettings = virtualRepo.repositorySettings

        def resp = artifactory.repository(virtualRepo.getKey()).get()
        assertThat(resp, CoreMatchers.notNullValue())
        assertThat(resp.repoLayoutRef, CoreMatchers.is(GenericRepositorySettingsImpl.defaultLayout))
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(expectedSettings.getPackageType()))
            assertThat(repoLayout, CoreMatchers.is(expectedSettings.getRepoLayout()))
            assertThat(listRemoteFolderItems, CoreMatchers.nullValue())
        }
    }

}
