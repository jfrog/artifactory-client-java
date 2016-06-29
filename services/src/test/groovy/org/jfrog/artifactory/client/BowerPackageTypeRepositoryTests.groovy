package org.jfrog.artifactory.client

import org.hamcrest.CoreMatchers
import org.jfrog.artifactory.client.model.*
import org.jfrog.artifactory.client.model.repository.settings.impl.BowerRepositorySettingsImpl
import org.jfrog.artifactory.client.model.repository.settings.vcs.VcsGitProvider
import org.jfrog.artifactory.client.model.repository.settings.vcs.VcsType
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

/**
 * test that client correctly sends and receives repository configuration with `bower` package type
 * 
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
public class BowerPackageTypeRepositoryTests extends BaseRepositoryTests {

    @BeforeMethod
    protected void setUp() {
        settings = new BowerRepositorySettingsImpl()

        settings.with {
            // remote
            listRemoteFolderItems = rnd.nextBoolean()
            maxUniqueSnapshots = rnd.nextInt()
            bowerRegistryUrl = "http://jfrog.com/${rnd.nextInt()}"
            vcsGitDownloadUrl = "http://jfrog.com/${rnd.nextInt()}"
            vcsGitProvider  = VcsGitProvider.CUSTOM
            vcsType = VcsType.values()[rnd.nextInt(VcsType.values().length)]

            // virtual
            externalDependenciesEnabled = rnd.nextBoolean()
            externalDependenciesPatterns = Collections.singletonList("org/${rnd.nextInt()}/**".toString())
            // TODO: skip, we have to add some remote bower repo to set this property
            // externalDependenciesRemoteRepo
        }

        super.setUp()
    }

    @Test(groups = "bowerPackageTypeRepo")
    public void testBowerLocalRepo() {
        artifactory.repositories().create(0, localRepo)

        def resp = artifactory.repository(localRepo.getKey()).get()
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(settings.getPackageType()))

            // remote
            assertThat(listRemoteFolderItems, CoreMatchers.nullValue())
            assertThat(maxUniqueSnapshots, CoreMatchers.is(settings.getMaxUniqueSnapshots())) // always in resp payload
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
    public void testBowerRemoteRepo() {
        artifactory.repositories().create(0, remoteRepo)

        def resp = artifactory.repository(remoteRepo.getKey()).get()
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(settings.getPackageType()))

            // remote
            assertThat(listRemoteFolderItems, CoreMatchers.is(settings.getListRemoteFolderItems()))
            assertThat(maxUniqueSnapshots, CoreMatchers.is(settings.getMaxUniqueSnapshots()))
            assertThat(bowerRegistryUrl, CoreMatchers.is(settings.getBowerRegistryUrl()))
            assertThat(vcsGitDownloadUrl, CoreMatchers.is(settings.getVcsGitDownloadUrl()))
            assertThat(vcsGitProvider, CoreMatchers.is(settings.getVcsGitProvider()))
            assertThat(vcsType, CoreMatchers.is(settings.getVcsType()))

            // virtual
            assertThat(externalDependenciesEnabled, CoreMatchers.nullValue())
            assertThat(externalDependenciesPatterns, CoreMatchers.nullValue())
            assertThat(externalDependenciesRemoteRepo, CoreMatchers.nullValue())
        }
    }

    @Test(groups = "bowerPackageTypeRepo")
    public void testBowerVirtualRepo() {
        artifactory.repositories().create(0, virtualRepo)

        def resp = artifactory.repository(virtualRepo.getKey()).get()
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(settings.getPackageType()))

            // remote
            assertThat(listRemoteFolderItems, CoreMatchers.nullValue())
            assertThat(maxUniqueSnapshots, CoreMatchers.nullValue())
            assertThat(bowerRegistryUrl, CoreMatchers.nullValue())
            assertThat(vcsGitDownloadUrl, CoreMatchers.nullValue())
            assertThat(vcsGitProvider, CoreMatchers.nullValue())
            assertThat(vcsType, CoreMatchers.nullValue())

            // virtual
            assertThat(externalDependenciesEnabled, CoreMatchers.is(settings.getExternalDependenciesEnabled()))
            assertThat(externalDependenciesPatterns, CoreMatchers.is(settings.getExternalDependenciesPatterns()))
            assertThat(externalDependenciesRemoteRepo, CoreMatchers.is(settings.getExternalDependenciesRemoteRepo()))
        }
    }

}
