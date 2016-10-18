package org.jfrog.artifactory.client

import org.hamcrest.CoreMatchers
import org.jfrog.artifactory.client.model.repository.settings.impl.MavenRepositorySettingsImpl
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

/**
 * @author Ihor Banadiga (ihorb@jfrog.com)
 */
class XrayPropertiesRepositoryTests extends BaseRepositoryTests {
  @BeforeMethod
  protected void setUp() {
    settings = new MavenRepositorySettingsImpl()

    settings.with {
      // local
      xrayIndex = true;
      blockXrayUnscannedArtifacts = rnd.nextBoolean();
      xrayMinimumBlockedSeverity = "Minor${rnd.nextInt()}";
    }

    super.setUp()
  }

  @Test(groups = "xrayProperties")
  public void testXrayPropertiesLocalRepo() {
    artifactory.repositories().create(0, localRepo)

    def resp = artifactory.repository(localRepo.getKey()).get()
    resp.getRepositorySettings().with {
      assertThat(packageType, CoreMatchers.is(settings.getPackageType()))

      // xray
      assertThat(xrayIndex, CoreMatchers.is(settings.getXrayIndex()))
      assertThat(blockXrayUnscannedArtifacts, CoreMatchers.is(settings.getBlockXrayUnscannedArtifacts()))
      assertThat(xrayMinimumBlockedSeverity, CoreMatchers.is(settings.getXrayMinimumBlockedSeverity()))
    }
  }

  @Test(groups = "xrayProperties")
  public void testXrayPropertiesRemoteRepo() {
    artifactory.repositories().create(0, remoteRepo)

    def resp = artifactory.repository(remoteRepo.getKey()).get()
    resp.getRepositorySettings().with {
      assertThat(packageType, CoreMatchers.is(settings.getPackageType()))

      // xray
      assertThat(xrayIndex, CoreMatchers.is(settings.getXrayIndex()))
      assertThat(blockXrayUnscannedArtifacts, CoreMatchers.is(settings.getBlockXrayUnscannedArtifacts()))
      assertThat(xrayMinimumBlockedSeverity, CoreMatchers.is(settings.getXrayMinimumBlockedSeverity()))
    }
  }

  @Test(groups = "xrayProperties")
  public void testXrayPropertiesVirtualRepo() {
    artifactory.repositories().create(0, virtualRepo)

    def resp = artifactory.repository(virtualRepo.getKey()).get()
    resp.getRepositorySettings().with {
      assertThat(packageType, CoreMatchers.is(settings.getPackageType()))

      // xray
      assertThat(xrayIndex, CoreMatchers.nullValue())
      assertThat(blockXrayUnscannedArtifacts, CoreMatchers.nullValue())
      assertThat(xrayMinimumBlockedSeverity, CoreMatchers.nullValue())
    }
  }
}
