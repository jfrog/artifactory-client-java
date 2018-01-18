package org.jfrog.artifactory.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpStatus;
import org.jfrog.artifactory.client.impl.ArtifactoryRequestImpl;
import org.jfrog.artifactory.client.utils.RestCallTestUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.*;

public class RestCallFullResponseTests extends ArtifactoryTestsBase {

    private Map<String, Object> buildBody;
    private RestCallTestUtils utils = new RestCallTestUtils();

    @BeforeTest
    public void setUp() throws IOException {
        buildBody = utils.createBuildBody();
    }

    @Test
    public void testRequestWithTextResponse() throws Exception {
        ArtifactoryRequest systemInfo = new ArtifactoryRequestImpl()
                .method(ArtifactoryRequest.Method.GET)
                .apiUrl("api/system/ping")
                .responseType(ArtifactoryRequest.ContentType.TEXT);
        ArtifactoryResponse response = artifactory.restCallFullResponse(systemInfo);
        assertTrue(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK);
    }

    @Test
    public void testRequestWithJsonResponse() throws Exception {
        ArtifactoryRequest versionRequest = new ArtifactoryRequestImpl()
                .apiUrl("api/system/version")
                .responseType(ArtifactoryRequest.ContentType.JSON)
                .method(ArtifactoryRequest.Method.GET);
        ArtifactoryResponse response = artifactory.restCallFullResponse(versionRequest);

        Map<String, Object> versionRequestResponse = new ObjectMapper().readValue(response.getBody(), Map.class);
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
        String gpgResponse = artifactory.restCallFullResponse(gpgRequest).getBody();
        assertTrue(gpgResponse.contains("Successfully configured the gpg public key"));
    }

    @Test
    public void testRequestWithJsonArrayResponse() throws Exception {
        ArtifactoryRequest repositoryRequest = new ArtifactoryRequestImpl()
                .apiUrl("api/repositories")
                .method(ArtifactoryRequest.Method.GET)
                .responseType(ArtifactoryRequest.ContentType.JSON);
        ArtifactoryResponse response = artifactory.restCallFullResponse(repositoryRequest);

        List<String> responseBody = new ObjectMapper().readValue(response.getBody(), List.class);
        assertNotNull(responseBody);
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
        return artifactory.restCallFullResponse(renameRequest).getBody();
    }

    private void uploadBuild() throws Exception {
        ArtifactoryRequest buildRequest = new ArtifactoryRequestImpl()
                .method(ArtifactoryRequest.Method.PUT)
                .requestType(ArtifactoryRequest.ContentType.JSON)
                .responseType(ArtifactoryRequest.ContentType.JSON)
                .apiUrl("api/build")
                .requestBody(buildBody);
        artifactory.restCallFullResponse(buildRequest);
    }

    @Test
    public void testGetBuildInfo() throws Exception {
        uploadBuild();
        ArtifactoryRequest buildInfoRequest = new ArtifactoryRequestImpl()
                .method(ArtifactoryRequest.Method.GET)
                .apiUrl("api/build/" + buildBody.get("name") + "/" + buildBody.get("number"))
                .responseType(ArtifactoryRequest.ContentType.JSON);
        ArtifactoryResponse response = artifactory.restCallFullResponse(buildInfoRequest);

        Map<String, Object> buildInfoResponse = new ObjectMapper().readValue(response.getBody(), Map.class);
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
        Map<String, Object> map = utils.createPermissionTargetBody(permissionName);

        // Create permission target:
        ArtifactoryRequest req = new ArtifactoryRequestImpl()
                .method(ArtifactoryRequest.Method.PUT)
                .apiUrl("api/security/permissions/" + permissionName)
                .requestType(ArtifactoryRequest.ContentType.JSON)
                .requestBody(map);
        artifactory.restCallFullResponse(req);

        // Verify permission target created:
        List permissions = getPermissionTargets();
        assertTrue(utils.findPermissionInList(permissions, permissionName));

        // Delete permission target:
        req = new ArtifactoryRequestImpl()
                .method(ArtifactoryRequest.Method.DELETE)
                .apiUrl("api/security/permissions/" + permissionName);
        artifactory.restCallFullResponse(req);

        // Verify permission target deleted:
        permissions = getPermissionTargets();
        assertFalse(utils.findPermissionInList(permissions, permissionName));
    }

    private String deleteBuild(String name) throws Exception {
        ArtifactoryRequest deleteBuild = new ArtifactoryRequestImpl()
                .apiUrl("api/build/" + name)
                .method(ArtifactoryRequest.Method.DELETE)
                .responseType(ArtifactoryRequest.ContentType.TEXT)
                .addQueryParam("buildNumbers", (String) buildBody.get("number"))
                .addQueryParam("deleteAll", "1")
                .addQueryParam("artifacts", "1");
        return artifactory.restCallFullResponse(deleteBuild).getBody();
    }

    private List getPermissionTargets() throws Exception {
        ArtifactoryRequest req = new ArtifactoryRequestImpl()
                .method(ArtifactoryRequest.Method.GET)
                .apiUrl("api/security/permissions")
                .responseType(ArtifactoryRequest.ContentType.JSON);
        ArtifactoryResponse response = artifactory.restCallFullResponse(req);

        List<String> responseBody = new ObjectMapper().readValue(response.getBody(), List.class);
        return responseBody;
    }
}
