package org.artifactory.client;

import org.artifactory.client.model.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.util.List;

import static java.util.Arrays.asList;
import static org.artifactory.client.model.RepositoryType.*;
import static org.testng.Assert.*;


/**
 * @author jbaruch
 * @since 30/07/12
 */
public class RepositoriesTests extends ArtifactoryTests {

    private static final String REPO1 = "repo1";
    private static final String LIBS_RELEASES_LOCAL = "libs-releases-local";
    private static final String NEW_LOCAL = "new-local";
    private static final String LIST_PATH = "api/repositories";
    private static final String PATH = "m/a/b/c.txt";
    private LocalRepository localRepository;

    @BeforeMethod
    protected void setUp() throws Exception {
        localRepository = artifactory.repositories().builders().localRepositoryBuilder().key(NEW_LOCAL).description("new local repository").build();
    }

    @Test
    public void testCreate() throws Exception {
        assertTrue(artifactory.repository(NEW_LOCAL).create(2, localRepository).startsWith("Repository " + NEW_LOCAL + " created successfully."));
        assertTrue(curl(LIST_PATH).contains(NEW_LOCAL));

    }

    @Test(dependsOnMethods = "testCreate")
    public void testUpdate() throws Exception {
        LocalRepository changedRepository = artifactory.repositories().builders().builderFrom(localRepository).description("new description").build();
        artifactory.repository(NEW_LOCAL).update(changedRepository);
        assertTrue(curl("api/repositories/" + NEW_LOCAL).contains("\"description\":\"new description\""));
    }

    @Test(dependsOnMethods = {"testCreate", "testUpdate", "testDeploy"})
    public void testDelete() throws Exception {
        assertTrue(artifactory.repository(NEW_LOCAL).delete().startsWith("Repository " + NEW_LOCAL + " and all its content have been removed successfully."));
        assertFalse(curl(LIST_PATH).contains(NEW_LOCAL));
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
        Repository repository = artifactory.repository(REPO1).get();
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

    @Test
    public void testGetLocal() throws Exception {
        Repository repository = artifactory.repository(LIBS_RELEASES_LOCAL).get();
        assertNotNull(repository);
        assertTrue(LocalRepository.class.isAssignableFrom(repository.getClass()));
        LocalRepository libsReleasesLocal = (LocalRepository) repository;
        assertEquals(libsReleasesLocal.getKey(), LIBS_RELEASES_LOCAL);
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
        Repository repository = artifactory.repository("libs-releases").get();
        assertNotNull(repository);
        assertTrue(VirtualRepository.class.isAssignableFrom(repository.getClass()));
        VirtualRepository libsReleases = (VirtualRepository) repository;
        assertEquals(libsReleases.getKey(), "libs-releases");
        assertEquals(libsReleases.getRclass().toString(), "virtual");
        assertEquals(libsReleases.getDescription(), "Virtual Repository which aggregates both uploaded and proxied releases");
        assertEquals(libsReleases.getNotes(), "");
        assertEquals(libsReleases.getIncludesPattern(), "**/*");
        assertEquals(libsReleases.getExcludesPattern(), "");
        assertEquals(libsReleases.getRepositories(), asList(LIBS_RELEASES_LOCAL, "ext-releases-local", "remote-repos"));
        assertEquals(libsReleases.getPomRepositoryReferencesCleanupPolicy().toString(), "discard_active_reference");
        assertFalse(libsReleases.isArtifactoryRequestsCanRetrieveRemoteArtifacts());
        assertTrue(libsReleases.getKeyPair() == null || libsReleases.getKeyPair().isEmpty());
        assertTrue(libsReleases.getRepoLayoutRef() == null || libsReleases.getRepoLayoutRef().isEmpty());

    }

    @Test
    public void testGetFolder() throws Exception {
        Folder folder = artifactory.repository("repo1-cache").folder("junit").get();
        assertNotNull(folder);
    }

    @Test(dependsOnMethods = "testCreate")
    public void testDeploy() {
        InputStream inputStream = this.getClass().getResourceAsStream("/sample.txt");
        assertNotNull(inputStream);
        File deployed = artifactory.repository(NEW_LOCAL).prepareArtifactFrom(inputStream).addParameter("colors", "red", "gold", "green").deployTo(PATH);
        assertNotNull(deployed);
        assertEquals(deployed.getRepo(), NEW_LOCAL);
        assertEquals(deployed.getPath(), "/"+PATH);
        assertEquals(deployed.getCreatedBy(), username);
        assertEquals(deployed.getDownloadUri(), host+"/"+applicationName+"/"+NEW_LOCAL+"/"+PATH);
        assertEquals(deployed.getSize(), 3044);
    }
}
