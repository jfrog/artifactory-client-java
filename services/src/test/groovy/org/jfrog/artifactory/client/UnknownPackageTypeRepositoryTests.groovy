package org.jfrog.artifactory.client

import org.hamcrest.CoreMatchers
import org.jfrog.artifactory.client.model.PackageType
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

class UnknownPackageTypeRepositoryTests extends BaseRepositoryTests {

    def somePackageType
    def someCalculateYumMetadata
    def someGroupFileNames
    def someListRemoteFolderItems
    def someYumRootDepth

    @BeforeMethod
    protected void setUp() {
        settings = null

        somePackageType = "rpm"
        someCalculateYumMetadata = rnd.nextBoolean()
        someGroupFileNames = "groups-${rnd.nextInt()}.xml"
        someListRemoteFolderItems = rnd.nextBoolean()
        someYumRootDepth = rnd.nextInt()

        otherProperties = [
            "packageType" : somePackageType,
            "calculateYumMetadata" : someCalculateYumMetadata,
            "groupFileNames" : someGroupFileNames ,
            "yumRootDepth" : someYumRootDepth,

            // remote
            "listRemoteFolderItems" : someListRemoteFolderItems
        ]
        super.setUp()
    }

    @Test(groups = "rpmPackageTypeRepo")
    void testRpmLocalRepo() {
        artifactory.repositories().create(0, localRepo)

        def resp = artifactory.repository(localRepo.getKey()).get()
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(PackageType.rpm))

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
            assertThat(packageType, CoreMatchers.is(PackageType.rpm))

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
            assertThat(packageType, CoreMatchers.is(PackageType.rpm))

            // local
            assertThat(calculateYumMetadata, CoreMatchers.is(CoreMatchers.nullValue()))
            assertThat(groupFileNames, CoreMatchers.is(CoreMatchers.nullValue()))
            assertThat(yumRootDepth, CoreMatchers.is(CoreMatchers.nullValue()))

            // remote
            assertThat(listRemoteFolderItems, CoreMatchers.is(CoreMatchers.nullValue()))
        }
    }
}
