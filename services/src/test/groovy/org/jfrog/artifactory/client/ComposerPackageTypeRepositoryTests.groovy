package org.jfrog.artifactory.client

import org.hamcrest.CoreMatchers
import org.jfrog.artifactory.client.model.RepositoryType
import org.jfrog.artifactory.client.model.repository.settings.RepositorySettings
import org.jfrog.artifactory.client.model.repository.settings.impl.ComposerRepositorySettingsImpl
import org.jfrog.artifactory.client.model.repository.settings.vcs.VcsGitProvider
import org.jfrog.artifactory.client.model.repository.settings.vcs.VcsType
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

/**
 * Test that client correctly sends and receives repository configuration with `composer` package type
 */
public class ComposerPackageTypeRepositoryTests extends BaseRepositoryTests {

    @Override
    RepositorySettings getRepositorySettings(RepositoryType repositoryType) {
        def settings = new ComposerRepositorySettingsImpl()

        settings.with {
            // remote
            listRemoteFolderItems = rnd.nextBoolean()
            maxUniqueSnapshots = rnd.nextInt()
            composerRegistryUrl = "http://jfrog.com/${rnd.nextInt()}"
            vcsGitDownloadUrl = "http://jfrog.com/${rnd.nextInt()}"
            vcsGitProvider = VcsGitProvider.CUSTOM
            vcsType = VcsType.values()[rnd.nextInt(VcsType.values().length)]
        }

        return settings
    }

    @BeforeMethod
    protected void setUp() {
        super.setUp()
    }

    @Test(groups = "composerPackageTypeRepo")
    public void testComposerLocalRepo() {
        artifactory.repositories().create(0, localRepo)
        def expectedSettings = localRepo.repositorySettings

        def resp = artifactory.repository(localRepo.getKey()).get()
        assertThat(resp, CoreMatchers.notNullValue())
        assertThat(resp.repoLayoutRef, CoreMatchers.is(ComposerRepositorySettingsImpl.defaultLayout))
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(expectedSettings.getPackageType()))
            assertThat(repoLayout, CoreMatchers.is(expectedSettings.getRepoLayout()))

            // remote
            assertThat(listRemoteFolderItems, CoreMatchers.nullValue())
            assertThat(maxUniqueSnapshots, CoreMatchers.is(expectedSettings.getMaxUniqueSnapshots())) // always in resp payload
            assertThat(composerRegistryUrl, CoreMatchers.nullValue())
            assertThat(vcsGitDownloadUrl, CoreMatchers.nullValue())
            assertThat(vcsGitProvider, CoreMatchers.nullValue())
            assertThat(vcsType, CoreMatchers.nullValue())
        }
    }

    @Test(groups = "composerPackageTypeRepo")
    public void testComposerRemoteRepo() {
        artifactory.repositories().create(0, remoteRepo)
        def expectedSettings = remoteRepo.repositorySettings

        def resp = artifactory.repository(remoteRepo.getKey()).get()
        assertThat(resp, CoreMatchers.notNullValue())
        assertThat(resp.repoLayoutRef, CoreMatchers.is(ComposerRepositorySettingsImpl.defaultLayout))
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(expectedSettings.getPackageType()))
            assertThat(repoLayout, CoreMatchers.is(expectedSettings.getRepoLayout()))

            // remote
            assertThat(listRemoteFolderItems, CoreMatchers.is(expectedSettings.getListRemoteFolderItems()))
            assertThat(maxUniqueSnapshots, CoreMatchers.is(expectedSettings.getMaxUniqueSnapshots()))
            assertThat(composerRegistryUrl, CoreMatchers.is(expectedSettings.getComposerRegistryUrl()))
            assertThat(vcsGitDownloadUrl, CoreMatchers.is(expectedSettings.getVcsGitDownloadUrl()))
            assertThat(vcsGitProvider, CoreMatchers.is(expectedSettings.getVcsGitProvider()))
            assertThat(vcsType, CoreMatchers.is(expectedSettings.getVcsType()))
        }
    }

    @Test(groups = "composerPackageTypeRepo")
    public void testComposerVirtualRepo() {
        artifactory.repositories().create(0, virtualRepo)
        def expectedSettings = virtualRepo.repositorySettings

        def resp = artifactory.repository(virtualRepo.getKey()).get()
        assertThat(resp, CoreMatchers.notNullValue())
        assertThat(resp.repoLayoutRef, CoreMatchers.is(ComposerRepositorySettingsImpl.defaultLayout))
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(expectedSettings.getPackageType()))
            assertThat(repoLayout, CoreMatchers.is(expectedSettings.getRepoLayout()))

            // remote
            assertThat(listRemoteFolderItems, CoreMatchers.nullValue())
            assertThat(maxUniqueSnapshots, CoreMatchers.nullValue())
            assertThat(composerRegistryUrl, CoreMatchers.nullValue())
            assertThat(vcsGitDownloadUrl, CoreMatchers.nullValue())
            assertThat(vcsGitProvider, CoreMatchers.nullValue())
            assertThat(vcsType, CoreMatchers.nullValue())
        }
    }
}
