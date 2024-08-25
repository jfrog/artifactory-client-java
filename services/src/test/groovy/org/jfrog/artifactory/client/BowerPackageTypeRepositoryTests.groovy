package org.jfrog.artifactory.client

import org.hamcrest.CoreMatchers
import org.jfrog.artifactory.client.model.RepositoryType
import org.jfrog.artifactory.client.model.repository.settings.RepositorySettings
import org.jfrog.artifactory.client.model.repository.settings.impl.BowerRepositorySettingsImpl
import org.jfrog.artifactory.client.model.repository.settings.vcs.VcsGitProvider
import org.jfrog.artifactory.client.model.repository.settings.vcs.VcsType
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

import static org.testng.Assert.assertFalse

/**
 * test that client correctly sends and receives repository configuration with `bower` package type
 *
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
class BowerPackageTypeRepositoryTests extends BaseRepositoryTests {

    @Override
    RepositorySettings getRepositorySettings(RepositoryType repositoryType) {
        def settings = new BowerRepositorySettingsImpl()

        settings.with {
            // remote
            listRemoteFolderItems = rnd.nextBoolean()
            maxUniqueSnapshots = rnd.nextInt()
            bowerRegistryUrl = "http://jfrog.com/${rnd.nextInt()}"
            vcsGitDownloadUrl = "http://jfrog.com/${rnd.nextInt()}"
            vcsGitProvider = VcsGitProvider.CUSTOM
            vcsType = VcsType.values()[rnd.nextInt(VcsType.values().length)]

            // virtual
            externalDependenciesEnabled = rnd.nextBoolean()
            externalDependenciesPatterns = Collections.singletonList("org/${rnd.nextInt()}/**".toString())
            // TODO: skip, we have to add some remote bower repo to set this property
            // externalDependenciesRemoteRepo
        }

        return settings
    }

    @BeforeMethod
    protected void setUp() {
        super.setUp()
    }

    @Test(groups = "bowerPackageTypeRepo")
    void testBowerLocalRepo() {
        artifactory.repositories().create(0, localRepo)
        def expectedSettings = localRepo.repositorySettings

        def resp = artifactory.repository(localRepo.getKey()).get()
        assertThat(resp, CoreMatchers.notNullValue())
        assertThat(resp.repoLayoutRef, CoreMatchers.is(BowerRepositorySettingsImpl.defaultLayout))
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(expectedSettings.getPackageType()))
            assertThat(repoLayout, CoreMatchers.is(expectedSettings.getRepoLayout()))

            // remote
            assertThat(listRemoteFolderItems, CoreMatchers.nullValue())
            assertThat(maxUniqueSnapshots, CoreMatchers.is(expectedSettings.getMaxUniqueSnapshots()))
            // always in resp payload
            assertThat(bowerRegistryUrl, CoreMatchers.nullValue())
            assertThat(vcsGitDownloadUrl, CoreMatchers.nullValue())
            assertThat(vcsGitProvider, CoreMatchers.nullValue())
            assertThat(vcsType, CoreMatchers.nullValue())

            // virtual
            assertThat(externalDependenciesEnabled, CoreMatchers.nullValue())
            assertThat(externalDependenciesPatterns, CoreMatchers.nullValue())
            assertThat(externalDependenciesRemoteRepo, CoreMatchers.nullValue())
        }
    }

    @Test(groups = "bowerPackageTypeRepo")
    void testBowerFederatedRepo() {
        artifactory.repositories().create(0, federatedRepo)
        def expectedSettings = federatedRepo.repositorySettings

        def resp = artifactory.repository(federatedRepo.getKey()).get()
        assertThat(resp, CoreMatchers.notNullValue())
        assertThat(resp.repoLayoutRef, CoreMatchers.is(BowerRepositorySettingsImpl.defaultLayout))
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(expectedSettings.getPackageType()))
            assertThat(repoLayout, CoreMatchers.is(expectedSettings.getRepoLayout()))

            // remote
            assertThat(listRemoteFolderItems, CoreMatchers.nullValue())
            assertThat(maxUniqueSnapshots, CoreMatchers.is(expectedSettings.getMaxUniqueSnapshots()))
            // always in resp payload
            assertThat(bowerRegistryUrl, CoreMatchers.nullValue())
            assertThat(vcsGitDownloadUrl, CoreMatchers.nullValue())
            assertThat(vcsGitProvider, CoreMatchers.nullValue())
            assertThat(vcsType, CoreMatchers.nullValue())

            // virtual
            assertThat(externalDependenciesEnabled, CoreMatchers.nullValue())
            assertThat(externalDependenciesPatterns, CoreMatchers.nullValue())
            assertThat(externalDependenciesRemoteRepo, CoreMatchers.nullValue())
        }
    }

    @Test(groups = "bowerPackageTypeRepo")
    void testBowerRemoteRepo() {
        artifactory.repositories().create(0, remoteRepo)
        def expectedSettings = remoteRepo.repositorySettings

        def resp = artifactory.repository(remoteRepo.getKey()).get()
        assertThat(resp, CoreMatchers.notNullValue())
        assertThat(resp.repoLayoutRef, CoreMatchers.is(BowerRepositorySettingsImpl.defaultLayout))
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(expectedSettings.getPackageType()))
            assertThat(repoLayout, CoreMatchers.is(expectedSettings.getRepoLayout()))

            // remote
            assertThat(listRemoteFolderItems, CoreMatchers.is(expectedSettings.getListRemoteFolderItems()))
            assertThat(bowerRegistryUrl, CoreMatchers.is(expectedSettings.getBowerRegistryUrl()))
            assertThat(vcsGitDownloadUrl, CoreMatchers.is(expectedSettings.getVcsGitDownloadUrl()))
            assertThat(vcsGitProvider, CoreMatchers.is(expectedSettings.getVcsGitProvider()))
            assertThat(vcsType, CoreMatchers.is(expectedSettings.getVcsType()))

            // virtual
            assertThat(externalDependenciesEnabled, CoreMatchers.is(expectedSettings.getExternalDependenciesEnabled()))
            assertThat(externalDependenciesPatterns, CoreMatchers.is(expectedSettings.getExternalDependenciesPatterns()))
            assertThat(externalDependenciesRemoteRepo, CoreMatchers.is(expectedSettings.getExternalDependenciesRemoteRepo()))
        }
    }

    @Test(groups = "bowerPackageTypeRepo")
    void testBowerVirtualRepo() {
        artifactory.repositories().create(0, virtualRepo)
        def expectedSettings = virtualRepo.repositorySettings

        def resp = artifactory.repository(virtualRepo.getKey()).get()
        assertThat(resp, CoreMatchers.notNullValue())
        assertThat(resp.repoLayoutRef, CoreMatchers.is(BowerRepositorySettingsImpl.defaultLayout))
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(expectedSettings.getPackageType()))
            assertThat(repoLayout, CoreMatchers.is(expectedSettings.getRepoLayout()))

            // remote
            assertThat(listRemoteFolderItems, CoreMatchers.nullValue())
            assertThat(maxUniqueSnapshots, CoreMatchers.nullValue())
            assertThat(bowerRegistryUrl, CoreMatchers.nullValue())
            assertThat(vcsGitDownloadUrl, CoreMatchers.nullValue())
            assertThat(vcsGitProvider, CoreMatchers.nullValue())
            assertThat(vcsType, CoreMatchers.nullValue())

            // virtual
            assertThat(externalDependenciesEnabled, CoreMatchers.is(expectedSettings.getExternalDependenciesEnabled()))
            assertThat(externalDependenciesPatterns, CoreMatchers.is(expectedSettings.getExternalDependenciesPatterns()))
            assertThat(externalDependenciesRemoteRepo, CoreMatchers.is(expectedSettings.getExternalDependenciesRemoteRepo()))
        }
    }

}
