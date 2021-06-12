package org.jfrog.artifactory.client;

import org.apache.commons.lang3.StringUtils;
import org.jfrog.artifactory.client.model.AllBuilds;
import org.jfrog.artifactory.client.model.Build;
import org.jfrog.artifactory.client.model.BuildNumber;
import org.jfrog.artifactory.client.model.BuildRuns;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.jfrog.artifactory.client.Utils.createBuildBody;
import static org.jfrog.artifactory.client.Utils.uploadBuild;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * @author yahavi
 */
public class BuildsTests extends ArtifactoryTestsBase {

    private static final String BUILDS_API = "/api/build";
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
        List<Build> actualBuilds = allBuilds.getBuilds();
        assertNotNull(actualBuilds);

        // Assert build uri "/TestBuild" exist
        String expectedBuildUri = "/" + getExpectedBuildName();
        Build actualBuild = actualBuilds.stream()
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

    private String getExpectedBuildName() {
        return (String) buildBody.get("name");
    }

    private String getExpectedBuildNumber() {
        return (String) buildBody.get("number");
    }
}
