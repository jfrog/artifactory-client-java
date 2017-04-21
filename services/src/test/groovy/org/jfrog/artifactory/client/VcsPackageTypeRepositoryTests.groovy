package org.jfrog.artifactory.client

import org.hamcrest.CoreMatchers
import org.jfrog.artifactory.client.model.*
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

    @BeforeMethod
    protected void setUp() {
        settings = new VcsRepositorySettingsImpl()

        settings.with {
            // remote
            vcsGitDownloadUrl = "http://jfrog.com/${rnd.nextInt()}"
            vcsGitProvider = VcsGitProvider.CUSTOM
            vcsType = VcsType.values()[rnd.nextInt(VcsType.values().length)]
            listRemoteFolderItems = rnd.nextBoolean()
            maxUniqueSnapshots = rnd.nextInt()
        }

        // only remote repository supported
        prepareLocalRepo = false
        prepareVirtualRepo = false

        super.setUp()
    }

    @Test(groups = "vcsPackageTypeRepo")
    public void testVcsRemoteRepo() {
        artifactory.repositories().create(0, remoteRepo)

        def resp = artifactory.repository(remoteRepo.getKey()).get()
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(settings.getPackageType()))

            // remote
            assertThat(vcsGitDownloadUrl, CoreMatchers.is(settings.getVcsGitDownloadUrl()))
            assertThat(vcsGitProvider, CoreMatchers.is(settings.getVcsGitProvider()))
            assertThat(vcsType, CoreMatchers.is(settings.getVcsType()))
            assertThat(listRemoteFolderItems, CoreMatchers.is(settings.getListRemoteFolderItems()))
            assertThat(maxUniqueSnapshots, CoreMatchers.is(settings.getMaxUniqueSnapshots()))
        }
    }

}
