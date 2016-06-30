package org.jfrog.artifactory.client;

import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.StringDescription
import org.jfrog.artifactory.client.model.Repository
import org.jfrog.artifactory.client.model.repository.settings.RepositorySettings
import org.testng.annotations.AfterMethod
import org.testng.annotations.BeforeMethod

/**
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
public abstract class BaseRepositoryTests extends ArtifactoryTestsBase {

    /**
     * used to generate test values ( especially boolean ones ) to be sure that we are sending
     * properties with values that does not match artifactory defaults
     */
    protected static final Random rnd = new Random()

    protected boolean prepareLocalRepo = true
    protected boolean prepareRemoteRepo = true
    protected boolean prepareVirtualRepo = true

    protected Repository localRepo
    protected Repository remoteRepo
    protected Repository virtualRepo

    protected RepositorySettings settings

    @BeforeMethod
    protected void setUp() {
        if (prepareLocalRepo) {
            localRepo = artifactory.repositories().builders().localRepositoryBuilder()
                .key("cutsman-repo_${rnd.nextInt()}")
                .description("description_${rnd.nextInt()}")
                .notes("notes_${rnd.nextInt()}")
                .archiveBrowsingEnabled(rnd.nextBoolean())
                .blackedOut(rnd.nextBoolean())
                .excludesPattern("org/${rnd.nextInt()}/**")
                .includesPattern("org/${rnd.nextInt()}/**")
                .propertySets(Collections.emptyList()) // no property sets configured
                .repoLayoutRef(nextRepoLayout())
                .repositorySettings(settings)
                .build();
        }

        if(prepareRemoteRepo) {
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
                .repoLayoutRef(nextRepoLayout())
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
                .build()
        }

        if(prepareVirtualRepo) {
            def repos = nextRepos()

            virtualRepo = artifactory.repositories().builders().virtualRepositoryBuilder()
                .key("cutsman-repo_${rnd.nextInt()}")
                .description("description_${rnd.nextInt()}")
                .notes("notes_${rnd.nextInt()}")
                .artifactoryRequestsCanRetrieveRemoteArtifacts(rnd.nextBoolean())
                .excludesPattern("org/${rnd.nextInt()}/**")
                .includesPattern("org/${rnd.nextInt()}/**")
                .repoLayoutRef(nextRepoLayout())
                .repositories(repos)
                .repositorySettings(settings)
                .defaultDeploymentRepo(repos.last())
                .build()
        }
    }

    @AfterMethod
    protected void tearDown() {
        def catchException = { Exception exception ->
            if (exception.getMessage().equals('Not Found')) {
            } else {
                throw exception
            }
        }

        if(localRepo) {
            try {
                artifactory.repository(localRepo.getKey()).delete()
            } catch (Exception e) {
                catchException(e)
            }
        }

        if(remoteRepo) {
            try {
                artifactory.repository(remoteRepo.getKey()).delete()
            } catch (Exception e) {
                catchException(e)
            }
        }

        if(virtualRepo) {
            try {
                artifactory.repository(virtualRepo.getKey()).delete()
            } catch (Exception e) {
                catchException(e)
            }
        }
    }

    private String nextRepoLayout() {
        def layouts = [
            //'bower-default',
            //'gradle-default',
            //'ivy-default',
            //'maven-1-default',
            'maven-2-default'
            //'npm-default',
            //'nuget-default',
            //'sbt-default',
            //'simple-default',
            //'vcs-default'
        ]

        layouts.getAt(rnd.nextInt(layouts.size()))
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
