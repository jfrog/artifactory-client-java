package org.jfrog.artifactory.client

import org.hamcrest.CoreMatchers
import org.jfrog.artifactory.client.model.RepositoryType
import org.jfrog.artifactory.client.model.repository.settings.RepositorySettings
import org.jfrog.artifactory.client.model.repository.settings.impl.GoRepositorySettingsImpl
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

/**
 * test that client correctly sends and receives repository configuration with `go` package type
 *
 * @author David Csakvari
 */
class GoPackageTypeRepositoryTests extends BaseRepositoryTests {

    GoPackageTypeRepositoryTests() {
        remoteRepoUrl = "https://proxy.golang.org"
    }

    @Override
    RepositorySettings getRepositorySettings(RepositoryType repositoryType) {
        def settings = new GoRepositorySettingsImpl()

        settings.with {
            // this property only makes sense for a virtual repository
            externalDependenciesEnabled = true
        }
        return settings
    }

    @BeforeMethod
    protected void setUp() {
        super.setUp()
    }

    @Test(groups = "goPackageTypeRepo")
    void testGoLocalRepo() {
        artifactory.repositories().create(0, localRepo)
        def expectedSettings = localRepo.repositorySettings

        def resp = artifactory.repository(localRepo.getKey()).get()
        assertThat(resp, CoreMatchers.notNullValue())
        assertThat(resp.repoLayoutRef, CoreMatchers.is(GoRepositorySettingsImpl.defaultLayout))

        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(expectedSettings.getPackageType()))
            assertThat(repoLayout, CoreMatchers.is(expectedSettings.getRepoLayout()))

            // not applicable to local repos
            assertThat(externalDependenciesEnabled, CoreMatchers.nullValue())
        }
    }

    @Test(groups = "goPackageTypeRepo")
    void testGoRemoteRepo() {
        artifactory.repositories().create(0, remoteRepo)
        def expectedSettings = remoteRepo.repositorySettings

        def resp = artifactory.repository(remoteRepo.getKey()).get()
        assertThat(resp, CoreMatchers.notNullValue())
        assertThat(resp.repoLayoutRef, CoreMatchers.is(GoRepositorySettingsImpl.defaultLayout))

        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(expectedSettings.getPackageType()))
            assertThat(repoLayout, CoreMatchers.is(expectedSettings.getRepoLayout()))

            // not applicable to remote repos
            assertThat(externalDependenciesEnabled, CoreMatchers.is(false))
        }
    }

    @Test(groups = "goPackageTypeRepo")
    void testGoVirtualRepo() {
        artifactory.repositories().create(0, virtualRepo)
        def expectedSettings = virtualRepo.repositorySettings

        def resp = artifactory.repository(virtualRepo.getKey()).get()
        assertThat(resp, CoreMatchers.notNullValue())
        assertThat(resp.repoLayoutRef, CoreMatchers.is(GoRepositorySettingsImpl.defaultLayout))

        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(expectedSettings.getPackageType()))
            assertThat(repoLayout, CoreMatchers.is(expectedSettings.getRepoLayout()))

            assertThat(externalDependenciesEnabled, CoreMatchers.is(true))
        }
    }
}
