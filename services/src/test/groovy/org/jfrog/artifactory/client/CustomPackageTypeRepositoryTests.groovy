package org.jfrog.artifactory.client

import org.hamcrest.CoreMatchers
import org.jfrog.artifactory.client.model.RepositoryType
import org.jfrog.artifactory.client.model.impl.CustomPackageTypeImpl
import org.jfrog.artifactory.client.model.impl.PackageTypeImpl
import org.jfrog.artifactory.client.model.repository.settings.RepositorySettings
import org.jfrog.artifactory.client.model.repository.settings.impl.CustomRepositorySettingsImpl
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

class CustomPackageTypeRepositoryTests extends BaseRepositoryTests {

    private boolean someCalculateYumMetadata
    private String someGroupFileNames
    private boolean someListRemoteFolderItems
    private int someYumRootDepth

    @Override
    RepositorySettings getRepositorySettings(RepositoryType repositoryType) {
        CustomPackageTypeImpl customPackageType = new CustomPackageTypeImpl("rpm")
        return new CustomRepositorySettingsImpl(customPackageType)
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
        ]
        super.setUp()
    }

    @Test(groups = "rpmPackageTypeRepo")
    void testRpmLocalRepo() {
        artifactory.repositories().create(0, localRepo)

        def resp = artifactory.repository(localRepo.getKey()).get()
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(PackageTypeImpl.rpm))

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
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(PackageTypeImpl.rpm))

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
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(PackageTypeImpl.rpm))

            // local
            assertThat(calculateYumMetadata, CoreMatchers.is(CoreMatchers.nullValue()))
            assertThat(groupFileNames, CoreMatchers.is(CoreMatchers.nullValue()))
            assertThat(yumRootDepth, CoreMatchers.is(CoreMatchers.nullValue()))

            // remote
            assertThat(listRemoteFolderItems, CoreMatchers.is(CoreMatchers.nullValue()))
        }
    }
}
