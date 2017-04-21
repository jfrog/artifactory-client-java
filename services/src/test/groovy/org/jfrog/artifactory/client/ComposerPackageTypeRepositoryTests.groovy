package org.jfrog.artifactory.client

import org.hamcrest.CoreMatchers
import org.jfrog.artifactory.client.model.repository.settings.impl.ComposerRepositorySettingsImpl
import org.jfrog.artifactory.client.model.repository.settings.vcs.VcsGitProvider
import org.jfrog.artifactory.client.model.repository.settings.vcs.VcsType
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

/**
 * Test that client correctly sends and receives repository configuration with `composer` package type
 */
public class ComposerPackageTypeRepositoryTests extends BaseRepositoryTests {

  @BeforeMethod
  protected void setUp() {
    settings = new ComposerRepositorySettingsImpl()

    settings.with {
      // remote
      listRemoteFolderItems = rnd.nextBoolean()
      maxUniqueSnapshots = rnd.nextInt()
      composerRegistryUrl = "http://jfrog.com/${rnd.nextInt()}"
      vcsGitDownloadUrl = "http://jfrog.com/${rnd.nextInt()}"
      vcsGitProvider = VcsGitProvider.CUSTOM
      vcsType = VcsType.values()[rnd.nextInt(VcsType.values().length)]
    }

    super.setUp()
  }

  @Test(groups = "composerPackageTypeRepo")
  public void testComposerLocalRepo() {
    artifactory.repositories().create(0, localRepo)

    def resp = artifactory.repository(localRepo.getKey()).get()
    resp.getRepositorySettings().with {
      assertThat(packageType, CoreMatchers.is(settings.getPackageType()))

      // remote
      assertThat(listRemoteFolderItems, CoreMatchers.nullValue())
      assertThat(maxUniqueSnapshots, CoreMatchers.is(settings.getMaxUniqueSnapshots())) // always in resp payload
      assertThat(composerRegistryUrl, CoreMatchers.nullValue())
      assertThat(vcsGitDownloadUrl, CoreMatchers.nullValue())
      assertThat(vcsGitProvider, CoreMatchers.nullValue())
      assertThat(vcsType, CoreMatchers.nullValue())
    }
  }

  @Test(groups = "composerPackageTypeRepo")
  public void testComposerRemoteRepo() {
    artifactory.repositories().create(0, remoteRepo)

    def resp = artifactory.repository(remoteRepo.getKey()).get()
    resp.getRepositorySettings().with {
      assertThat(packageType, CoreMatchers.is(settings.getPackageType()))

      // remote
      assertThat(listRemoteFolderItems, CoreMatchers.is(settings.getListRemoteFolderItems()))
      assertThat(maxUniqueSnapshots, CoreMatchers.is(settings.getMaxUniqueSnapshots()))
      assertThat(composerRegistryUrl, CoreMatchers.is(settings.getComposerRegistryUrl()))
      assertThat(vcsGitDownloadUrl, CoreMatchers.is(settings.getVcsGitDownloadUrl()))
      assertThat(vcsGitProvider, CoreMatchers.is(settings.getVcsGitProvider()))
      assertThat(vcsType, CoreMatchers.is(settings.getVcsType()))
    }
  }

  @Test(groups = "composerPackageTypeRepo")
  public void testComposerVirtualRepo() {
    artifactory.repositories().create(0, virtualRepo)

    def resp = artifactory.repository(virtualRepo.getKey()).get()
    resp.getRepositorySettings().with {
      assertThat(packageType, CoreMatchers.is(settings.getPackageType()))

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
