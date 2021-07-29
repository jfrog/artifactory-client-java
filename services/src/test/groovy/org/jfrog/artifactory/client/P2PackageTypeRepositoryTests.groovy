package org.jfrog.artifactory.client

import org.hamcrest.CoreMatchers
import org.jfrog.artifactory.client.model.RepositoryType
import org.jfrog.artifactory.client.model.impl.LocalRepoChecksumPolicyTypeImpl
import org.jfrog.artifactory.client.model.impl.RemoteRepoChecksumPolicyTypeImpl
import org.jfrog.artifactory.client.model.impl.RepositoryTypeImpl
import org.jfrog.artifactory.client.model.impl.SnapshotVersionBehaviorImpl
import org.jfrog.artifactory.client.model.repository.PomCleanupPolicy
import org.jfrog.artifactory.client.model.repository.settings.RepositorySettings
import org.jfrog.artifactory.client.model.repository.settings.impl.P2RepositorySettingsImpl
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

/**
 * test that client correctly sends and receives repository configuration with `p2` package type
 *
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
class P2PackageTypeRepositoryTests extends BaseRepositoryTests {

    P2PackageTypeRepositoryTests() {
        remoteRepoUrl = "https://download.eclipse.org/releases/photon"
    }

    @Override
    RepositorySettings getRepositorySettings(RepositoryType repositoryType) {
        if (repositoryType == RepositoryTypeImpl.LOCAL) {
            return null
        }

        def settings = new P2RepositorySettingsImpl()

        settings.with {
            // local
            checksumPolicyType = LocalRepoChecksumPolicyTypeImpl.values()[rnd.nextInt(LocalRepoChecksumPolicyTypeImpl.values().length)]
            handleReleases = true
            handleSnapshots = true
            maxUniqueSnapshots = rnd.nextInt()
            snapshotVersionBehavior = SnapshotVersionBehaviorImpl.values()[rnd.nextInt(SnapshotVersionBehaviorImpl.values().length)]
            suppressPomConsistencyChecks = true

            // remote
            fetchJarsEagerly = true
            fetchSourcesEagerly = true
            listRemoteFolderItems = true
            rejectInvalidJars = true
            remoteRepoChecksumPolicyType = RemoteRepoChecksumPolicyTypeImpl.ignore_and_generate

            // virtual
            keyPair // no key pairs configured
            pomRepositoryReferencesCleanupPolicy = PomCleanupPolicy.values()[rnd.nextInt(PomCleanupPolicy.values().length)]
        }

        return settings
    }

    @BeforeMethod
    protected void setUp() {
        // only remote ant virtual repository is supported
        prepareLocalRepo = false

        super.setUp()
    }

    @Test(groups = "p2PackageTypeRepo")
    void testP2RemoteRepo() {
        artifactory.repositories().create(0, remoteRepo)
        def expectedSettings = remoteRepo.repositorySettings

        def resp = artifactory.repository(remoteRepo.getKey()).get()
        assertThat(resp, CoreMatchers.notNullValue())
        assertThat(resp.repoLayoutRef, CoreMatchers.is(P2RepositorySettingsImpl.defaultLayout))
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(expectedSettings.getPackageType()))
            assertThat(repoLayout, CoreMatchers.is(expectedSettings.getRepoLayout()))

            // local
            assertThat(checksumPolicyType, CoreMatchers.nullValue())
            // always in resp payload
            assertThat(handleReleases, CoreMatchers.is(expectedSettings.getHandleReleases()))
            assertThat(maxUniqueSnapshots, CoreMatchers.is(expectedSettings.getMaxUniqueSnapshots()))
            assertThat(maxUniqueSnapshots, CoreMatchers.is(expectedSettings.getMaxUniqueSnapshots()))
            assertThat(snapshotVersionBehavior, CoreMatchers.nullValue())
            assertThat(suppressPomConsistencyChecks, CoreMatchers.is(expectedSettings.getSuppressPomConsistencyChecks()))
            // always sent by artifactory

            // remote
            assertThat(fetchJarsEagerly, CoreMatchers.is(expectedSettings.getFetchJarsEagerly()))
            assertThat(fetchSourcesEagerly, CoreMatchers.is(expectedSettings.getFetchSourcesEagerly()))
            assertThat(listRemoteFolderItems, CoreMatchers.is(expectedSettings.getListRemoteFolderItems()))
            assertThat(rejectInvalidJars, CoreMatchers.is(expectedSettings.getRejectInvalidJars()))
            assertThat(remoteRepoChecksumPolicyType, CoreMatchers.is(RemoteRepoChecksumPolicyTypeImpl.ignore_and_generate))

            // virtual
            assertThat(keyPair, CoreMatchers.nullValue())
            assertThat(pomRepositoryReferencesCleanupPolicy, CoreMatchers.nullValue())
        }
    }

    @Test(groups = "p2PackageTypeRepo")
    void testP2VirtualRepo() {
        artifactory.repositories().create(0, virtualRepo)
        def expectedSettings = virtualRepo.repositorySettings

        def resp = artifactory.repository(virtualRepo.getKey()).get()
        assertThat(resp, CoreMatchers.notNullValue())
        assertThat(resp.repoLayoutRef, CoreMatchers.is(P2RepositorySettingsImpl.defaultLayout))
        resp.getRepositorySettings().with {
            assertThat(packageType, CoreMatchers.is(expectedSettings.getPackageType()))
            assertThat(repoLayout, CoreMatchers.is(expectedSettings.getRepoLayout()))

            // local
            assertThat(checksumPolicyType, CoreMatchers.nullValue())
            assertThat(handleReleases, CoreMatchers.nullValue())
            assertThat(handleSnapshots, CoreMatchers.nullValue())
            assertThat(maxUniqueSnapshots, CoreMatchers.nullValue())
            assertThat(snapshotVersionBehavior, CoreMatchers.nullValue())
            assertThat(suppressPomConsistencyChecks, CoreMatchers.nullValue())

            // remote
            assertThat(fetchJarsEagerly, CoreMatchers.nullValue())
            assertThat(fetchSourcesEagerly, CoreMatchers.nullValue())
            assertThat(listRemoteFolderItems, CoreMatchers.nullValue())
            assertThat(rejectInvalidJars, CoreMatchers.nullValue())
            assertThat(remoteRepoChecksumPolicyType, CoreMatchers.nullValue())

            // virtual
            assertThat(keyPair, CoreMatchers.is('')) // empty = keyPair is not set
            assertThat(pomRepositoryReferencesCleanupPolicy, CoreMatchers.is(expectedSettings.getPomRepositoryReferencesCleanupPolicy()))
            // always sent by artifactory
        }
    }
}
