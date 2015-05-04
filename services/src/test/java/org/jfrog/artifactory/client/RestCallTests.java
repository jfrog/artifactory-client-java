package org.jfrog.artifactory.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jfrog.artifactory.client.impl.ArtifactoryRequestImpl;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * @author Aviad Shikloshi
 */
public class RestCallTests extends ArtifactoryTestsBase {

    private Map<String, Object> buildBody;

    @BeforeTest
    public void setUp() throws IOException {
        buildBody = createBuildBody();
    }

    @Test
    public void testRequestWithTextResponse() {
        ArtifactoryRequest systemInfo = new ArtifactoryRequestImpl()
                .method(ArtifactoryRequest.Method.GET)
                .apiUrl("api/system/ping")
                .responseType(ArtifactoryRequest.ContentType.TEXT);
        String response = artifactory.restCall(systemInfo);
        assertTrue(response.equals("OK"));
    }

    @Test
    public void testRequestWithJsonResponse() {
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
    public void testRequestWithJsonArrayResponse() {
        ArtifactoryRequest repositoryRequest = new ArtifactoryRequestImpl()
                .apiUrl("api/repositories")
                .method(ArtifactoryRequest.Method.GET)
                .responseType(ArtifactoryRequest.ContentType.JSON);
        List<String> response = artifactory.restCall(repositoryRequest);
        assertNotNull(response);
    }

    @Test(dependsOnMethods = {"testGetBuildInfo"})
    public void testPostRequestNoBody(){
        String name = (String) buildBody.get("name");
        String response = renameBuild(name, "new-name");
        assertTrue(response.contains("Build renaming of '" + name + "' to 'new-name' was successfully started."));
    }

    private String renameBuild(String oldName, String newName) {
        ArtifactoryRequest renameRequest = new ArtifactoryRequestImpl()
                .method(ArtifactoryRequest.Method.POST)
                .apiUrl("api/build/rename/" + oldName)
                .responseType(ArtifactoryRequest.ContentType.TEXT)
                .addQueryParam("to", newName);
        return artifactory.restCall(renameRequest);
    }

    private void uploadBuild() {
        ArtifactoryRequest buildRequest = new ArtifactoryRequestImpl()
                .method(ArtifactoryRequest.Method.PUT)
                .requestType(ArtifactoryRequest.ContentType.JSON)
                .responseType(ArtifactoryRequest.ContentType.JSON)
                .apiUrl("api/build")
                .requestBody(buildBody);
        artifactory.restCall(buildRequest);
    }

    @Test
    public void testGetBuildInfo() {
        try {
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
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test(dependsOnMethods = {"testPostRequestNoBody"})
    public void testDeleteBuild() {
        String deleteResponse = deleteBuild("new-name");
        assertTrue(deleteResponse.contains("All 'new-name' builds have been deleted successfully"));
    }

    private String deleteBuild(String name) {
        ArtifactoryRequest deleteBuild = new ArtifactoryRequestImpl()
                .apiUrl("api/build/" + name)
                .method(ArtifactoryRequest.Method.DELETE)
                .responseType(ArtifactoryRequest.ContentType.TEXT)
                .addQueryParam("buildNumbers", (String) buildBody.get("number"))
                .addQueryParam("deleteAll", "1")
                .addQueryParam("artifacts", "1");
        return artifactory.restCall(deleteBuild);
    }

    private Map<String, Object> createBuildBody() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> buildBody = mapper.readValue(this.getClass().getResourceAsStream("/build.json"), Map.class);
        return buildBody;
    }

}