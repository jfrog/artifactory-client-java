package org.jfrog.artifactory.client

import org.hamcrest.CoreMatchers
import org.jfrog.artifactory.client.model.RepositoryType
import org.jfrog.artifactory.client.model.repository.settings.RepositorySettings
import org.jfrog.artifactory.client.model.repository.settings.impl.CocoaPodsRepositorySettingsImpl
import org.jfrog.artifactory.client.model.repository.settings.vcs.VcsGitProvider
import org.jfrog.artifactory.client.model.repository.settings.vcs.VcsType
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

/**
 * test that client correctly sends and receives repository configuration with `cocoapods` package type
 *
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
class CocoaPodsPackageTypeRepositoryTests extends BaseRepositoryTests {

    @Override
    RepositorySettings getRepositorySettings(RepositoryType repositoryType) {
        def settings = new CocoaPodsRepositorySettingsImpl()

        settings.with {
            // remote
            listRemoteFolderItems = rnd.nextBoolean()
            maxUniqueSnapshots = rnd.nextInt()
            podsSpecsRepoUrl = "http://jfrog.com/${rnd.nextInt()}"
            vcsGitDownloadUrl = "http://jfrog.com/${rnd.nextInt()}"
            vcsGitProvider = VcsGitProvider.CUSTOM
            vcsType = VcsType.values()[rnd.nextInt(VcsType.values().length)]
        }

        return settings
    }

    @BeforeMethod
    protected void setUp() {
        // only local and remote repository supported
        prepareVirtualRepo = false

        super.setUp()
    }

    @Test(groups = "cocoapodsPackageTypeRepo")
    void testCocoaPodsLocalRepo() {
        artifactory.repositories().create(0, localRepo)
        def expectedSettings = localRepo.repositorySettings

        def resp = artifactory.repository(localRepo.getKey()).get()
        assertThat(resp, CoreMatchers.notNullValue())
        assertThat(resp.repoLayoutRef, CoreMatchers.is(CocoaPodsRepositorySettingsImpl.defaultLayout))
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(expectedSettings.getPackageType()))
            assertThat(repoLayout, CoreMatchers.is(expectedSettings.getRepoLayout()))

            // remote
            assertThat(listRemoteFolderItems, CoreMatchers.nullValue())
            assertThat(maxUniqueSnapshots, CoreMatchers.is(expectedSettings.getMaxUniqueSnapshots()))
            // always in resp payload
            assertThat(podsSpecsRepoUrl, CoreMatchers.nullValue())
            assertThat(vcsGitDownloadUrl, CoreMatchers.nullValue())
            assertThat(vcsGitProvider, CoreMatchers.nullValue())
            assertThat(vcsType, CoreMatchers.nullValue())
        }
    }

    @Test(groups = "cocoapodsPackageTypeRepo")
    void testCocoaPodsRemoteRepo() {
        artifactory.repositories().create(0, remoteRepo)
        def expectedSettings = remoteRepo.repositorySettings

        def resp = artifactory.repository(remoteRepo.getKey()).get()
        assertThat(resp, CoreMatchers.notNullValue())
        assertThat(resp.repoLayoutRef, CoreMatchers.is(CocoaPodsRepositorySettingsImpl.defaultLayout))
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(expectedSettings.getPackageType()))
            assertThat(repoLayout, CoreMatchers.is(expectedSettings.getRepoLayout()))

            // remote
            assertThat(listRemoteFolderItems, CoreMatchers.is(expectedSettings.getListRemoteFolderItems()))
            assertThat(maxUniqueSnapshots, CoreMatchers.is(expectedSettings.getMaxUniqueSnapshots()))
            assertThat(podsSpecsRepoUrl, CoreMatchers.is(expectedSettings.getPodsSpecsRepoUrl()))
            assertThat(vcsGitDownloadUrl, CoreMatchers.is(expectedSettings.getVcsGitDownloadUrl()))
            assertThat(vcsGitProvider, CoreMatchers.is(expectedSettings.getVcsGitProvider()))
            assertThat(vcsType, CoreMatchers.is(expectedSettings.getVcsType()))
        }
    }

}
