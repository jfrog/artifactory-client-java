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

    XrayPropertiesRepositoryTests() {
        remoteRepoUrl = "https://repo.maven.apache.org/maven2"
    }

    @Override
    RepositorySettings getRepositorySettings(RepositoryType repositoryType) {
        return new MavenRepositorySettingsImpl()
    }

    @BeforeMethod
    protected void setUp() {
        xraySettings = new XraySettingsImpl()

        xraySettings.with {
            // local
            xrayIndex = true
        }

        super.setUp()
    }

    @Test(groups = "xrayProperties")
    void testXrayPropertiesLocalRepo() {
        artifactory.repositories().create(0, localRepo)

        def resp = artifactory.repository(localRepo.getKey()).get()
        resp.getXraySettings().with {
            assertThat(xrayIndex, CoreMatchers.is(xraySettings.getXrayIndex()))
        }
    }

    @Test(groups = "xrayProperties")
    void testXrayPropertiesRemoteRepo() {
        artifactory.repositories().create(0, remoteRepo)

        def resp = artifactory.repository(remoteRepo.getKey()).get()
        resp.getXraySettings().with {
            assertThat(xrayIndex, CoreMatchers.is(xraySettings.getXrayIndex()))
        }
    }

    @Test(groups = "xrayProperties")
    void testXrayPropertiesVirtualRepo() {
        artifactory.repositories().create(0, virtualRepo)

        VirtualRepository resp = artifactory.repository(virtualRepo.getKey()).get() as VirtualRepository

        resp.getXraySettings().with {
            assertThat(xrayIndex, CoreMatchers.nullValue())
        }
    }
}
