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

    abstract RepositorySettings getRepositorySettings(RepositoryType repositoryType)

    @BeforeMethod
    protected void setUp() {
        if (prepareGenericRepo) {
            RepositorySettings settings = getRepositorySettings(RepositoryTypeImpl.LOCAL)

            XraySettings genericXraySettings = new XraySettingsImpl()
            genericRepo = artifactory.repositories().builders().localRepositoryBuilder()
                    .key("cutsman-repo_${rnd.nextInt()}")
                    .description("description_${rnd.nextInt()}")
                    .notes("notes_${rnd.nextInt()}")
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
                .key("cutsman-repo_${rnd.nextInt()}")
                .description("description_${rnd.nextInt()}")
                .notes("notes_${rnd.nextInt()}")
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
                .key("cutsman-repo_${rnd.nextInt()}")
                .description("description_${rnd.nextInt()}")
                .notes("notes_${rnd.nextInt()}")
                .allowAnyHostAuth(rnd.nextBoolean())
                .archiveBrowsingEnabled(rnd.nextBoolean())
                .assumedOfflinePeriodSecs(rnd.nextLong())
                // .blackedOut(rnd.nextBoolean())
                // .blackedOut(false)
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
                // .proxy("") // no proxy configured
                .retrievalCachePeriodSecs(rnd.nextInt())
                .shareConfiguration(rnd.nextBoolean())
                .socketTimeoutMillis(rnd.nextInt())
                .storeArtifactsLocally(rnd.nextBoolean())
                .synchronizeProperties(rnd.nextBoolean())
                // .unusedArtifactsCleanupEnabled(rnd.nextBoolean())
                // .unusedArtifactsCleanupEnabled(true)
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
                .key("cutsman-repo_${rnd.nextInt()}")
                .description("description_${rnd.nextInt()}")
                .notes("notes_${rnd.nextInt()}")
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
        deleteRepoIfExists(localRepo?.getKey())
        deleteRepoIfExists(remoteRepo?.getKey())
        deleteRepoIfExists(virtualRepo?.getKey())
        //Invoking sequence is important!
        deleteRepoIfExists(genericRepo?.getKey())
    }

    private Collection<String> nextRepos() {
        def repos = [
            'ext-release-local',
            'ext-snapshots-local',
            'libs-release-local',
            'libs-snapshots-local',
            'plugins-releases-local',
            'plugins-snapshots-local'
        ]

        Collections.singleton(repos.getAt(rnd.nextInt(repos.size())))
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
