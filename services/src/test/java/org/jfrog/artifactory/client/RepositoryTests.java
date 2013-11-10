package org.jfrog.artifactory.client;

import groovyx.net.http.HttpResponseException;
import org.jfrog.artifactory.client.model.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.apache.commons.lang.StringUtils.countMatches;
import static org.jfrog.artifactory.client.model.impl.RepositoryTypeImpl.*;
import static org.testng.Assert.*;


/**
 * @author jbaruch
 * @since 30/07/12
 */
public class RepositoryTests extends ArtifactoryTestsBase {

    private LocalRepository localRepository;

    @BeforeMethod
    protected void setUp() throws Exception {
        localRepository = artifactory.repositories().builders().localRepositoryBuilder().key(NEW_LOCAL)
                .description("new local repository").build();
    }

    @Test(groups = "repositoryBasics", dependsOnMethods = "testDelete")
    public void testCreate() throws Exception {
        String result = artifactory.repositories().create(2, localRepository);
        assertTrue(result.startsWith("Repository " + NEW_LOCAL + " created successfully."));
        assertTrue(curl(LIST_PATH).contains(NEW_LOCAL));

    }

    @Test(dependsOnMethods = "testCreate")
    public void testUpdate() throws Exception {
        LocalRepository changedRepository = artifactory.repositories().builders().builderFrom(localRepository).description("new_description").build();
        artifactory.repositories().update(changedRepository);
        assertTrue(curlAndStrip("api/repositories/" + NEW_LOCAL).contains("\"description\":\"new_description\""));
    }

    @Test(dependsOnMethods = "testCreate")
    public void testReplicationStatus() throws Exception {
        assertEquals(artifactory.repository(NEW_LOCAL).replicationStatus().getStatus(), "never_run");
    }

    @Test(groups = "repositoryBasics")
    public void testDelete() throws Exception {
        //all the assertions are taken care of in deleteRepoIfExists
            deleteRepoIfExists(NEW_LOCAL);
    }

    @Test
    public void testListRemotes() throws Exception {
        List<LightweightRepository> remoteRepositories = artifactory.repositories().list(REMOTE);
        assertNotNull(remoteRepositories);
        String expected = curlAndStrip("api/repositories?type=remote");
        assertEquals(remoteRepositories.size(), countMatches(expected, "{"));
        LightweightRepository repo = remoteRepositories.get(0);
        assertNotNull(repo);
        assertTrue(LightweightRepository.class.isAssignableFrom(repo.getClass()));
        assertTrue(expected.substring(9).startsWith(repo.getKey()));
        assertEquals(repo.getType().toString(), "remote");
        assertFalse(repo.getUrl().startsWith(url));
    }

    @Test(dependsOnMethods = "testCreate")// to be sure number of repos match
    public void testListLocals() throws Exception {
        List<LightweightRepository> localRepositories = artifactory.repositories().list(LOCAL);
        String expected = curl("api/repositories?type=local");
        assertEquals(localRepositories.size(), countMatches(expected, "{"));
        for (LightweightRepository localRepository : localRepositories) {
            assertTrue(localRepository.getUrl().startsWith(url));
            assertEquals(localRepository.getType().toString(), "local");
        }
    }

    @Test
    public void testListVirtuals() throws Exception {
        List<LightweightRepository> virtualRepositories = artifactory.repositories().list(VIRTUAL);
        assertEquals(virtualRepositories.size(), countMatches(curl("api/repositories?type=virtual"), "{"));
        for (LightweightRepository virtualRepository : virtualRepositories) {
            assertTrue(virtualRepository.getUrl().startsWith(url));
            assertEquals(virtualRepository.getType().toString(), "virtual");
        }
    }

    @Test
    public void testGetRemote() throws Exception {
        Repository repository = artifactory.repository(JCENTER).get();
        assertNotNull(repository);
        assertTrue(RemoteRepository.class.isAssignableFrom(repository.getClass()));
        RemoteRepository jcenter = (RemoteRepository) repository;
        assertEquals(jcenter.getKey(), JCENTER);
        assertEquals(jcenter.getRclass().toString(), "remote");
        //urls of jcenter are different for aol and standalone
        assertTrue(jcenter.getUrl().equals("http://jcenter.bintray.com"));
        assertEquals(jcenter.getUsername(), "");
        assertEquals(jcenter.getPassword(), "");
        assertTrue(jcenter.getDescription().equals("Bintray Central Java repository"));
        assertEquals(jcenter.getNotes(), "");
        assertEquals(jcenter.getIncludesPattern(), "**/*");
        assertEquals(jcenter.getExcludesPattern(), "");
        assertEquals(jcenter.getRemoteRepoChecksumPolicyType().toString(), "generate-if-absent");
        assertTrue(jcenter.isHandleReleases());
        assertFalse(jcenter.isHandleSnapshots());
        assertEquals(jcenter.getMaxUniqueSnapshots(), 0);
        assertFalse(jcenter.isSuppressPomConsistencyChecks());
        assertFalse(jcenter.isHardFail());
        assertFalse(jcenter.isOffline());
        assertFalse(jcenter.isBlackedOut());
        assertTrue(jcenter.isStoreArtifactsLocally());
        assertEquals(jcenter.getSocketTimeoutMillis(), 15000);
        assertEquals(jcenter.getLocalAddress(), "");
        assertEquals(jcenter.getRetrievalCachePeriodSecs(), 43200);
        assertEquals(jcenter.getMissedRetrievalCachePeriodSecs(), 7200);
        assertEquals(jcenter.getUnusedArtifactsCleanupPeriodHours(), 0);
        assertFalse(jcenter.isFetchJarsEagerly());
        assertFalse(jcenter.isFetchSourcesEagerly());
        assertFalse(jcenter.isShareConfiguration());
        assertFalse(jcenter.isSynchronizeProperties());
        List<String> propertySets = jcenter.getPropertySets();
        assertEquals(propertySets.size(), 1);
        assertEquals(propertySets.get(0), ("artifactory"));
        assertEquals(jcenter.getRepoLayoutRef(), "maven-2-default");
    }

    @Test
    public void testGetLocal() throws Exception {
        Repository repository = artifactory.repository(LIBS_RELEASE_LOCAL).get();
        assertNotNull(repository);
        assertTrue(LocalRepository.class.isAssignableFrom(repository.getClass()));
        LocalRepository libsReleasesLocal = (LocalRepository) repository;
        assertEquals(libsReleasesLocal.getKey(), LIBS_RELEASE_LOCAL);
        assertEquals(libsReleasesLocal.getRclass().toString(), "local");
        assertEquals(libsReleasesLocal.getDescription(), "Local repository for in-house libraries");
        assertEquals(libsReleasesLocal.getNotes(), "");
        assertEquals(libsReleasesLocal.getIncludesPattern(), "**/*");
        assertEquals(libsReleasesLocal.getExcludesPattern(), "");
        assertTrue(libsReleasesLocal.isHandleReleases());
        assertFalse(libsReleasesLocal.isHandleSnapshots());
        assertEquals(libsReleasesLocal.getMaxUniqueSnapshots(), 0);
        assertFalse(libsReleasesLocal.isSuppressPomConsistencyChecks());
        assertFalse(libsReleasesLocal.isBlackedOut());
        List<String> propertySets = libsReleasesLocal.getPropertySets();
        assertEquals(propertySets.size(), 1);
        assertEquals(propertySets.get(0), ("artifactory"));
        assertEquals(libsReleasesLocal.getRepoLayoutRef(), "maven-2-default");
        assertEquals(libsReleasesLocal.getSnapshotVersionBehavior().toString(), "unique");
        assertEquals(libsReleasesLocal.getChecksumPolicyType().toString(), "client-checksums");
    }

    @Test
    public void testGetVirtual() throws Exception {
        Repository repository = artifactory.repository(LIBS_RELEASE_VIRTUAL).get();
        assertNotNull(repository);
        assertTrue(VirtualRepository.class.isAssignableFrom(repository.getClass()));
        VirtualRepository libsReleases = (VirtualRepository) repository;
        assertEquals(libsReleases.getKey(), LIBS_RELEASE_VIRTUAL);
        assertEquals(libsReleases.getRclass().toString(), "virtual");
        // Stock Artifactory doesn't set this
//        assertEquals(libsReleases.getDescription(),
//                "Virtual Repository which aggregates both uploaded and proxied releases");
        assertEquals(libsReleases.getNotes(), "");
        assertEquals(libsReleases.getIncludesPattern(), "**/*");
        assertEquals(libsReleases.getExcludesPattern(), "");
        assertEquals(libsReleases.getRepositories(), asList(LIBS_RELEASE_LOCAL, "ext-release-local", "remote-repos"));
        assertEquals(libsReleases.getPomRepositoryReferencesCleanupPolicy().toString(), "discard_active_reference");
        assertFalse(libsReleases.isArtifactoryRequestsCanRetrieveRemoteArtifacts());
        assertTrue(libsReleases.getKeyPair() == null || libsReleases.getKeyPair().isEmpty());
        assertTrue(libsReleases.getRepoLayoutRef() == null || libsReleases.getRepoLayoutRef().isEmpty());
    }
}
