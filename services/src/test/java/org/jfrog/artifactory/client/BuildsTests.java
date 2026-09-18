package org.jfrog.artifactory.client;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.HttpResponseException;
import org.jfrog.artifactory.client.model.AllBuilds;
import org.jfrog.artifactory.client.model.BuildNumber;
import org.jfrog.artifactory.client.model.BuildPromotionResponse;
import org.jfrog.artifactory.client.model.BuildRuns;
import org.jfrog.artifactory.client.model.PromotionMessage;
import org.jfrog.artifactory.client.model.impl.BuildPromotionRequestImpl;
import org.jfrog.build.api.Build;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import static org.jfrog.artifactory.client.Utils.createBuild;
import static org.jfrog.artifactory.client.Utils.createBuildBody;
import static org.jfrog.artifactory.client.Utils.uploadBuild;
import static org.testng.Assert.*;

/**
 * @author yahavi
 */
public class BuildsTests extends ArtifactoryTestsBase {

    private static final String BUILDS_API = "/api/build";
    private static final String TEST_BUILD_NAME = "TestBuild";
    private static final String TEST_BUILD_NUMBER = "13";
    private static final String UPLOAD_TEST_BUILD_NAME = "UploadTestBuild";
    private static final String UPLOAD_TEST_BUILD_NUMBER = "100";
    private static final String PROMOTE_TEST_BUILD_NAME = "PromoteTestBuild";
    private static final String PROMOTE_TEST_BUILD_NUMBER = "200";
    
    private Map<String, Object> buildBody;

    @BeforeClass
    public void setUp() throws IOException {
        buildBody = createBuildBody();
        uploadBuild(artifactory, buildBody);
    }

    @Test
    public void testGetAllBuilds() throws Exception {
        // Get all builds
        AllBuilds allBuilds = artifactory.builds().getAllBuilds();
        assertNotNull(allBuilds);
        assertTrue(StringUtils.contains(allBuilds.getUri(), BUILDS_API),
                allBuilds.getUri() + " is expected to contains '" + BUILDS_API + "'");
        List<org.jfrog.artifactory.client.model.Build> actualBuilds = allBuilds.getBuilds();
        assertNotNull(actualBuilds);

        // Assert build uri "/TestBuild" exist
        String expectedBuildUri = "/" + getExpectedBuildName();
        org.jfrog.artifactory.client.model.Build actualBuild = actualBuilds.stream()
                .filter(build -> StringUtils.equals(build.getUri(), expectedBuildUri))
                .findAny().orElse(null);
        assertNotNull(actualBuild, "Build Uri " + expectedBuildUri + " does not exist in [" + actualBuilds + "]");
        assertTrue(StringUtils.isNotBlank(actualBuild.getLastStarted()));
    }

    @Test
    public void testGetBuildRuns() throws IOException {
        // Get build runs of "/TestBuild"
        String expectedBuildName = getExpectedBuildName();
        BuildRuns buildRuns = artifactory.builds().getBuildRuns(expectedBuildName);
        assertNotNull(buildRuns);
        String expectedStartUrl = artifactory.getUri() + "/artifactory" + BUILDS_API + "/" + expectedBuildName;
        assertTrue(buildRuns.getUri().startsWith(expectedStartUrl), buildRuns.getUri() + " was expected to start with: " + expectedStartUrl);

        String expectedBuildNumber = "/" + getExpectedBuildNumber();
        BuildNumber buildNumber = buildRuns.getBuildsNumbers().stream()
                .filter(build -> StringUtils.equals(build.getUri(), expectedBuildNumber))
                .findAny().orElse(null);
        assertNotNull(buildNumber, "Build number " + expectedBuildNumber + " does not exist in [" + buildRuns.getBuildsNumbers() + "]");
        assertTrue(StringUtils.isNotBlank(buildNumber.getStarted()));
    }

    @Test
    public void testUploadBuild() throws IOException {
        // Create a new build using the build-info API
        Build build = createBuild();
        
        // Modify the build name and number to avoid conflicts
        build.setName(UPLOAD_TEST_BUILD_NAME);
        build.setNumber(UPLOAD_TEST_BUILD_NUMBER);
        
        // Upload the build
        artifactory.builds().uploadBuild(build);
        
        // Verify the build was uploaded by retrieving it
        BuildRuns buildRuns = artifactory.builds().getBuildRuns(UPLOAD_TEST_BUILD_NAME);
        assertNotNull(buildRuns);
        
        // Check that our build number exists
        BuildNumber buildNumber = buildRuns.getBuildsNumbers().stream()
                .filter(bn -> StringUtils.equals(bn.getUri(), "/" + UPLOAD_TEST_BUILD_NUMBER))
                .findAny().orElse(null);
        assertNotNull(buildNumber, "Build number " + UPLOAD_TEST_BUILD_NUMBER + " was not found after upload");
    }

    @Test
    public void testPromoteBuild() throws IOException {
        // First upload a build to promote using the build-info API
        Build build = createBuild();
        build.setName(PROMOTE_TEST_BUILD_NAME);
        build.setNumber(PROMOTE_TEST_BUILD_NUMBER);
        artifactory.builds().uploadBuild(build);
        
        // Create promotion request
        BuildPromotionRequestImpl promotionRequest = new BuildPromotionRequestImpl();
        promotionRequest.setStatus("Released");
        promotionRequest.setComment("Promoted by automated test");
        promotionRequest.setCiUser("testUser");
        promotionRequest.setTimestamp(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(System.currentTimeMillis()));
        promotionRequest.setCopy(false);
        promotionRequest.setArtifacts(true);
        promotionRequest.setDependencies(false);
        promotionRequest.setFailFast(true);
        promotionRequest.setDryRun(true); // Use dry run to avoid needing actual artifacts
        
        // Promote the build
        BuildPromotionResponse response = artifactory.builds().promoteBuild(
                PROMOTE_TEST_BUILD_NAME,
                PROMOTE_TEST_BUILD_NUMBER,
                promotionRequest
        );
        
        // Verify response
        assertNotNull(response);
        assertNotNull(response.getMessages());
        
        // In dry run mode, we should get messages about what would happen
        if (!response.getMessages().isEmpty()) {
            for (PromotionMessage message : response.getMessages()) {
                assertNotNull(message.getLevel());
                assertNotNull(message.getMessage());
            }
        }
    }

    @Test(expectedExceptions = HttpResponseException.class,
          description = "A non-existent build must produce HttpResponseException(404), " +
                        "not a JsonParseException from Jackson parsing an HTML/JSON error page. " +
                        "Regression test for the missing status-code check in ArtifactoryImpl.post().")
    public void testPromoteBuild_nonexistentBuild_throwsHttpResponseException() throws IOException {
        BuildPromotionRequestImpl req = new BuildPromotionRequestImpl();
        req.setTargetRepo("any-repo");
        // This must throw HttpResponseException, not JsonParseException
        artifactory.builds().promoteBuild("no-such-build-xyzzy-regression", "99999", req);
    }

    private String getExpectedBuildName() {
        return (String) buildBody.get("name");
    }

    private String getExpectedBuildNumber() {
        return (String) buildBody.get("number");
    }
}
