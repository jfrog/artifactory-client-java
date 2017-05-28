package org.jfrog.artifactory.client

import org.hamcrest.CoreMatchers
import org.jfrog.artifactory.client.model.PackageType
import org.jfrog.artifactory.client.model.Version
import org.jfrog.artifactory.client.model.repository.settings.impl.YumRepositorySettingsImpl
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

/**
 * test that client correctly sends and receives repository configuration with `yum` package type
 *
 * @author Yahav Itzhak (yahavi@jfrog.com)
 */
public class YumPackageTypeRepositoryTests extends BaseRepositoryTests {

    private static String YUM_REPO_DEPRECATION_VERSION = "5.0.0"
    private PackageType expectedPackageType

    @BeforeMethod
    protected void setUp() {
        settings = new YumRepositorySettingsImpl()

        settings.with {
            // local
            calculateYumMetadata = rnd.nextBoolean()
            groupFileNames = "groups-${rnd.nextInt()}.xml"
            listRemoteFolderItems = rnd.nextBoolean()

            // remote
            yumRootDepth = rnd.nextInt()
        }

        // only local and remote repository supported
        prepareVirtualRepo = false

        super.setUp()

        expectedPackageType = getExpectedPackageType()
    }

    @Test(groups = "yumPackageTypeRepo")
    public void testYumLocalRepo() {
        artifactory.repositories().create(0, localRepo)

        def resp = artifactory.repository(localRepo.getKey()).get()
        resp.getRepositorySettings().with {

            assertThat(packageType, CoreMatchers.is(expectedPackageType))

            // local
            assertThat(calculateYumMetadata, CoreMatchers.is(settings.getCalculateYumMetadata()))
            // TODO: property is not returned by the artifactory
            // assertThat(groupFileNames, CoreMatchers.is(specRepo.getGroupFileNames()))
            assertThat(groupFileNames, CoreMatchers.is(CoreMatchers.nullValue()))
            assertThat(yumRootDepth, CoreMatchers.is(settings.getYumRootDepth()))

            // remote
            assertThat(listRemoteFolderItems, CoreMatchers.is(CoreMatchers.nullValue()))
        }
    }

    @Test(groups = "yumPackageTypeRepo")
    public void testYumRemoteRepo() {
        artifactory.repositories().create(0, remoteRepo)

        def resp = artifactory.repository(remoteRepo.getKey()).get()
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(expectedPackageType))

            // remote
            assertThat(listRemoteFolderItems, CoreMatchers.is(settings.getListRemoteFolderItems()))

            // local
            assertThat(calculateYumMetadata, CoreMatchers.is(CoreMatchers.nullValue()))
            assertThat(groupFileNames, CoreMatchers.is(CoreMatchers.nullValue()))
            assertThat(yumRootDepth, CoreMatchers.is(CoreMatchers.nullValue()))
        }
    }

    private PackageType getExpectedPackageType() {
        Version version = artifactory.system().version()
        return version.isAtLeast(YUM_REPO_DEPRECATION_VERSION) ? PackageType.rpm : settings.getPackageType()
    }

}
