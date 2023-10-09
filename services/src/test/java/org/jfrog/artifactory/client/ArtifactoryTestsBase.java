package org.jfrog.artifactory.client;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jfrog.artifactory.client.model.LocalRepository;
import org.jfrog.artifactory.client.model.Repository;
import org.jfrog.artifactory.client.model.repository.settings.impl.MavenRepositorySettingsImpl;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Properties;

import static org.apache.commons.codec.binary.Base64.encodeBase64;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.remove;

/**
 * @author jbaruch
 * @since 30/07/12
 */
public abstract class ArtifactoryTestsBase {
    protected static final String PATH = "m/a/b/c.txt";
    protected static final String PATH_PROPS = "m/a/b/p.txt";
    protected static final String JCENTER = "java-client-jcenter";
    protected static final String JCENTER_URL = "https://jcenter.bintray.com";
    protected static final String LIST_PATH = "api/repositories";
    private static final String CLIENTTESTS_ARTIFACTORY_ENV_VAR_PREFIX = "CLIENTTESTS_ARTIFACTORY_";
    private static final String CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX = "clienttests.artifactory.";
    protected Artifactory artifactory;
    protected String username;
    private String password;
    protected String url;
    protected String filePath;
    protected long fileSize;
    protected String fileMd5;
    protected String fileSha1;
    protected LocalRepository localRepository;
    protected String federationUrl;

    @BeforeClass
    public void init() throws IOException {
        String localRepositoryKey = "java-client-" + getClass().getSimpleName();
        Properties props = new Properties();
        // This file is not in GitHub. Create your own in src/test/resources.
        InputStream inputStream = this.getClass().getResourceAsStream("/artifactory-client.properties");
        if (inputStream != null) {
            props.load(inputStream);
        }

        url = readParam(props, "url", "http://localhost:8081/artifactory");
        if (!url.endsWith("/")) {
            url += "/";
        }
        username = readParam(props, "username", "admin");
        password = readParam(props, "password", "password");
        filePath = "a/b";
        fileSize = 141185;
        fileMd5 = "8f17d4271b86478a2731deebdab8c846";
        fileSha1 = "6c98d6766e72d5575f96c9479d1c1d3b865c6e25";
        artifactory = ArtifactoryClientBuilder.create()
                .setUrl(url)
                .setUsername(username)
                .setPassword(password)
                .build();
        deleteRepoIfExists(localRepositoryKey);
        deleteRepoIfExists(getJCenterRepoName());
        localRepository = artifactory.repositories().builders().localRepositoryBuilder()
                .key(localRepositoryKey)
                .description("new local repository")
                .repositorySettings(new MavenRepositorySettingsImpl())
                .propertySets(Arrays.asList("artifactory"))
                .build();

        if (!artifactory.repository(localRepository.getKey()).exists()) {
            artifactory.repositories().create(1, localRepository);
        }

        String jcenterRepoName = getJCenterRepoName();
        if (!artifactory.repository(jcenterRepoName).exists()) {
            Repository jcenter = artifactory.repositories().builders().remoteRepositoryBuilder()
                    .key(jcenterRepoName)
                    .url(JCENTER_URL)
                    .repositorySettings(new MavenRepositorySettingsImpl())
                    .build();
            artifactory.repositories().create(1, jcenter);
        }
    }

    public static String readParam(Properties props, String paramName, String defaultValue) {
        return StringUtils.firstNonBlank(
                props.getProperty(CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX + paramName),
                System.getProperty(CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX + paramName),
                System.getenv(CLIENTTESTS_ARTIFACTORY_ENV_VAR_PREFIX + paramName.toUpperCase()),
                defaultValue);
    }

    @AfterClass
    public void clean() {
        deleteRepoIfExists(localRepository.getKey());
        deleteRepoIfExists(getJCenterRepoName());
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

    protected String deleteRepoIfExists(String repoName) {
        if (isEmpty(repoName)) {
            return null;
        }

        try {
            return artifactory.repository(repoName).delete();
        } catch (Exception e) {
            if (e instanceof HttpResponseException && ((HttpResponseException) e).getStatusCode() == 404) {
                //if repo wasn't found - that's ok.
                return e.getMessage();
            } else {
                throw e;
            }
        }
    }
    protected void deletePermissionTargetV2IfExists(String name){
        if (StringUtils.isBlank(name)) { return ; }
        try {
            artifactory.security().deletePermissionTargetV2(name);
        } catch (Exception e) {
            if (e instanceof HttpResponseException && ((HttpResponseException)e).getStatusCode() == 404) {
                return;
            } else {
                throw e;
            }
        }
    }

    protected void deleteUserIfExists(String userName){
        if (StringUtils.isBlank(userName)) { return ; }
        try {
            artifactory.security().deleteUser(userName);
        } catch (Exception e) {
            if (e instanceof HttpResponseException && ((HttpResponseException)e).getStatusCode() == 404) {
                return;
            } else {
                throw e;
            }
        }
    }

    protected void deleteGroupIfExists(String groupName){
        if (StringUtils.isBlank(groupName)) { return ; }
        try {
            artifactory.security().deleteGroup(groupName);
        } catch (Exception e) {
            if (e instanceof HttpResponseException && ((HttpResponseException)e).getStatusCode() == 404) {
                return;
            } else {
                throw e;
            }
        }
    }

    public static String calcSha1(InputStream content) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA1");

            byte[] dataBytes = new byte[1024];
            int nread;
            while ((nread = content.read(dataBytes)) != -1) {
                md.update(dataBytes, 0, nread);
            }

            byte[] mdbytes = md.digest();

            //convert the byte to hex format
            StringBuilder sb = new StringBuilder("");
            for (int i = 0; i < mdbytes.length; i++) {
                sb.append(Integer.toString((((mdbytes[i] & 0xff) + 0x100)), 16).substring(1));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException | IOException e) {
            return null;
        }
    }

    protected String getJCenterRepoName() {
        return JCENTER + getClass().getSimpleName();
    }

    protected String getJcenterCacheName() {
        return getJCenterRepoName() + "-cache";
    }
}
