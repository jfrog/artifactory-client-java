package org.jfrog.artifactory.client

import org.hamcrest.CoreMatchers
import org.jfrog.artifactory.client.model.RepositoryType
import org.jfrog.artifactory.client.model.repository.settings.RepositorySettings
import org.jfrog.artifactory.client.model.repository.settings.impl.PypiRepositorySettingsImpl
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

/**
 * test that client correctly sends and receives repository configuration with `pypi` package type
 *
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
class PypiPackageTypeRepositoryTests extends BaseRepositoryTests {

    PypiPackageTypeRepositoryTests() {
        remoteRepoUrl = "https://files.pythonhosted.org"
    }

    @Override
    RepositorySettings getRepositorySettings(RepositoryType repositoryType) {
        RepositorySettings settings = new PypiRepositorySettingsImpl()
        settings.with {
            // remote
            listRemoteFolderItems = rnd.nextBoolean()
            pypiRegistryUrl = "https://pypi.org"
        }

        return settings
    }

    @BeforeMethod
    protected void setUp() {
        super.setUp()
    }

    @Test(groups = "pypiPackageTypeRepo")
    void testPypiLocalRepo() {
        artifactory.repositories().create(0, localRepo)
        def expectedSettings = localRepo.repositorySettings

        def resp = artifactory.repository(localRepo.getKey()).get()
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(expectedSettings.getPackageType()))
            assertThat(repoLayout, CoreMatchers.is(expectedSettings.getRepoLayout()))
            assertThat(listRemoteFolderItems, CoreMatchers.nullValue())
        }
    }

    @Test(groups = "pypiPackageTypeRepo")
    void testPypiFederatedRepo() {
        artifactory.repositories().create(0, federatedRepo)
        def expectedSettings = federatedRepo.repositorySettings

        def resp = artifactory.repository(federatedRepo.getKey()).get()
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(expectedSettings.getPackageType()))
            assertThat(repoLayout, CoreMatchers.is(expectedSettings.getRepoLayout()))
            assertThat(listRemoteFolderItems, CoreMatchers.nullValue())
        }
    }

    @Test(groups = "pypiPackageTypeRepo")
    void testPypiRemoteRepo() {
        artifactory.repositories().create(0, remoteRepo)
        def expectedSettings = remoteRepo.repositorySettings

        def resp = artifactory.repository(remoteRepo.getKey()).get()
        assertThat(resp, CoreMatchers.notNullValue())
        assertThat(resp.repoLayoutRef, CoreMatchers.is(PypiRepositorySettingsImpl.defaultLayout))
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(expectedSettings.getPackageType()))
            assertThat(repoLayout, CoreMatchers.is(expectedSettings.getRepoLayout()))
            assertThat(listRemoteFolderItems, CoreMatchers.is(expectedSettings.getListRemoteFolderItems()))
            assertThat(pypiRegistryUrl, CoreMatchers.is(expectedSettings.pypiRegistryUrl))
        }
    }

    @Test(groups = "pypiPackageTypeRepo")
    void testPypiVirtualRepo() {
        artifactory.repositories().create(0, virtualRepo)
        def expectedSettings = virtualRepo.repositorySettings

        def resp = artifactory.repository(virtualRepo.getKey()).get()
        assertThat(resp, CoreMatchers.notNullValue())
        assertThat(resp.repoLayoutRef, CoreMatchers.is(PypiRepositorySettingsImpl.defaultLayout))
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(expectedSettings.getPackageType()))
            assertThat(repoLayout, CoreMatchers.is(expectedSettings.getRepoLayout()))
            assertThat(listRemoteFolderItems, CoreMatchers.nullValue())
        }
    }

}
