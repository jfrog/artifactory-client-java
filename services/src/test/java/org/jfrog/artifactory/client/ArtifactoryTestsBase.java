package org.jfrog.artifactory.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import static org.apache.commons.codec.binary.Base64.encodeBase64;
import static org.apache.commons.lang.StringUtils.isEmpty;
import static org.apache.commons.lang.StringUtils.remove;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import static org.testng.Assert.fail;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

/**
 * @author jbaruch
 * @since 30/07/12
 */
public abstract class ArtifactoryTestsBase {
    protected static final String NEW_LOCAL = "ext-release-local";
    protected static final String PATH = "m/a/b/c.txt";
    protected static final String PATH_PROPS = "m/a/b/p.txt";
    protected static final String LIBS_RELEASE_LOCAL = "libs-release-local";
    protected static final String LIBS_RELEASE_VIRTUAL = "libs-release";
    protected static final String JCENTER = "jcenter";
    protected static final String JCENTER_CACHE = JCENTER + "-cache";
    protected static final String LIST_PATH = "api/repositories";
    private static final String CLIENTTESTS_ARTIFACTORY_ENV_VAR_PREFIX = "CLIENTTESTS_ARTIFACTORY_";
    private static final String CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX = "clienttests.artifactory.";
    protected Artifactory artifactory;
    protected Artifactory threadSafeArtifactory;
    protected String username;
    private String password;
    protected String url;
    protected String filePath;
    protected long fileSize;
    protected String fileMd5;
    protected String fileSha1;

    @BeforeClass
    public void init() throws IOException {

        Properties props = new Properties();
        // This file is not in GitHub. Create your own in src/test/resources.
        InputStream inputStream = this.getClass().getResourceAsStream("/artifactory-client.properties");
        if (inputStream != null) {
            props.load(inputStream);
        }

        url = readParam(props, "url");
        if (!url.endsWith("/")) {
            url += "/";
        }
        username = readParam(props, "username");
        password = readParam(props, "password");


        filePath = "a/b";
        fileSize = 141185;
        fileMd5 = "8f17d4271b86478a2731deebdab8c846";
        fileSha1 = "6c98d6766e72d5575f96c9479d1c1d3b865c6e25";


        artifactory = ArtifactoryClientBuilder.create()
                .setUrl(url)
                .setUsername(username)
                .setPassword(password)
                .build();
    }

    private String readParam(Properties props, String paramName) {
        String paramValue = null;
        if (props.size() > 0) {
            paramValue = props.getProperty(CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX + paramName);
        }
        if (paramValue == null) {
            paramValue = System.getProperty(CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX + paramName);
        }
        if (paramValue == null) {
            paramValue = System.getenv(CLIENTTESTS_ARTIFACTORY_ENV_VAR_PREFIX + paramName.toUpperCase());
        }
        if (paramValue == null) {
            failInit();
        }
        return paramValue;
    }

    private void failInit() {
        String message =
                new StringBuilder("Failed to load test Artifactory instance credentials. ")
                        .append("Looking for System properties '")
                        .append(CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX)
                        .append("url', ")
                        .append(CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX)
                        .append("username' and ")
                        .append(CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX)
                        .append("password' or a properties file with those properties in classpath ")
                        .append("or Environment variables '")
                        .append(CLIENTTESTS_ARTIFACTORY_ENV_VAR_PREFIX).append("URL', ")
                        .append(CLIENTTESTS_ARTIFACTORY_ENV_VAR_PREFIX).append("USERNAME' and ")
                        .append(CLIENTTESTS_ARTIFACTORY_ENV_VAR_PREFIX).append("PASSWORD'").toString();

        fail(message);
    }

    @AfterClass
    public void clean() throws IOException {
        artifactory.close();
    }

    protected String curl(String path, String method) throws IOException {
        String authStringEnc = new String(encodeBase64((username + ":" + password).getBytes()));
        CloseableHttpResponse response = null;
        String responseString = null;
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpUriRequest request = createRequest(path, method);
            request.addHeader("Authorization", "Basic " + authStringEnc);
            response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            responseString = textFrom(entity.getContent());
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return responseString;
    }

    private HttpUriRequest createRequest(String path, String method) {
        HttpUriRequest request;
        switch (method.toLowerCase()) {
            case "get":
                request = new HttpGet(url + path);
                break;
            case "post":
                request = new HttpPost(url + path);
                break;
            default:
                throw new IllegalArgumentException("Http Method " + method + " is invalid");
        }
        return request;
    }

    protected String curl(String path) throws IOException {
        return curl(path, "GET");
    }

    protected String curlAndStrip(String path) throws IOException {
        return curlAndStrip(path, "GET");
    }

    protected String curlAndStrip(String path, String method) throws IOException {
        String result = curl(path, method);
        result = remove(result, ' ');
        result = remove(result, '\r');
        result = remove(result, '\n');
        return result;
    }

    protected String textFrom(InputStream is) throws IOException {
        try (InputStreamReader isr = new InputStreamReader(is)) {
            int numCharsRead;
            char[] charArray = new char[1024];
            StringBuilder sb = new StringBuilder();
            while ((numCharsRead = isr.read(charArray)) > 0) {
                sb.append(charArray, 0, numCharsRead);
            }
            return sb.toString();
        }
    }

    protected String deleteRepoIfExists(String repoName) throws IOException {
        if (isEmpty(repoName)) {
            return null;
        }

        try {
            String result = artifactory.repository(repoName).delete();
            return result;
        } catch (Exception e) {
            if (e.getMessage().equals("Not Found")) { //if repo wasn't found - that's ok.
                return e.getMessage();
            } else {
                throw e;
            }
        }
    }
}
