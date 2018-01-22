package org.jfrog.artifactory.client

import org.hamcrest.CoreMatchers
import org.jfrog.artifactory.client.model.RepositoryType
import org.jfrog.artifactory.client.model.impl.RepositoryTypeImpl
import org.jfrog.artifactory.client.model.repository.settings.RepositorySettings
import org.jfrog.artifactory.client.model.repository.settings.impl.GenericRepositorySettingsImpl
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

import static org.testng.Assert.assertTrue

/**
 * test that client correctly sends and receives general repository configuration
 * 
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
public class GeneralRepositoryTests extends BaseRepositoryTests {

    @Override
    RepositorySettings getRepositorySettings(RepositoryType repositoryType) {
        return new GenericRepositorySettingsImpl()
    }

    @BeforeMethod
    protected void setUp() {
        super.setUp()
    }

    @Test(groups = "generalRepo")
    public void testLocalRepo() {
        artifactory.repositories().create(0, localRepo)
        assertTrue(curl(LIST_PATH).contains(localRepo.getKey()))

        def resp = artifactory.repository(localRepo.getKey()).get()
        assertThat(resp, CoreMatchers.notNullValue())
        resp.with {
            assertThat(rclass, CoreMatchers.is(RepositoryTypeImpl.LOCAL))
            assertThat(key, CoreMatchers.is(localRepo.getKey()))
            assertThat(description, CoreMatchers.is(localRepo.getDescription()))
            assertThat(notes, CoreMatchers.is(localRepo.getNotes()))
            assertThat(archiveBrowsingEnabled, CoreMatchers.is(localRepo.isArchiveBrowsingEnabled()))
            assertThat(blackedOut, CoreMatchers.is(localRepo.isBlackedOut()))
            assertThat(excludesPattern, CoreMatchers.is(localRepo.getExcludesPattern()))
            assertThat(includesPattern, CoreMatchers.is(localRepo.getIncludesPattern()))
            assertThat(propertySets, CoreMatchers.is(localRepo.getPropertySets()))
            assertThat(repoLayoutRef, CoreMatchers.is(localRepo.getRepoLayoutRef()))
        }
    }

    @Test(groups = "generalRepo")
    public void testRemoteRepo() {
        artifactory.repositories().create(0, remoteRepo)
        assertTrue(curl(LIST_PATH).contains(remoteRepo.getKey()))

        def resp = artifactory.repository(remoteRepo.getKey()).get()
        assertThat(resp, CoreMatchers.notNullValue())
        resp.with {
            assertThat(rclass, CoreMatchers.is(RepositoryTypeImpl.REMOTE))
            assertThat(key, CoreMatchers.is(remoteRepo.getKey()))
            assertThat(description.startsWith(remoteRepo.getDescription()), CoreMatchers.is(true))
            assertThat(notes, CoreMatchers.is(remoteRepo.getNotes()))
            assertThat(allowAnyHostAuth, CoreMatchers.is(remoteRepo.isAllowAnyHostAuth()))
            assertThat(archiveBrowsingEnabled, CoreMatchers.is(remoteRepo.isArchiveBrowsingEnabled()))
            assertThat(assumedOfflinePeriodSecs, CoreMatchers.is(remoteRepo.getAssumedOfflinePeriodSecs()))
            assertThat(enableCookieManagement, CoreMatchers.is(remoteRepo.isEnableCookieManagement()))
            assertThat(excludesPattern, CoreMatchers.is(remoteRepo.getExcludesPattern()))
            assertThat(hardFail, CoreMatchers.is(remoteRepo.isHardFail()))
            assertThat(includesPattern, CoreMatchers.is(remoteRepo.getIncludesPattern()))
            assertThat(localAddress, CoreMatchers.is(remoteRepo.getLocalAddress()))
            assertThat(missedRetrievalCachePeriodSecs, CoreMatchers.is(remoteRepo.getMissedRetrievalCachePeriodSecs()))
            assertThat(offline, CoreMatchers.is(remoteRepo.isOffline()))
            assertThat(propertySets, CoreMatchers.is(remoteRepo.getPropertySets()))
            assertThat(proxy, CoreMatchers.is(remoteRepo.getProxy()))
            assertThat(repoLayoutRef, CoreMatchers.is(remoteRepo.getRepoLayoutRef()))
            assertThat(retrievalCachePeriodSecs, CoreMatchers.is(remoteRepo.getRetrievalCachePeriodSecs()))
            assertThat(shareConfiguration, CoreMatchers.is(remoteRepo.isShareConfiguration()))
            assertThat(socketTimeoutMillis, CoreMatchers.is(remoteRepo.getSocketTimeoutMillis()))
            assertThat(storeArtifactsLocally, CoreMatchers.is(remoteRepo.isStoreArtifactsLocally()))
            assertThat(synchronizeProperties, CoreMatchers.is(remoteRepo.isSynchronizeProperties()))
            assertThat(unusedArtifactsCleanupPeriodHours, CoreMatchers.is(remoteRepo.getUnusedArtifactsCleanupPeriodHours()))
            assertThat(url, CoreMatchers.is(remoteRepo.getUrl()))
            assertThat(username, CoreMatchers.is(remoteRepo.getUsername()))
        }
    }

    @Test(groups = "generalRepo")
    public void testVirtualRepo() {
        artifactory.repositories().create(0, virtualRepo)
        assertTrue(curl(LIST_PATH).contains(virtualRepo.getKey()))

        def resp = artifactory.repository(virtualRepo.getKey()).get()
        assertThat(resp, CoreMatchers.notNullValue())
        resp.with {
            assertThat(rclass, CoreMatchers.is(RepositoryTypeImpl.VIRTUAL))
            assertThat(key, CoreMatchers.is(virtualRepo.getKey()))
            assertThat(description, CoreMatchers.is(virtualRepo.getDescription()))
            assertThat(notes, CoreMatchers.is(virtualRepo.getNotes()))
            assertThat(artifactoryRequestsCanRetrieveRemoteArtifacts, CoreMatchers.is(virtualRepo.isArtifactoryRequestsCanRetrieveRemoteArtifacts()))
            assertThat(excludesPattern, CoreMatchers.is(virtualRepo.getExcludesPattern()))
            assertThat(includesPattern, CoreMatchers.is(virtualRepo.getIncludesPattern()))
            assertThat(virtualRepo.getRepoLayoutRef(), CoreMatchers.is(virtualRepo.getRepoLayoutRef()))
            assertThat(repositories.last(), CoreMatchers.is(virtualRepo.getRepositories().last()))
            assertThat(defaultDeploymentRepo, CoreMatchers.is(virtualRepo.getDefaultDeploymentRepo()))
            assertThat(repoLayoutRef, CoreMatchers.is(remoteRepo.getRepoLayoutRef()))
        }
    }
}
