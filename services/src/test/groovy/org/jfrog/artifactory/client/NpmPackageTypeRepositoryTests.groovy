package org.jfrog.artifactory.client

import org.hamcrest.CoreMatchers
import org.jfrog.artifactory.client.model.*
import org.jfrog.artifactory.client.model.repository.settings.impl.NpmRepositorySettingsImpl
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

/**
 * test that client correctly sends and receives repository configuration with `npm` package type
 * 
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
public class NpmPackageTypeRepositoryTests extends BaseRepositoryTests {

    @BeforeMethod
    protected void setUp() {
        settings  = new NpmRepositorySettingsImpl()

        settings.with {
            // remote
            listRemoteFolderItems = rnd.nextBoolean()

            // virtual
            externalDependenciesEnabled = rnd.nextBoolean()
            externalDependenciesPatterns = Collections.singletonList("org/${rnd.nextInt()}/**".toString())
            // TODO: skip, we have to add some remote bower repo to set this property
            externalDependenciesRemoteRepo
        }

        super.setUp()
    }

    @Test(groups = "npmPackageTypeRepo")
    public void testNpmLocalRepo() {
        artifactory.repositories().create(0, localRepo)

        def resp = artifactory.repository(localRepo.getKey()).get()
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(settings.getPackageType()))

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

        def resp = artifactory.repository(remoteRepo.getKey()).get()
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(settings.getPackageType()))

            // remote
            assertThat(listRemoteFolderItems, CoreMatchers.is(settings.getListRemoteFolderItems()))

            // virtual
            assertThat(externalDependenciesEnabled, CoreMatchers.nullValue())
            assertThat(externalDependenciesPatterns, CoreMatchers.nullValue())
            assertThat(externalDependenciesRemoteRepo, CoreMatchers.nullValue())
        }
    }

    @Test(groups = "npmPackageTypeRepo")
    public void testNpmVirtualRepo() {
        artifactory.repositories().create(0, virtualRepo)

        def resp = artifactory.repository(virtualRepo.getKey()).get()
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(settings.getPackageType()))

            // remote
            assertThat(listRemoteFolderItems, CoreMatchers.nullValue())

            // virtual
            assertThat(externalDependenciesEnabled, CoreMatchers.is(settings.getExternalDependenciesEnabled()))
            assertThat(externalDependenciesPatterns, CoreMatchers.is(settings.getExternalDependenciesPatterns()))
            assertThat(externalDependenciesRemoteRepo, CoreMatchers.is(settings.getExternalDependenciesRemoteRepo()))
        }
    }
}
