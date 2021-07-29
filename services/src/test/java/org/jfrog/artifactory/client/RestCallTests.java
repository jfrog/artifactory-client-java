package org.jfrog.artifactory.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.jfrog.artifactory.client.impl.ArtifactoryRequestImpl;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.jfrog.artifactory.client.Utils.uploadBuild;
import static org.testng.Assert.*;

/**
 * @author Aviad Shikloshi
 */
public class RestCallTests extends ArtifactoryTestsBase {
    private static final long PERM_TARGET_SLEEP_INTERVAL_MILLIS = TimeUnit.SECONDS.toMillis(10);
    private static final int PERM_TARGET_RETRIES = 12;
    private Map<String, Object> buildBody;

    @BeforeTest
    public void setUp() throws IOException {
        buildBody = createBuildBody();
    }

    @Test
    public void testRequestWithTextResponse() throws Exception {
        ArtifactoryRequest systemInfo = new ArtifactoryRequestImpl()
                .method(ArtifactoryRequest.Method.GET)
                .apiUrl("api/system/ping")
                .responseType(ArtifactoryRequest.ContentType.TEXT);
        ArtifactoryResponse response = artifactory.restCall(systemInfo);
        assertTrue(response.isSuccessResponse());
    }

    @Test
    public void testRequestWithJsonResponse() throws Exception {
        ArtifactoryRequest versionRequest = new ArtifactoryRequestImpl()
                .apiUrl("api/system/version")
                .responseType(ArtifactoryRequest.ContentType.JSON)
                .method(ArtifactoryRequest.Method.GET);

        ArtifactoryResponse response = artifactory.restCall(versionRequest);
        Map<String, Object> versionRequestResponse = response.parseBody(Map.class);

        assertTrue(versionRequestResponse.containsKey("version"));
        assertTrue(versionRequestResponse.containsKey("revision"));
        assertTrue(versionRequestResponse.containsKey("addons"));
    }

    @Test
    public void testRequestWithTextBody() throws Exception {
        ArtifactoryRequest gpgRequest = new ArtifactoryRequestImpl()
                .method(ArtifactoryRequest.Method.PUT)
                .apiUrl("api/gpg/key/public")
                .requestType(ArtifactoryRequest.ContentType.TEXT)
                .requestBody(IOUtils.toString(this.getClass().getResourceAsStream("/public.key"), StandardCharsets.UTF_8))
                .responseType(ArtifactoryRequest.ContentType.TEXT);
        String gpgResponse = artifactory.restCall(gpgRequest).getRawBody();
        assertTrue(gpgResponse.contains("Successfully configured the gpg public key"));
    }

    @Test
    public void testRequestWithJsonArrayResponse() throws Exception {
        ArtifactoryRequest repositoryRequest = new ArtifactoryRequestImpl()
                .apiUrl("api/repositories")
                .method(ArtifactoryRequest.Method.GET)
                .responseType(ArtifactoryRequest.ContentType.JSON);
        ArtifactoryResponse response = artifactory.restCall(repositoryRequest);

        List<Map<String, String>> responseBody = response.parseBody(List.class);
        assertNotNull(responseBody);
        assertTrue(responseBody.size() > 0);
        for (Map<String, String> map : responseBody) {
            assertTrue(map.containsKey("key"));
            assertTrue(map.containsKey("type"));
        }
    }

    @Test
    public void testPostRequestNoBody() throws Exception {
        String response = executeApi("block");
        assertTrue(response.contains("Successfully blocked all replications, no replication will be triggered."));
        response = executeApi("unblock");
        assertTrue(response.contains("Successfully unblocked all replications."));
    }

    private String executeApi(String command) throws Exception {
        ArtifactoryRequest renameRequest = new ArtifactoryRequestImpl()
                .method(ArtifactoryRequest.Method.POST)
                .apiUrl("api/system/replications/" + command)
                .responseType(ArtifactoryRequest.ContentType.TEXT);
        return artifactory.restCall(renameRequest).getRawBody();
    }

    @Test
    public void testGetBuildInfo() throws Exception {
        uploadBuild(artifactory, buildBody);
        ArtifactoryRequest buildInfoRequest = new ArtifactoryRequestImpl()
                .method(ArtifactoryRequest.Method.GET)
                .apiUrl("api/build/" + buildBody.get("name") + "/" + buildBody.get("number"))
                .responseType(ArtifactoryRequest.ContentType.JSON);
        ArtifactoryResponse response = artifactory.restCall(buildInfoRequest);

        Map<String, Object> buildInfoResponse = response.parseBody(Map.class);
        assertNotNull(buildInfoResponse);
        Map<String, Object> buildInfo = (Map<String, Object>) buildInfoResponse.get("buildInfo");
        assertTrue(buildInfo.containsKey("version"));
        assertTrue(buildInfo.containsKey("name"));
        assertTrue(buildInfo.containsKey("number"));
    }

    @Test(dependsOnMethods = {"testGetBuildInfo"})
    public void testDeleteBuild() throws Exception {
        deleteBuild("TestBuild");
        String builds = getBuilds();
        assertFalse(builds.contains("TestBuild"));
    }

    @Test
    public void testGetPermissionTargets() throws Exception {
        List response = getPermissionTargets();
        assertNotNull(response);
    }

    @Test
    public void testCreateDeletePermissionTarget() throws Exception {
        final String permissionName = "java-client-tests-permission";
        Map<String, Object> map = createPermissionTargetBody(permissionName);

        // Create permission target:
        ArtifactoryRequest req = new ArtifactoryRequestImpl()
                .method(ArtifactoryRequest.Method.PUT)
                .apiUrl("api/security/permissions/" + permissionName)
                .requestType(ArtifactoryRequest.ContentType.JSON)
                .requestBody(map);
        ArtifactoryResponse response = artifactory.restCall(req);
        assertTrue(response.isSuccessResponse(), response.getRawBody());

        // Verify permission target created:
        assetPermissionTarget(permissionName, true);

        // Delete permission target:
        req = new ArtifactoryRequestImpl()
                .method(ArtifactoryRequest.Method.DELETE)
                .apiUrl("api/security/permissions/" + permissionName);
        response = artifactory.restCall(req);
        assertTrue(response.isSuccessResponse(), response.getRawBody());

        // Verify permission target deleted:
        assetPermissionTarget(permissionName, false);
    }

    private String deleteBuild(String name) throws Exception {
        ArtifactoryRequest deleteBuild = new ArtifactoryRequestImpl()
                .apiUrl("api/build/" + name)
                .method(ArtifactoryRequest.Method.DELETE)
                .responseType(ArtifactoryRequest.ContentType.TEXT)
                .addQueryParam("buildNumbers", (String) buildBody.get("number"))
                .addQueryParam("deleteAll", "1")
                .addQueryParam("artifacts", "1");
        return artifactory.restCall(deleteBuild).getRawBody();
    }

    private String getBuilds() throws Exception {
        ArtifactoryRequest deleteBuild = new ArtifactoryRequestImpl()
                .apiUrl("api/build/")
                .method(ArtifactoryRequest.Method.GET)
                .responseType(ArtifactoryRequest.ContentType.TEXT);
        return artifactory.restCall(deleteBuild).getRawBody();
    }

    private List getPermissionTargets() throws Exception {
        ArtifactoryRequest req = new ArtifactoryRequestImpl()
                .method(ArtifactoryRequest.Method.GET)
                .apiUrl("api/security/permissions")
                .responseType(ArtifactoryRequest.ContentType.JSON);
        ArtifactoryResponse response = artifactory.restCall(req);

        List<String> responseBody = response.parseBody(List.class);
        return responseBody;
    }

    private void assetPermissionTarget(String permissionName, boolean expectExist) throws Exception {
        for (int i = 0; i < PERM_TARGET_RETRIES; i++) {
            List permissions = getPermissionTargets();
            if (findPermissionInList(permissions, permissionName) == expectExist) {
                return;
            }
            Thread.sleep(PERM_TARGET_SLEEP_INTERVAL_MILLIS);
        }
        fail("Permission " + permissionName + " is expected to " + (expectExist ? "" : "not ") + "exist.");
    }

    private Map<String, Object> createPermissionTargetBody(String permissionName) throws IOException {
        String json =
                "{" +
                        "\"name\" : \"" + permissionName + "\"," +
                        "\"includesPattern\" : \"**\"," +
                        "\"excludesPattern\" : \"\"," +
                        "\"repositories\" : [ \"ANY\" ]," +
                        "\"principals\" : {" +
                        "\"users\" : {" +
                        "\"anonymous\" : [ \"r\" ]" +
                        "}," +
                        "\"groups\" : {" +
                        "\"readers\" : [ \"r\" ]" +
                        "}" +
                        "}" +
                        "}";

        return new ObjectMapper().readValue(json, Map.class);
    }

    private boolean findPermissionInList(List list, String permissionName) {
        for (Object permission : list) {
            Object name = ((Map) permission).get("name");
            if (permissionName.equals(name)) {
                return true;
            }
        }
        return false;
    }

    private Map<String, Object> createBuildBody() throws IOException {
        String buildStarted = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(System.currentTimeMillis());
        String buildInfoJson = IOUtils.toString(this.getClass().getResourceAsStream("/build.json"), StandardCharsets.UTF_8);
        buildInfoJson = StringUtils.replace(buildInfoJson, "{build.start.time}", buildStarted);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(buildInfoJson, Map.class);
    }
}
