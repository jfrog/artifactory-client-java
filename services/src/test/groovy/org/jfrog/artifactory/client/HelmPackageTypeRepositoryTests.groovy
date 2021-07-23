package org.jfrog.artifactory.client

import org.hamcrest.CoreMatchers
import org.jfrog.artifactory.client.model.RepositoryType
import org.jfrog.artifactory.client.model.repository.settings.RepositorySettings
import org.jfrog.artifactory.client.model.repository.settings.impl.HelmRepositorySettingsImpl
import org.testng.annotations.Test

/**
 * test that client correctly sends and receives repository configuration with `helm` package type
 *
 * @author Glen Lockhart (glen@openet.com)
 */
class HelmPackageTypeRepositoryTests extends BaseRepositoryTests {

    HelmPackageTypeRepositoryTests() {
        remoteRepoUrl = "https://repo.chartcenter.io"
    }

    @Override
    RepositorySettings getRepositorySettings(RepositoryType repositoryType) {
        def settings = new HelmRepositorySettingsImpl()

        settings.with {
            //virtual
            virtualRetrievalCachePeriodSecs = 7210
        }
        return settings
    }

    @Test(groups = "helmPackageTypeRepo")
    void testHelmLocalRepo() {
        artifactory.repositories().create(0, localRepo)
        def expectedSettings = localRepo.repositorySettings

        def resp = artifactory.repository(localRepo.getKey()).get()
        assertThat(resp, CoreMatchers.notNullValue())
        assertThat(resp.repoLayoutRef, CoreMatchers.is(HelmRepositorySettingsImpl.defaultLayout))

        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(expectedSettings.getPackageType()))
            assertThat(repoLayout, CoreMatchers.is(expectedSettings.getRepoLayout()))

            //not applicable to local repos
            assertThat(virtualRetrievalCachePeriodSecs, CoreMatchers.nullValue())
        }
    }

    @Test(groups = "helmPackageTypeRepo")
    void testHelmRemoteRepo() {
        artifactory.repositories().create(0, remoteRepo)
        def expectedSettings = remoteRepo.repositorySettings

        def resp = artifactory.repository(remoteRepo.getKey()).get()
        assertThat(resp, CoreMatchers.notNullValue())
        assertThat(resp.repoLayoutRef, CoreMatchers.is(HelmRepositorySettingsImpl.defaultLayout))

        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(expectedSettings.getPackageType()))
            assertThat(repoLayout, CoreMatchers.is(expectedSettings.getRepoLayout()))

            //not applicable to remote repos
            assertThat(virtualRetrievalCachePeriodSecs, CoreMatchers.nullValue())
        }
    }

    @Test(groups = "helmPackageTypeRepo")
    void testHelmVirtualRepo() {
        artifactory.repositories().create(0, virtualRepo)
        def expectedSettings = virtualRepo.repositorySettings

        def resp = artifactory.repository(virtualRepo.getKey()).get()
        assertThat(resp, CoreMatchers.notNullValue())
        assertThat(resp.repoLayoutRef, CoreMatchers.is(HelmRepositorySettingsImpl.defaultLayout))

        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(expectedSettings.getPackageType()))
            assertThat(repoLayout, CoreMatchers.is(expectedSettings.getRepoLayout()))

            assertThat(virtualRetrievalCachePeriodSecs, CoreMatchers.is(expectedSettings.getVirtualRetrievalCachePeriodSecs()))
        }
    }
}
