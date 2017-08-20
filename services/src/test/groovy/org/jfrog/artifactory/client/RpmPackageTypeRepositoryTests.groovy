package org.jfrog.artifactory.client

import org.hamcrest.CoreMatchers
import org.jfrog.artifactory.client.model.RepositoryType
import org.jfrog.artifactory.client.model.repository.settings.RepositorySettings
import org.jfrog.artifactory.client.model.repository.settings.impl.RpmRepositorySettingsImpl
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

/**
 * test that client correctly sends and receives repository configuration with `rpm` package type
 *
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
public class RpmPackageTypeRepositoryTests extends BaseRepositoryTests {

    @Override
    RepositorySettings getRepositorySettings(RepositoryType repositoryType) {
        def settings = new RpmRepositorySettingsImpl()

        settings.with {
            // local
            calculateYumMetadata = rnd.nextBoolean()
            enableFileListsIndexing = rnd.nextBoolean()
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

    @Test(groups = "rpmPackageTypeRepo")
    public void testRpmLocalRepo() {
        artifactory.repositories().create(0, localRepo)
        def expectedSettings = localRepo.repositorySettings

        def resp = artifactory.repository(localRepo.getKey()).get()
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(expectedSettings.getPackageType()))

            // local
            assertThat(calculateYumMetadata, CoreMatchers.is(expectedSettings.getCalculateYumMetadata()))
            assertThat(enableFileListsIndexing, CoreMatchers.is(expectedSettings.getEnableFileListsIndexing()))
            // TODO: property is not returned by the artifactory
            // assertThat(groupFileNames, CoreMatchers.is(specRepo.getGroupFileNames()))
            assertThat(groupFileNames, CoreMatchers.is(CoreMatchers.nullValue()))
            assertThat(yumRootDepth, CoreMatchers.is(expectedSettings.getYumRootDepth()))

            // remote
            assertThat(listRemoteFolderItems, CoreMatchers.is(CoreMatchers.nullValue()))
        }
    }

    @Test(groups = "rpmPackageTypeRepo")
    public void testRpmRemoteRepo() {
        artifactory.repositories().create(0, remoteRepo)
        def expectedSettings = remoteRepo.repositorySettings

        def resp = artifactory.repository(remoteRepo.getKey()).get()
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(expectedSettings.getPackageType()))

            // remote
            assertThat(listRemoteFolderItems, CoreMatchers.is(expectedSettings.getListRemoteFolderItems()))

            // local
            assertThat(enableFileListsIndexing, CoreMatchers.is(CoreMatchers.nullValue()))
            assertThat(calculateYumMetadata, CoreMatchers.is(CoreMatchers.nullValue()))
            assertThat(groupFileNames, CoreMatchers.is(CoreMatchers.nullValue()))
            assertThat(yumRootDepth, CoreMatchers.is(CoreMatchers.nullValue()))
        }
    }
}
