package org.jfrog.artifactory.client

import org.hamcrest.CoreMatchers
import org.jfrog.artifactory.client.model.RepositoryType
import org.jfrog.artifactory.client.model.repository.settings.RepositorySettings
import org.jfrog.artifactory.client.model.repository.settings.impl.CargoRepositorySettingsImpl
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

class CargoPackageTypeRepositoryTests extends BaseRepositoryTests {

    CargoPackageTypeRepositoryTests() {
        remoteRepoUrl = "https://index.crates.io"
    }

    @Override
    RepositorySettings getRepositorySettings(RepositoryType repositoryType) {
        def settings = new CargoRepositorySettingsImpl()

        settings.with {
            cargoAnonymousAccess = rnd.nextBoolean()
            // cargoInternalIndex and gitRegistryUrl were removed because Artifactory blocked
            // legacy Git index support for all Cargo repository types (local, remote, federated)
            // as of Artifactory 7.46.3. Sending cargoInternalIndex=true or a gitRegistryUrl now
            // returns HTTP 400: "the legacy Git index is no longer supported. Use the default
            // sparse HTTP index instead." All Cargo repositories must use the sparse HTTP index.
            // See: https://jfrog.com/help/r/jfrog-artifactory-documentation/cargo-repositories
        }

        return settings
    }

    @BeforeMethod
    protected void setUp() {
        prepareVirtualRepo = false
        super.setUp()
    }

    @Test(groups = "cargoPackageTypeRepo")
    void testCargoLocalRepo() {
        artifactory.repositories().create(0, localRepo)
        def expectedSettings = localRepo.repositorySettings

        def resp = artifactory.repository(localRepo.getKey()).get()
        assertThat(resp, CoreMatchers.notNullValue())
        assertThat(resp.repoLayoutRef, CoreMatchers.is(CargoRepositorySettingsImpl.defaultLayout))
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(expectedSettings.getPackageType()))
            assertThat(repoLayout, CoreMatchers.is(expectedSettings.getRepoLayout()))
            assertThat(cargoAnonymousAccess, CoreMatchers.is(expectedSettings.cargoAnonymousAccess))
        }
    }

    @Test(groups = "cargoPackageTypeRepo")
    void testCargoFederatedRepo() {
        artifactory.repositories().create(0, federatedRepo)
        def expectedSettings = federatedRepo.repositorySettings

        def resp = artifactory.repository(federatedRepo.getKey()).get()
        assertThat(resp, CoreMatchers.notNullValue())
        assertThat(resp.repoLayoutRef, CoreMatchers.is(CargoRepositorySettingsImpl.defaultLayout))
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(expectedSettings.getPackageType()))
            assertThat(repoLayout, CoreMatchers.is(expectedSettings.getRepoLayout()))
            assertThat(cargoAnonymousAccess, CoreMatchers.is(expectedSettings.cargoAnonymousAccess))
        }
    }

    @Test(groups = "cargoPackageTypeRepo")
    void testCargoRemoteRepo() {
        artifactory.repositories().create(0, remoteRepo)
        def expectedSettings = remoteRepo.repositorySettings

        def resp = artifactory.repository(remoteRepo.getKey()).get()
        assertThat(resp, CoreMatchers.notNullValue())
        assertThat(resp.repoLayoutRef, CoreMatchers.is(CargoRepositorySettingsImpl.defaultLayout))
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(expectedSettings.getPackageType()))
            assertThat(repoLayout, CoreMatchers.is(expectedSettings.getRepoLayout()))
            assertThat(cargoAnonymousAccess, CoreMatchers.is(expectedSettings.cargoAnonymousAccess))
        }
    }
}