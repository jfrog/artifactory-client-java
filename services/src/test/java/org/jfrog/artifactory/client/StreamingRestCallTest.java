package org.jfrog.artifactory.client;

import org.apache.commons.io.IOUtils;
import org.jfrog.artifactory.client.impl.ArtifactoryRequestImpl;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.*;

public class StreamingRestCallTest extends ArtifactoryTestsBase {

    @Test
    public void testDownloadWithHeadersByStreamingRestCall() throws IOException {
        InputStream inputStream = this.getClass().getResourceAsStream("/sample.txt");
        assertNotNull(inputStream);
        artifactory.repository(localRepository.getKey()).upload(PATH, inputStream).withProperty("color", "blue")
                .withProperty("color", "red").doUpload();

        Map<String, String> headers = new HashMap<>();
        headers.put("Range", "bytes=0-10");
        ArtifactoryRequest request = new ArtifactoryRequestImpl()
                .apiUrl(localRepository.getKey() + "/" + PATH)
                .method(ArtifactoryRequest.Method.GET)
                .setHeaders(headers)
                .requestType(ArtifactoryRequest.ContentType.JSON);

        ArtifactoryStreamingResponse response = artifactory.streamingRestCall(request);
        assertTrue(response.isSuccessResponse());

        inputStream = response.getInputStream();
        String actual = textFrom(inputStream);
        assertEquals(actual, textFrom(this.getClass().getResourceAsStream("/sample.txt")).substring(0, 11));
    }

    @Test
    public void testErrorStreamingRestCall() throws IOException {
        ArtifactoryRequest request = new ArtifactoryRequestImpl()
                .apiUrl(localRepository.getKey() + "/" + PATH + "shouldNotExist")
                .method(ArtifactoryRequest.Method.GET)
                .requestType(ArtifactoryRequest.ContentType.JSON);
        ArtifactoryStreamingResponse response = artifactory.streamingRestCall(request);
        assertFalse(response.isSuccessResponse());
        assertEquals(response.getStatusLine().getStatusCode(), 404);
        String raw = IOUtils.toString(response.getInputStream(), StandardCharsets.UTF_8);
        assertEquals(raw, "{\n" +
                "  \"errors\" : [ {\n" +
                "    \"status\" : 404,\n" +
                "    \"message\" : \"File not found.\"\n" +
                "  } ]\n" +
                "}");
    }
}
