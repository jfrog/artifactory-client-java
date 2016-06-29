package org.jfrog.artifactory.client

import org.hamcrest.CoreMatchers
import org.jfrog.artifactory.client.model.*
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
public class CocoaPodsPackageTypeRepositoryTests extends BaseRepositoryTests {

    @BeforeMethod
    protected void setUp() {
        settings = new CocoaPodsRepositorySettingsImpl()

        settings.with {
            // remote
            listRemoteFolderItems = rnd.nextBoolean()
            maxUniqueSnapshots = rnd.nextInt()
            podsSpecsRepoUrl = "http://jfrog.com/${rnd.nextInt()}"
            vcsGitDownloadUrl = "http://jfrog.com/${rnd.nextInt()}"
            vcsGitProvider  = VcsGitProvider.CUSTOM
            vcsType = VcsType.values()[rnd.nextInt(VcsType.values().length)]
        }

        // only local and remote repository supported
        prepareVirtualRepo = false

        super.setUp()
    }

    @Test(groups = "cocoapodsPackageTypeRepo")
    public void testCocoaPodsLocalRepo() {
        artifactory.repositories().create(0, localRepo)

        def resp = artifactory.repository(localRepo.getKey()).get()
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(settings.getPackageType()))

            // remote
            assertThat(listRemoteFolderItems, CoreMatchers.nullValue())
            assertThat(maxUniqueSnapshots, CoreMatchers.is(settings.getMaxUniqueSnapshots())) // always in resp payload
            assertThat(podsSpecsRepoUrl, CoreMatchers.nullValue())
            assertThat(vcsGitDownloadUrl, CoreMatchers.nullValue())
            assertThat(vcsGitProvider, CoreMatchers.nullValue())
            assertThat(vcsType, CoreMatchers.nullValue())
        }
    }

    @Test(groups = "cocoapodsPackageTypeRepo")
    public void testCocoaPodsRemoteRepo() {
        artifactory.repositories().create(0, remoteRepo)

        def resp = artifactory.repository(remoteRepo.getKey()).get()
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(settings.getPackageType()))

            // remote
            assertThat(listRemoteFolderItems, CoreMatchers.is(settings.getListRemoteFolderItems()))
            assertThat(maxUniqueSnapshots, CoreMatchers.is(settings.getMaxUniqueSnapshots()))
            assertThat(podsSpecsRepoUrl, CoreMatchers.is(settings.getPodsSpecsRepoUrl()))
            assertThat(vcsGitDownloadUrl, CoreMatchers.is(settings.getVcsGitDownloadUrl()))
            assertThat(vcsGitProvider, CoreMatchers.is(settings.getVcsGitProvider()))
            assertThat(vcsType, CoreMatchers.is(settings.getVcsType()))
        }
    }

}
