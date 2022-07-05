package org.jfrog.artifactory.client

import org.hamcrest.CoreMatchers
import org.jfrog.artifactory.client.model.RepositoryType
import org.jfrog.artifactory.client.model.impl.CustomPackageTypeImpl
import org.jfrog.artifactory.client.model.impl.PackageTypeImpl
import org.jfrog.artifactory.client.model.repository.settings.RepositorySettings
import org.jfrog.artifactory.client.model.repository.settings.impl.CustomRepositorySettingsImpl
import org.jfrog.artifactory.client.model.repository.settings.impl.RpmRepositorySettingsImpl
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

class CustomPackageTypeRepositoryTests extends BaseRepositoryTests {

    private boolean someCalculateYumMetadata
    private String someGroupFileNames
    private boolean someListRemoteFolderItems
    private int someYumRootDepth

    CustomPackageTypeRepositoryTests() {
        remoteRepoUrl = "http://mirror.centos.org/centos"
    }

    @Override
    RepositorySettings getRepositorySettings(RepositoryType repositoryType) {
        CustomPackageTypeImpl customPackageType = new CustomPackageTypeImpl("rpm")
        CustomRepositorySettingsImpl repositorySettings = new CustomRepositorySettingsImpl(customPackageType)
        repositorySettings.setRepoLayout(RpmRepositorySettingsImpl.defaultLayout)
        return repositorySettings
    }

    @BeforeMethod
    protected void setUp() {
        someCalculateYumMetadata = true
        someGroupFileNames = "groups.xml"
        someListRemoteFolderItems = true
        someYumRootDepth = 0

        customProperties = [
                "calculateYumMetadata" : someCalculateYumMetadata,
                "groupFileNames"       : someGroupFileNames,
                "yumRootDepth"         : someYumRootDepth,

                // remote
                "listRemoteFolderItems": someListRemoteFolderItems
        ] as Map<String, Object>
        super.setUp()
    }

    @Test(groups = "rpmPackageTypeRepo")
    void testRpmLocalRepo() {
        artifactory.repositories().create(0, localRepo)

        def resp = artifactory.repository(localRepo.getKey()).get()
        assertThat(resp, CoreMatchers.notNullValue())
        assertThat(resp.repoLayoutRef, CoreMatchers.is(RpmRepositorySettingsImpl.defaultLayout))
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(PackageTypeImpl.rpm))
            assertThat(repoLayout, CoreMatchers.is(RpmRepositorySettingsImpl.defaultLayout))

            // local
            assertThat(calculateYumMetadata, CoreMatchers.is(someCalculateYumMetadata))
            assertThat(groupFileNames, CoreMatchers.is(CoreMatchers.nullValue()))
            assertThat(yumRootDepth, CoreMatchers.is(someYumRootDepth))

            // remote
            assertThat(listRemoteFolderItems, CoreMatchers.is(CoreMatchers.nullValue()))
        }
    }

    @Test(groups = "rpmPackageTypeRepo")
    void testRpmRemoteRepo() {
        artifactory.repositories().create(0, remoteRepo)

        def resp = artifactory.repository(remoteRepo.getKey()).get()
        assertThat(resp, CoreMatchers.notNullValue())
        assertThat(resp.repoLayoutRef, CoreMatchers.is(RpmRepositorySettingsImpl.defaultLayout))
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(PackageTypeImpl.rpm))
            assertThat(repoLayout, CoreMatchers.is(RpmRepositorySettingsImpl.defaultLayout))

            // remote
            assertThat(listRemoteFolderItems, CoreMatchers.is(someListRemoteFolderItems))

            // local
            assertThat(calculateYumMetadata, CoreMatchers.is(CoreMatchers.nullValue()))
            assertThat(groupFileNames, CoreMatchers.is(CoreMatchers.nullValue()))
            assertThat(yumRootDepth, CoreMatchers.is(CoreMatchers.nullValue()))
        }
    }

    @Test(groups = "rpmPackageTypeRepo")
    void testRpnVirtualRepo() {
        artifactory.repositories().create(0, virtualRepo)

        def resp = artifactory.repository(virtualRepo.getKey()).get()
        assertThat(resp, CoreMatchers.notNullValue())
        assertThat(resp.repoLayoutRef, CoreMatchers.is(RpmRepositorySettingsImpl.defaultLayout))
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(PackageTypeImpl.rpm))
            assertThat(repoLayout, CoreMatchers.is(RpmRepositorySettingsImpl.defaultLayout))

            // local
            assertThat(calculateYumMetadata, CoreMatchers.is(CoreMatchers.nullValue()))
            assertThat(groupFileNames, CoreMatchers.is(CoreMatchers.nullValue()))
            assertThat(yumRootDepth, CoreMatchers.is(CoreMatchers.nullValue()))

            // remote
            assertThat(listRemoteFolderItems, CoreMatchers.is(CoreMatchers.nullValue()))
        }
    }
}
