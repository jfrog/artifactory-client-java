package org.jfrog.artifactory.client

import org.hamcrest.CoreMatchers
import org.jfrog.artifactory.client.model.RepositoryType
import org.jfrog.artifactory.client.model.VirtualRepository
import org.jfrog.artifactory.client.model.repository.settings.RepositorySettings
import org.jfrog.artifactory.client.model.repository.settings.impl.MavenRepositorySettingsImpl
import org.jfrog.artifactory.client.model.xray.settings.impl.XraySettingsImpl
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

/**
 * @author Ihor Banadiga (ihorb@jfrog.com)
 */
class XrayPropertiesRepositoryTests extends BaseRepositoryTests {
  @Override
  RepositorySettings getRepositorySettings(RepositoryType repositoryType) {
    return new MavenRepositorySettingsImpl()
  }

  @BeforeMethod
  protected void setUp() {
    xraySettings = new XraySettingsImpl()

    xraySettings.with {
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
    resp.getXraySettings().with {
      assertThat(xrayIndex, CoreMatchers.is(xraySettings.getXrayIndex()))
      assertThat(blockXrayUnscannedArtifacts, CoreMatchers.is(xraySettings.getBlockXrayUnscannedArtifacts()))
      assertThat(xrayMinimumBlockedSeverity, CoreMatchers.is(xraySettings.getXrayMinimumBlockedSeverity()))
    }
  }

  @Test(groups = "xrayProperties")
  public void testXrayPropertiesRemoteRepo() {
    artifactory.repositories().create(0, remoteRepo)

    def resp = artifactory.repository(remoteRepo.getKey()).get()
    resp.getXraySettings().with {
      assertThat(xrayIndex, CoreMatchers.is(xraySettings.getXrayIndex()))
      assertThat(blockXrayUnscannedArtifacts, CoreMatchers.is(xraySettings.getBlockXrayUnscannedArtifacts()))
      assertThat(xrayMinimumBlockedSeverity, CoreMatchers.is(xraySettings.getXrayMinimumBlockedSeverity()))
    }
  }

  @Test(groups = "xrayProperties")
  public void testXrayPropertiesVirtualRepo() {
    artifactory.repositories().create(0, virtualRepo)

    VirtualRepository resp = artifactory.repository(virtualRepo.getKey()).get() as VirtualRepository

    resp.getXraySettings().with {
      assertThat(xrayIndex, CoreMatchers.nullValue())
      assertThat(blockXrayUnscannedArtifacts, CoreMatchers.nullValue())
      assertThat(xrayMinimumBlockedSeverity, CoreMatchers.nullValue())
    }
  }
}
