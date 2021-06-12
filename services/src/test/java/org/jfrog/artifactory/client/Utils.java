package org.jfrog.artifactory.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.jfrog.artifactory.client.impl.ArtifactoryRequestImpl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.fail;

/**
 * @author yahavi
 **/
public class Utils {

    public static void uploadBuild(Artifactory artifactory, Map<String, Object> buildBody) throws IOException {
        ArtifactoryRequest buildRequest = new ArtifactoryRequestImpl()
                .method(ArtifactoryRequest.Method.PUT)
                .requestType(ArtifactoryRequest.ContentType.JSON)
                .responseType(ArtifactoryRequest.ContentType.JSON)
                .apiUrl("api/build")
                .requestBody(buildBody);
        artifactory.restCall(buildRequest);
    }

    public static Map<String, Object> createBuildBody() {
        String buildStarted = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(System.currentTimeMillis());
        try {
            String buildInfoJson = IOUtils.toString(Utils.class.getResourceAsStream("/build.json"), StandardCharsets.UTF_8);
            buildInfoJson = StringUtils.replace(buildInfoJson, "{build.start.time}", buildStarted);
            return new ObjectMapper().readValue(buildInfoJson, Map.class);
        } catch (IOException e) {
            fail(ExceptionUtils.getRootCauseMessage(e));
        }
        return new HashMap<>();
    }
}
