package org.jfrog.artifactory.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.jfrog.artifactory.client.impl.ArtifactoryRequestImpl;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class RestCallFullResponseTests extends ArtifactoryTestsBase {

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
        String response = artifactory.restCall(systemInfo);
        assertTrue(response.equals("OK"));
    }

    @Test
    public void testRequestWithJsonResponse() throws Exception {
        ArtifactoryRequest versionRequest = new ArtifactoryRequestImpl()
                .apiUrl("api/system/version")
                .responseType(ArtifactoryRequest.ContentType.JSON)
                .method(ArtifactoryRequest.Method.GET);
        Map<String, Object> versionRequestResponse = artifactory.restCall(versionRequest);
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
                .requestBody(IOUtils.toString(this.getClass().getResourceAsStream("/public.key"), "UTF-8"))
                .responseType(ArtifactoryRequest.ContentType.TEXT);
        String gpgResponse = artifactory.restCall(gpgRequest);
        assertTrue(gpgResponse.contains("Successfully configured the gpg public key"));
    }

    @Test
    public void testRequestWithJsonArrayResponse() throws Exception {
        ArtifactoryRequest repositoryRequest = new ArtifactoryRequestImpl()
                .apiUrl("api/repositories")
                .method(ArtifactoryRequest.Method.GET)
                .responseType(ArtifactoryRequest.ContentType.JSON);
        List<String> response = artifactory.restCall(repositoryRequest);
        assertNotNull(response);
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
        return artifactory.restCall(renameRequest);
    }

    private void uploadBuild() throws Exception {
        ArtifactoryRequest buildRequest = new ArtifactoryRequestImpl()
                .method(ArtifactoryRequest.Method.PUT)
                .requestType(ArtifactoryRequest.ContentType.JSON)
                .responseType(ArtifactoryRequest.ContentType.JSON)
                .apiUrl("api/build")
                .requestBody(buildBody);
        artifactory.restCall(buildRequest);
    }

    @Test
    public void testGetBuildInfo() throws Exception {
        uploadBuild();
        ArtifactoryRequest buildInfoRequest = new ArtifactoryRequestImpl()
                .method(ArtifactoryRequest.Method.GET)
                .apiUrl("api/build/" + buildBody.get("name") + "/" + buildBody.get("number"))
                .responseType(ArtifactoryRequest.ContentType.JSON);
        Map<String, Object> buildInfoResponse = artifactory.restCall(buildInfoRequest);
        assertNotNull(buildInfoResponse);
        Map<String, Object> buildInfo = (Map<String, Object>) buildInfoResponse.get("buildInfo");
        assertTrue(buildInfo.containsKey("version"));
        assertTrue(buildInfo.containsKey("name"));
        assertTrue(buildInfo.containsKey("number"));
    }

    @Test(dependsOnMethods = {"testGetBuildInfo"})
    public void testDeleteBuild() throws Exception {
        String deleteResponse = deleteBuild("TestBuild");
        assertTrue(deleteResponse.contains("All 'TestBuild' builds have been deleted successfully"));
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
        artifactory.restCall(req);

        // Verify permission target created:
        List permissions = getPermissionTargets();
        assertTrue(findPermissionInLiat(permissions, permissionName));

        // Delete permission target:
        req = new ArtifactoryRequestImpl()
                .method(ArtifactoryRequest.Method.DELETE)
                .apiUrl("api/security/permissions/" + permissionName);
        artifactory.restCall(req);

        // Verify permission target deleted:
        permissions = getPermissionTargets();
        assertFalse(findPermissionInLiat(permissions, permissionName));
    }

    private String deleteBuild(String name) throws Exception {
        ArtifactoryRequest deleteBuild = new ArtifactoryRequestImpl()
                .apiUrl("api/build/" + name)
                .method(ArtifactoryRequest.Method.DELETE)
                .responseType(ArtifactoryRequest.ContentType.TEXT)
                .addQueryParam("buildNumbers", (String) buildBody.get("number"))
                .addQueryParam("deleteAll", "1")
                .addQueryParam("artifacts", "1");
        return artifactory.restCall(deleteBuild);
    }

    private List getPermissionTargets() throws Exception {
        ArtifactoryRequest req = new ArtifactoryRequestImpl()
                .method(ArtifactoryRequest.Method.GET)
                .apiUrl("api/security/permissions")
                .responseType(ArtifactoryRequest.ContentType.JSON);

        return artifactory.restCall(req);
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

    private boolean findPermissionInLiat(List list, String permissionName) {
        for(Object permission : list) {
            Object name = ((Map)permission).get("name");
            if (permissionName.equals(name)) {
                return true;
            }
        }
        return false;
    }

    private Map<String, Object> createBuildBody() throws IOException {
        String buildStarted = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(System.currentTimeMillis());
        String buildInfoJson = IOUtils.toString(this.getClass().getResourceAsStream("/build.json"), "UTF-8");
        buildInfoJson = StringUtils.replace(buildInfoJson, "{build.start.time}", buildStarted);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(buildInfoJson, Map.class);
    }
}
