package org.artifactory.client;

import org.artifactory.client.model.LightweightRepository;
import org.artifactory.client.model.RemoteRepository;
import org.artifactory.client.model.Repository;
import org.testng.annotations.Test;

import java.util.List;

import static org.artifactory.client.model.RepositoryType.*;
import static org.testng.Assert.*;


/**
 * @author jbaruch
 * @since 30/07/12
 */
public class RepositoriesTests extends ArtifactoryTests {

    private static final String REPO1 = "repo1";

    @Test
    public void testCreate() throws Exception {

    }

    @Test
    public void testUpdate() throws Exception {

    }

    @Test
    public void testDelete() throws Exception {

    }

    @Test
    public void testListRemotes() throws Exception {
        List<LightweightRepository> remoteRepositories = artifactory.repositories().list(REMOTE);
        assertNotNull(remoteRepositories);
        assertEquals(remoteRepositories.size(), 1);
        LightweightRepository repo = remoteRepositories.get(0);
        assertNotNull(repo);
        assertTrue(LightweightRepository.class.isAssignableFrom(repo.getClass()));
        assertEquals(repo.getKey(), REPO1);
        assertEquals(repo.getDescription(), "Central Repo1");
        assertEquals(repo.getType().toString(), "remote");
        assertFalse(repo.getUrl().startsWith("http://clienttests.artifactoryonline.com/clienttests"));
        assertEquals(repo.getUrl(), "http://repo-demo.jfrog.org/artifactory/repo1");

    }

    @Test
    public void testListLocals() throws Exception {
        List<LightweightRepository> localRepositories = artifactory.repositories().list(LOCAL);
        assertEquals(localRepositories.size(), 6);
        for (LightweightRepository localRepository : localRepositories) {
            assertTrue(localRepository.getUrl().startsWith("http://clienttests.artifactoryonline.com/clienttests"));
            assertEquals(localRepository.getType().toString(), "local");
        }
    }

    @Test
    public void testListVirtuals() throws Exception {
        List<LightweightRepository> virtualRepositories = artifactory.repositories().list(VIRTUAL);
        assertEquals(virtualRepositories.size(), 7);
        for (LightweightRepository virtualRepository : virtualRepositories) {
            assertTrue(virtualRepository.getUrl().startsWith("http://clienttests.artifactoryonline.com/clienttests"));
            assertEquals(virtualRepository.getType().toString(), "virtual");
        }
    }

    @Test
    public void testGetRemote() throws Exception {
        Repository repository = artifactory.repositories().repoKey(REPO1).get();
        assertNotNull(repository);
        assertTrue(RemoteRepository.class.isAssignableFrom(repository.getClass()));
        RemoteRepository repo1 = (RemoteRepository) repository;
        assertEquals(repo1.getKey(), "repo1");
        assertEquals(repo1.getRclass().toString(), "remote");
        assertEquals(repo1.getUrl(), "http://repo-demo.jfrog.org/artifactory/repo1");
        assertEquals(repo1.getUsername(), "");
        assertEquals(repo1.getPassword(), "");
        assertEquals(repo1.getDescription(), "Central Repo1");
        assertEquals(repo1.getNotes(), "");
        assertEquals(repo1.getIncludesPattern(), "**/*");
        assertEquals(repo1.getExcludesPattern(), "");
        assertEquals(repo1.getRemoteRepoChecksumPolicyType().toString(), "generate-if-absent");
        assertTrue(repo1.isHandleReleases());
        assertFalse(repo1.isHandleSnapshots());
        assertEquals(repo1.getMaxUniqueSnapshots(), 0);
        assertFalse(repo1.isSuppressPomConsistencyChecks());
        assertFalse(repo1.isHardFail());
        assertFalse(repo1.isOffline());
        assertFalse(repo1.isBlackedOut());
        assertTrue(repo1.isStoreArtifactsLocally());
        assertEquals(repo1.getSocketTimeoutMillis(), 15000);
        assertEquals(repo1.getLocalAddress(), "");
        assertEquals(repo1.getRetrievalCachePeriodSecs(), 43200);
        assertEquals(repo1.getFailedRetrievalCachePeriodSecs(), 300);
        assertEquals(repo1.getMissedRetrievalCachePeriodSecs(), 7200);
        assertEquals(repo1.getUnusedArtifactsCleanupPeriodHours(), 0);
        assertFalse(repo1.isFetchJarsEagerly());
        assertFalse(repo1.isFetchSourcesEagerly());
        assertFalse(repo1.isShareConfiguration());
        assertFalse(repo1.isSynchronizeProperties());
        List<String> propertySets = repo1.getPropertySets();
        assertEquals(propertySets.size(), 1);
        assertEquals(propertySets.get(0), ("artifactory"));
        assertEquals(repo1.getRepoLayoutRef(), "maven-2-default");
    }

}
