package org.jfrog.artifactory.client

import org.hamcrest.CoreMatchers
import org.jfrog.artifactory.client.model.RepositoryType
import org.jfrog.artifactory.client.model.impl.RepositoryTypeImpl
import org.jfrog.artifactory.client.model.repository.settings.RepositorySettings
import org.jfrog.artifactory.client.model.repository.settings.impl.VcsRepositorySettingsImpl
import org.jfrog.artifactory.client.model.repository.settings.vcs.VcsGitProvider
import org.jfrog.artifactory.client.model.repository.settings.vcs.VcsType
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

/**
 * test that client correctly sends and receives repository configuration with `vcs` package type
 *
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
public class VcsPackageTypeRepositoryTests extends BaseRepositoryTests {

    @Override
    RepositorySettings getRepositorySettings(RepositoryType repositoryType) {
        if (repositoryType == RepositoryTypeImpl.LOCAL) {
            return null
        }

        def settings = new VcsRepositorySettingsImpl()
        settings.with {
            // remote
            vcsGitDownloadUrl = "http://jfrog.com/${rnd.nextInt()}"
            vcsGitProvider = VcsGitProvider.CUSTOM
            vcsType = VcsType.values()[rnd.nextInt(VcsType.values().length)]
            listRemoteFolderItems = rnd.nextBoolean()
            maxUniqueSnapshots = rnd.nextInt()
        }

        return settings
    }

    @BeforeMethod
    protected void setUp() {
        // only remote repository supported
        prepareLocalRepo = false
        prepareVirtualRepo = false

        super.setUp()
    }

    @Test(groups = "vcsPackageTypeRepo")
    public void testVcsRemoteRepo() {
        artifactory.repositories().create(0, remoteRepo)
        def expectedSettings = remoteRepo.repositorySettings

        def resp = artifactory.repository(remoteRepo.getKey()).get()
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(expectedSettings.getPackageType()))

            // remote
            assertThat(vcsGitDownloadUrl, CoreMatchers.is(expectedSettings.getVcsGitDownloadUrl()))
            assertThat(vcsGitProvider, CoreMatchers.is(expectedSettings.getVcsGitProvider()))
            assertThat(vcsType, CoreMatchers.is(expectedSettings.getVcsType()))
            assertThat(listRemoteFolderItems, CoreMatchers.is(expectedSettings.getListRemoteFolderItems()))
            assertThat(maxUniqueSnapshots, CoreMatchers.is(expectedSettings.getMaxUniqueSnapshots()))
        }
    }

}
