package org.jfrog.artifactory.client

import org.hamcrest.CoreMatchers
import org.jfrog.artifactory.client.model.RepositoryType
import org.jfrog.artifactory.client.model.impl.PackageTypeImpl
import org.jfrog.artifactory.client.model.repository.settings.RepositorySettings
import org.jfrog.artifactory.client.model.repository.settings.impl.RpmRepositorySettingsImpl
import org.jfrog.artifactory.client.model.repository.settings.impl.YumRepositorySettingsImpl
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

/**
 * test that client correctly sends and receives repository configuration with `yum` package type
 *
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
class YumPackageTypeRepositoryTests extends BaseRepositoryTests {

    YumPackageTypeRepositoryTests() {
        remoteRepoUrl = "http://mirror.centos.org/centos"
    }

    @Override
    RepositorySettings getRepositorySettings(RepositoryType repositoryType) {
        def settings = new YumRepositorySettingsImpl()

        settings.with {
            // local
            calculateYumMetadata = rnd.nextBoolean()
            groupFileNames = "groups-${rnd.nextInt()}.xml"
            listRemoteFolderItems = rnd.nextBoolean()

            // remote
            yumRootDepth = rnd.nextInt()
        }

        return settings
    }

    @BeforeMethod
    protected void setUp() {
        // only local and remote repository supported
        prepareVirtualRepo = false

        super.setUp()
    }

    @Test(groups = "yumPackageTypeRepo")
    void testYumLocalRepo() {
        artifactory.repositories().create(0, localRepo)
        def expectedSettings = localRepo.repositorySettings

        def resp = artifactory.repository(localRepo.getKey()).get()
        assertThat(resp, CoreMatchers.notNullValue())
        assertThat(resp.repoLayoutRef, CoreMatchers.is(RpmRepositorySettingsImpl.defaultLayout))
        resp.getRepositorySettings().with {
            // The package type is 'rpm' since Artifactory 5.0.0
            assertThat(packageType, CoreMatchers.is(PackageTypeImpl.rpm))
            assertThat(repoLayout, CoreMatchers.is(expectedSettings.getRepoLayout()))

            // local
            assertThat(calculateYumMetadata, CoreMatchers.is(expectedSettings.getCalculateYumMetadata()))
            // TODO: property is not returned by the artifactory
            // assertThat(groupFileNames, CoreMatchers.is(specRepo.getGroupFileNames()))
            assertThat(groupFileNames, CoreMatchers.is(CoreMatchers.nullValue()))
            assertThat(yumRootDepth, CoreMatchers.is(expectedSettings.getYumRootDepth()))

            // remote
            assertThat(listRemoteFolderItems, CoreMatchers.is(CoreMatchers.nullValue()))
        }
    }

    @Test(groups = "yumPackageTypeRepo")
    void testYumFederatedRepo() {
        artifactory.repositories().create(0, federatedRepo)
        def expectedSettings = federatedRepo.repositorySettings

        def resp = artifactory.repository(federatedRepo.getKey()).get()
        assertThat(resp, CoreMatchers.notNullValue())
        assertThat(resp.repoLayoutRef, CoreMatchers.is(RpmRepositorySettingsImpl.defaultLayout))
        resp.getRepositorySettings().with {
            // The package type is 'rpm' since Artifactory 5.0.0
            assertThat(packageType, CoreMatchers.is(PackageTypeImpl.rpm))
            assertThat(repoLayout, CoreMatchers.is(expectedSettings.getRepoLayout()))

            // local
            assertThat(calculateYumMetadata, CoreMatchers.is(expectedSettings.getCalculateYumMetadata()))
            // assertThat(groupFileNames, CoreMatchers.is(specRepo.getGroupFileNames()))
            assertThat(groupFileNames, CoreMatchers.is(CoreMatchers.nullValue()))
            assertThat(yumRootDepth, CoreMatchers.is(expectedSettings.getYumRootDepth()))

            // remote
            assertThat(listRemoteFolderItems, CoreMatchers.is(CoreMatchers.nullValue()))
        }
    }

    @Test(groups = "yumPackageTypeRepo")
    void testYumRemoteRepo() {
        artifactory.repositories().create(0, remoteRepo)
        def expectedSettings = remoteRepo.repositorySettings

        def resp = artifactory.repository(remoteRepo.getKey()).get()
        assertThat(resp, CoreMatchers.notNullValue())
        assertThat(resp.repoLayoutRef, CoreMatchers.is(RpmRepositorySettingsImpl.defaultLayout))
        resp.getRepositorySettings().with {
            // The package type is 'rpm' since Artifactory 5.0.0
            assertThat(packageType, CoreMatchers.is(PackageTypeImpl.rpm))
            assertThat(repoLayout, CoreMatchers.is(expectedSettings.getRepoLayout()))

            // remote
            assertThat(listRemoteFolderItems, CoreMatchers.is(expectedSettings.getListRemoteFolderItems()))

            // local
            assertThat(calculateYumMetadata, CoreMatchers.is(CoreMatchers.nullValue()))
            assertThat(groupFileNames, CoreMatchers.is(CoreMatchers.nullValue()))
            assertThat(yumRootDepth, CoreMatchers.is(CoreMatchers.nullValue()))
        }
    }
}
