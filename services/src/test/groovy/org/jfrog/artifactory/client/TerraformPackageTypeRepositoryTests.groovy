package org.jfrog.artifactory.client

import org.hamcrest.CoreMatchers
import org.jfrog.artifactory.client.model.RepositoryType
import org.jfrog.artifactory.client.model.impl.RepositoryTypeImpl
import org.jfrog.artifactory.client.model.repository.settings.RepositorySettings
import org.jfrog.artifactory.client.model.repository.settings.TerraformRepositorySettings
import org.jfrog.artifactory.client.model.repository.settings.impl.TerraformRepositorySettingsImpl
import org.jfrog.artifactory.client.model.repository.settings.vcs.VcsGitProvider
import org.jfrog.artifactory.client.model.repository.settings.vcs.VcsType
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

class TerraformPackageTypeRepositoryTests extends BaseRepositoryTests {

    TerraformPackageTypeRepositoryTests() {
        remoteRepoUrl = "https://github.com"
        storeArtifactsLocallyInRemoteRepo = true
    }

    @Override
    RepositorySettings getRepositorySettings(RepositoryType repositoryType) {
        def settings = new TerraformRepositorySettingsImpl()

        switch (repositoryType) {
            case RepositoryTypeImpl.REMOTE:
                settings.with {
                    repoLayout = defaultLayout
                    vcsType = VcsType.GIT
                    vcsGitProvider = VcsGitProvider.GITHUB
                    terraformRegistryUrl = "https://registry.terraform.io"
                    terraformProvidersUrl = "https://releases.hashicorp.com"
                    remoteRepoLayoutRef = defaultLayout
                }
                break
            case RepositoryTypeImpl.VIRTUAL:
                settings.with {
                    repoLayout = moduleLayout
                }
                break
            case RepositoryTypeImpl.FEDERATED:
                settings.with {
                    terraformType = TerraformRepositorySettings.TerraformType.module
                    repoLayout = moduleLayout
                }
                break
            default:
                settings = null
        }

        return settings

    }

    @BeforeMethod
    protected void setUp() {
        super.setUp()
    }

    @Test(groups = "terraformPackageTypeRepo")
    void testTerraformLocalRepoModule() {
        def mySettings = new TerraformRepositorySettingsImpl()
        mySettings.with {
            terraformType = TerraformRepositorySettings.TerraformType.module
            repoLayout = moduleLayout
        }

        testLocalRepoWithSettings(mySettings)
    }

    @Test(groups = "terraformPackageTypeRepo")
    void testTerraformLocalRepoProvider() {
        def mySettings = new TerraformRepositorySettingsImpl()
        mySettings.with {
            terraformType = TerraformRepositorySettings.TerraformType.provider
            repoLayout = providerLayout
        }

        testLocalRepoWithSettings(mySettings)
    }

    @Test(groups = "terraformPackageTypeRepo")
    void testTerraformRemoteRepo() {
        artifactory.repositories().create(0, remoteRepo)
        def expectedSettings = remoteRepo.repositorySettings

        def resp = artifactory.repository(remoteRepo.getKey()).get()
        assertThat(resp, CoreMatchers.notNullValue())
        assertThat(resp.repoLayoutRef, CoreMatchers.is(expectedSettings.repoLayout))
        assertThat(resp.url, CoreMatchers.is(remoteRepoUrl))
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(expectedSettings.getPackageType()))

            // remote
            assertThat(vcsType, CoreMatchers.is(expectedSettings.vcsType))
            assertThat(vcsGitProvider, CoreMatchers.is(expectedSettings.vcsGitProvider))
            assertThat(terraformRegistryUrl, CoreMatchers.is(expectedSettings.terraformRegistryUrl))
            assertThat(terraformProvidersUrl, CoreMatchers.is(expectedSettings.terraformProvidersUrl))
            assertThat(remoteRepoLayoutRef, CoreMatchers.is(expectedSettings.remoteRepoLayoutRef))
        }
    }

    @Test(groups = "terraformPackageTypeRepo")
    void testTerraformVirtualRepo() {
        artifactory.repositories().create(0, virtualRepo)
        def expectedSettings = virtualRepo.repositorySettings

        def resp = artifactory.repository(virtualRepo.getKey()).get()
        assertThat(resp, CoreMatchers.notNullValue())
        assertThat(resp.repoLayoutRef, CoreMatchers.is(expectedSettings.repoLayout))

        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(expectedSettings.getPackageType()))
        }
    }

    @Test(groups = "terraformPackageTypeRepo")
    void testTerraformFederatedRepo() {
        artifactory.repositories().create(0, federatedRepo)
        def expectedSettings = federatedRepo.repositorySettings

        def resp = artifactory.repository(federatedRepo.getKey()).get()
        assertThat(resp, CoreMatchers.notNullValue())
        assertThat(resp.repoLayoutRef, CoreMatchers.is(expectedSettings.repoLayout))

        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(expectedSettings.getPackageType()))
        }
    }

    private void testLocalRepoWithSettings(TerraformRepositorySettingsImpl mySettings) {
        String id = Long.toString(repoUniqueId)
        def myLocalRepo = artifactory.repositories().builders().localRepositoryBuilder()
                .key("$REPO_NAME_PREFIX-local-$id")
                .description("local-$id")
                .notes("notes-${rnd.nextInt()}")
                .archiveBrowsingEnabled(rnd.nextBoolean())
                .blackedOut(rnd.nextBoolean())
                .excludesPattern("org/${rnd.nextInt()}/**")
                .includesPattern("org/${rnd.nextInt()}/**")
                .propertySets(Collections.emptyList()) // no property sets configured
                .repositorySettings(mySettings)
                .xraySettings(xraySettings)
                .customProperties(customProperties)
                .build()
        artifactory.repositories().create(0, myLocalRepo)
        def expectedSettings = myLocalRepo.repositorySettings

        def resp = artifactory.repository(myLocalRepo.getKey()).get()
        assertThat(resp, CoreMatchers.notNullValue())
        assertThat(resp.repoLayoutRef, CoreMatchers.is(expectedSettings.repoLayout))

        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(expectedSettings.packageType))
            assertThat(terraformType, CoreMatchers.is(expectedSettings.terraformType))
        }
    }
}
