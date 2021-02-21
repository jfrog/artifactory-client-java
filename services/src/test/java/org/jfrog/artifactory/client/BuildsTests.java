package org.jfrog.artifactory.client;

import org.apache.commons.lang3.StringUtils;
import org.jfrog.artifactory.client.impl.BuildsImpl;
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
import static org.testng.Assert.*;

/**
 * @author yahavi
 */
public class BuildsTests extends ArtifactoryTestsBase {

    private Map<String, Object> buildBody;
    private BuildsImpl builds;

    @BeforeClass
    public void setUp() throws IOException {
        buildBody = createBuildBody();
        uploadBuild(artifactory, buildBody);
        builds = new BuildsImpl(artifactory);
    }

    @Test
    public void testGetAllBuilds() throws Exception {
        // Get all builds
        AllBuilds allBuilds = builds.getAllBuilds();
        assertNotNull(allBuilds);
        assertTrue(StringUtils.endsWith(allBuilds.getUri(), BuildsImpl.BUILDS_API),
                allBuilds.getUri() + " is expected to ends with '" + BuildsImpl.BUILDS_API + "'");
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
        BuildRuns buildRuns = builds.getBuildRuns(expectedBuildName);
        assertNotNull(buildRuns);
        assertEquals(buildRuns.getUri(), artifactory.getUri() + "/artifactory" + BuildsImpl.BUILDS_API + "/" + expectedBuildName);

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
