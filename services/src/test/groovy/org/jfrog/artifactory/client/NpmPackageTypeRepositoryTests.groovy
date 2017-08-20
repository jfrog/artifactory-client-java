package org.jfrog.artifactory.client

import org.hamcrest.CoreMatchers
import org.jfrog.artifactory.client.model.RepositoryType
import org.jfrog.artifactory.client.model.repository.settings.RepositorySettings
import org.jfrog.artifactory.client.model.repository.settings.impl.NpmRepositorySettingsImpl
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

/**
 * test that client correctly sends and receives repository configuration with `npm` package type
 *
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
public class NpmPackageTypeRepositoryTests extends BaseRepositoryTests {

    @Override
    RepositorySettings getRepositorySettings(RepositoryType repositoryType) {
        def settings = new NpmRepositorySettingsImpl()

        settings.with {
            // remote
            listRemoteFolderItems = rnd.nextBoolean()

            // virtual
            externalDependenciesEnabled = rnd.nextBoolean()
            externalDependenciesPatterns = Collections.singletonList("org/${rnd.nextInt()}/**".toString())
            // TODO: skip, we have to add some remote bower repo to set this property
            externalDependenciesRemoteRepo
        }

        return settings
    }

    @BeforeMethod
    protected void setUp() {
        super.setUp()
    }

    @Test(groups = "npmPackageTypeRepo")
    public void testNpmLocalRepo() {
        artifactory.repositories().create(0, localRepo)
        def expectedSettings = localRepo.repositorySettings

        def resp = artifactory.repository(localRepo.getKey()).get()
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(expectedSettings.getPackageType()))

            // remote
            assertThat(listRemoteFolderItems, CoreMatchers.nullValue())

            // virtual
            assertThat(externalDependenciesEnabled, CoreMatchers.nullValue())
            assertThat(externalDependenciesPatterns, CoreMatchers.nullValue())
            assertThat(externalDependenciesRemoteRepo, CoreMatchers.nullValue())
        }
    }

    @Test(groups = "npmPackageTypeRepo")
    public void testNpmRemoteRepo() {
        artifactory.repositories().create(0, remoteRepo)
        def expectedSettings = remoteRepo.repositorySettings

        def resp = artifactory.repository(remoteRepo.getKey()).get()
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(expectedSettings.getPackageType()))

            // remote
            assertThat(listRemoteFolderItems, CoreMatchers.is(expectedSettings.getListRemoteFolderItems()))

            // virtual
            assertThat(externalDependenciesEnabled, CoreMatchers.nullValue())
            assertThat(externalDependenciesPatterns, CoreMatchers.nullValue())
            assertThat(externalDependenciesRemoteRepo, CoreMatchers.nullValue())
        }
    }

    @Test(groups = "npmPackageTypeRepo")
    public void testNpmVirtualRepo() {
        artifactory.repositories().create(0, virtualRepo)
        def expectedSettings = virtualRepo.repositorySettings

        def resp = artifactory.repository(virtualRepo.getKey()).get()
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(expectedSettings.getPackageType()))

            // remote
            assertThat(listRemoteFolderItems, CoreMatchers.nullValue())

            // virtual
            assertThat(externalDependenciesEnabled, CoreMatchers.is(expectedSettings.getExternalDependenciesEnabled()))
            assertThat(externalDependenciesPatterns, CoreMatchers.is(expectedSettings.getExternalDependenciesPatterns()))
            assertThat(externalDependenciesRemoteRepo, CoreMatchers.is(expectedSettings.getExternalDependenciesRemoteRepo()))
        }
    }
}
