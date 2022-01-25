package org.jfrog.artifactory.client

import org.hamcrest.CoreMatchers
import org.jfrog.artifactory.client.model.RepositoryType
import org.jfrog.artifactory.client.model.repository.settings.RepositorySettings
import org.jfrog.artifactory.client.model.repository.settings.impl.NpmRepositorySettingsImpl
import org.testng.annotations.Test

import static org.testng.Assert.assertFalse

/**
 * test that client correctly sends and receives repository configuration with `npm` package type
 *
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
class NpmPackageTypeRepositoryTests extends BaseRepositoryTests {

    NpmPackageTypeRepositoryTests() {
        remoteRepoUrl = "https://registry.npmjs.org"
    }

    @Override
    RepositorySettings getRepositorySettings(RepositoryType repositoryType) {
        def settings = new NpmRepositorySettingsImpl()

        settings.with {
            // remote
            listRemoteFolderItems = rnd.nextBoolean()

            // virtual
            externalDependenciesEnabled = rnd.nextBoolean()
            externalDependenciesPatterns = Collections.singletonList("org/${rnd.nextInt()}/**".toString())
            // TODO: skip, we have to add some remote bower repo to set this property
            externalDependenciesRemoteRepo
        }

        return settings
    }

    @Test(groups = "npmPackageTypeRepo")
    void testNpmLocalRepo() {
        artifactory.repositories().create(0, localRepo)
        def expectedSettings = localRepo.repositorySettings

        def resp = artifactory.repository(localRepo.getKey()).get()
        assertThat(resp, CoreMatchers.notNullValue())
        assertThat(resp.repoLayoutRef, CoreMatchers.is(NpmRepositorySettingsImpl.defaultLayout))

        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(expectedSettings.getPackageType()))
            assertThat(repoLayout, CoreMatchers.is(expectedSettings.getRepoLayout()))

            // remote
            assertThat(listRemoteFolderItems, CoreMatchers.nullValue())

            // virtual
            assertThat(externalDependenciesEnabled, CoreMatchers.nullValue())
            assertThat(externalDependenciesPatterns, CoreMatchers.nullValue())
            assertThat(externalDependenciesRemoteRepo, CoreMatchers.nullValue())
        }
    }

    @Test(groups = "npmPackageTypeRepo")
    void testNpmFederatedRepo() {
        artifactory.repositories().create(0, federatedRepo)
        def expectedSettings = federatedRepo.repositorySettings

        def resp = artifactory.repository(federatedRepo.getKey()).get()
        assertThat(resp, CoreMatchers.notNullValue())
        assertThat(resp.repoLayoutRef, CoreMatchers.is(NpmRepositorySettingsImpl.defaultLayout))

        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(expectedSettings.getPackageType()))
            assertThat(repoLayout, CoreMatchers.is(expectedSettings.getRepoLayout()))

            // remote
            assertThat(listRemoteFolderItems, CoreMatchers.nullValue())

            // virtual
            assertThat(externalDependenciesEnabled, CoreMatchers.nullValue())
            assertThat(externalDependenciesPatterns, CoreMatchers.nullValue())
            assertThat(externalDependenciesRemoteRepo, CoreMatchers.nullValue())
        }
    }

    @Test(groups = "npmPackageTypeRepo")
    void testNpmRemoteRepo() {
        artifactory.repositories().create(0, remoteRepo)
        def expectedSettings = remoteRepo.repositorySettings

        def resp = artifactory.repository(remoteRepo.getKey()).get()
        assertThat(resp, CoreMatchers.notNullValue())
        assertThat(resp.repoLayoutRef, CoreMatchers.is(NpmRepositorySettingsImpl.defaultLayout))
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(expectedSettings.getPackageType()))
            assertThat(repoLayout, CoreMatchers.is(expectedSettings.getRepoLayout()))

            // remote
            assertThat(listRemoteFolderItems, CoreMatchers.is(expectedSettings.getListRemoteFolderItems()))

            // virtual
            assertFalse(externalDependenciesEnabled)
            assertThat(externalDependenciesPatterns, CoreMatchers.nullValue())
            assertThat(externalDependenciesRemoteRepo, CoreMatchers.nullValue())
        }
    }

    @Test(groups = "npmPackageTypeRepo")
    void testNpmVirtualRepo() {
        artifactory.repositories().create(0, virtualRepo)
        def expectedSettings = virtualRepo.repositorySettings

        def resp = artifactory.repository(virtualRepo.getKey()).get()
        assertThat(resp, CoreMatchers.notNullValue())
        assertThat(resp.repoLayoutRef, CoreMatchers.is(NpmRepositorySettingsImpl.defaultLayout))
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(expectedSettings.getPackageType()))
            assertThat(repoLayout, CoreMatchers.is(expectedSettings.getRepoLayout()))

            // remote
            assertThat(listRemoteFolderItems, CoreMatchers.nullValue())

            // virtual
            assertThat(externalDependenciesEnabled, CoreMatchers.is(expectedSettings.getExternalDependenciesEnabled()))
            assertThat(externalDependenciesPatterns, CoreMatchers.is(expectedSettings.getExternalDependenciesPatterns()))
            assertThat(externalDependenciesRemoteRepo, CoreMatchers.is(expectedSettings.getExternalDependenciesRemoteRepo()))
        }
    }
}
