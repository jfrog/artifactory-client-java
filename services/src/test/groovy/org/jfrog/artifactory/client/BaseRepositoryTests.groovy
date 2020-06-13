package org.jfrog.artifactory.client

import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.StringDescription
import org.jfrog.artifactory.client.model.ContentSync
import org.jfrog.artifactory.client.model.Repository
import org.jfrog.artifactory.client.model.RepositoryType
import org.jfrog.artifactory.client.model.impl.ContentSyncImpl
import org.jfrog.artifactory.client.model.impl.RepositoryTypeImpl
import org.jfrog.artifactory.client.model.repository.settings.RepositorySettings
import org.jfrog.artifactory.client.model.repository.settings.XraySettings
import org.jfrog.artifactory.client.model.xray.settings.impl.XraySettingsImpl
import org.testng.annotations.AfterMethod
import org.testng.annotations.BeforeMethod

/**
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
abstract class BaseRepositoryTests extends ArtifactoryTestsBase {

    /**
     * used to generate test values ( especially boolean ones ) to be sure that we are sending
     * properties with values that does not match artifactory defaults
     */
    protected static final Random rnd = new Random()

    protected boolean prepareGenericRepo = true
    protected boolean prepareLocalRepo = true
    protected boolean prepareRemoteRepo = true
    protected boolean prepareVirtualRepo = true

    protected Repository genericRepo
    protected Repository localRepo
    protected Repository remoteRepo
    protected Repository virtualRepo

    protected XraySettings xraySettings
    protected Map<String, Object> customProperties

    public static final REPO_NAME_PREFIX = "rt-client-java"
    protected long repoUniqueId = System.currentTimeMillis()

    abstract RepositorySettings getRepositorySettings(RepositoryType repositoryType)

    @BeforeMethod
    protected void setUp() {
        String id = Long.toString(repoUniqueId)
        if (prepareGenericRepo) {
            RepositorySettings settings = getRepositorySettings(RepositoryTypeImpl.LOCAL)

            XraySettings genericXraySettings = new XraySettingsImpl()
            genericRepo = artifactory.repositories().builders().localRepositoryBuilder()
                    .key("$REPO_NAME_PREFIX-generic-$id")
                    .description("generic-$id")
                    .notes("notes-${rnd.nextInt()}")
                    .archiveBrowsingEnabled(rnd.nextBoolean())
                    .blackedOut(rnd.nextBoolean())
                    .excludesPattern("org/${rnd.nextInt()}/**")
                    .includesPattern("org/${rnd.nextInt()}/**")
                    .propertySets(Collections.emptyList()) // no property sets configured
                    .repositorySettings(settings)
                    .xraySettings(genericXraySettings)
                    .customProperties(new HashMap<String, Object>())
                    .build()
        }
        if (prepareLocalRepo) {
            RepositorySettings settings = getRepositorySettings()
            localRepo = artifactory.repositories().builders().localRepositoryBuilder()
                .key("$REPO_NAME_PREFIX-local-$id")
                .description("local-$id")
                .notes("notes-${rnd.nextInt()}")
                .archiveBrowsingEnabled(rnd.nextBoolean())
                .blackedOut(rnd.nextBoolean())
                .excludesPattern("org/${rnd.nextInt()}/**")
                .includesPattern("org/${rnd.nextInt()}/**")
                .propertySets(Collections.emptyList()) // no property sets configured
                .repositorySettings(settings)
                .xraySettings(xraySettings)
                .customProperties(customProperties)
                .build()
        }

        if (prepareRemoteRepo) {
            RepositorySettings settings = getRepositorySettings()
            ContentSync contentSync = new ContentSyncImpl()
            remoteRepo = artifactory.repositories().builders().remoteRepositoryBuilder()
                .key("$REPO_NAME_PREFIX-remote-$id")
                .description("remote-$id")
                .notes("notes-${rnd.nextInt()}")
                .allowAnyHostAuth(rnd.nextBoolean())
                .archiveBrowsingEnabled(rnd.nextBoolean())
                .assumedOfflinePeriodSecs(rnd.nextLong())
                .enableCookieManagement(rnd.nextBoolean())
                .excludesPattern("org/${rnd.nextInt()}/**")
                .failedRetrievalCachePeriodSecs(rnd.nextInt())
                .hardFail(rnd.nextBoolean())
                .includesPattern("org/${rnd.nextInt()}/**")
                .localAddress("http://jfrog.com/${rnd.nextInt()}")
                .missedRetrievalCachePeriodSecs(rnd.nextInt())
                .offline(rnd.nextBoolean())
                .password("password_${rnd.nextInt()}")
                .propertySets(Collections.emptyList()) // no property sets configured
                .retrievalCachePeriodSecs(rnd.nextInt())
                .shareConfiguration(rnd.nextBoolean())
                .socketTimeoutMillis(rnd.nextInt())
                .storeArtifactsLocally(rnd.nextBoolean())
                .synchronizeProperties(rnd.nextBoolean())
                .unusedArtifactsCleanupPeriodHours(Math.abs(rnd.nextInt()))
                .url("http://jfrog.com/${rnd.nextInt()}")
                .username("user_${rnd.nextInt()}")
                .repositorySettings(settings)
                .xraySettings(xraySettings)
                .contentSync(contentSync)
                .customProperties(customProperties)
                .build()
        }

        if (prepareVirtualRepo) {
            RepositorySettings settings = getRepositorySettings()
            artifactory.repositories().create(0, genericRepo)
            def repos = new ArrayList<String>()
            repos.add(genericRepo.getKey())

            virtualRepo = artifactory.repositories().builders().virtualRepositoryBuilder()
                .key("$REPO_NAME_PREFIX-virtual-$id")
                .description("virtual-$id")
                .notes("notes-${rnd.nextInt()}")
                .artifactoryRequestsCanRetrieveRemoteArtifacts(rnd.nextBoolean())
                .excludesPattern("org/${rnd.nextInt()}/**")
                .includesPattern("org/${rnd.nextInt()}/**")
                .repositories(repos)
                .repositorySettings(settings)
                .customProperties(customProperties)
                .defaultDeploymentRepo(repos.last())
                .build()
        }
    }

    @AfterMethod
    protected void tearDown() {
        // Invoking sequence is important!
        deleteRepoIfExists(genericRepo?.getKey())
        deleteRepoIfExists(localRepo?.getKey())
        deleteRepoIfExists(remoteRepo?.getKey())
        deleteRepoIfExists(virtualRepo?.getKey())

        repoUniqueId++
    }

    protected static <T> void assertThat(T actual, Matcher<? super T> matcher) {
        assertThat("", actual, matcher)
    }

    protected static <T> void assertThat(String reason, T actual, Matcher<? super T> matcher) {
        if (!matcher.matches(actual)) {
            Description description = new StringDescription()

            description.appendText(reason)
                    .appendText("\nExpected: ")
                    .appendDescriptionOf(matcher)
                    .appendText("\n     but: ")
                    .appendValue(actual)

            throw new AssertionError(description.toString())
        }
    }
}