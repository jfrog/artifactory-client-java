package org.jfrog.artifactory.client;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

import static org.apache.commons.codec.binary.Base64.encodeBase64;
import static org.apache.commons.lang.StringUtils.remove;
import static org.jfrog.artifactory.client.ArtifactoryClient.create;
import static org.testng.Assert.fail;

/**
 * @author jbaruch
 * @since 30/07/12
 */
public abstract class ArtifactoryTestsBase {
    protected static final String NEW_LOCAL = "new-local";
    protected static final String PATH = "m/a/b/c.txt";
    protected static final String LIBS_RELEASE_LOCAL = "libs-release-local";
    protected static final String LIBS_RELEASE_VIRTUAL = "libs-release";
    protected static final String JCENTER = "jcenter";
    protected static final String JCENTER_CACHE = JCENTER + "-cache";
    private static final String CLIENTTESTS_ARTIFACTORY_ENV_VAR_PREFIX = "CLIENTTESTS_ARTIFACTORY_";
    private static final String CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX = "clienttests.artifactory.";
    protected Artifactory artifactory;
    protected String username;
    private String password;
    protected String url;

    @BeforeClass
    public void init() throws IOException {

        Properties props = new Properties();
        InputStream inputStream = this.getClass().getResourceAsStream(
                "/artifactory-client.properties");//this file is not in GitHub. Create your own in src/test/resources.
        if (inputStream != null) {
            props.load(inputStream);
            url = props.getProperty(CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX + "url");
            username = props.getProperty(CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX + "username");
            password = props.getProperty(CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX + "password");
        } else {
            url = System.getProperty(CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX + "url");
            if (url == null) {
                url = System.getenv(CLIENTTESTS_ARTIFACTORY_ENV_VAR_PREFIX + "URL");
            }
            if (url == null) {
                failInit();
            }
            username = System.getProperty(CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX + "username");
            if (username == null) {
                username = System.getenv(CLIENTTESTS_ARTIFACTORY_ENV_VAR_PREFIX + "USERNAME");
            }
            if (username == null) {
                failInit();
            }
            password = System.getProperty(CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX + "password");
            if (password == null) {
                password = System.getenv(CLIENTTESTS_ARTIFACTORY_ENV_VAR_PREFIX + "PASSWORD");
            }
            if (password == null) {
                failInit();
            }


        }
        artifactory = create(url, username, password);
    }

    private void failInit() {
        fail(
                "Failed to load test Artifactory instance credentials." +
                        "Looking for System properties '" + CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX + "url', 'clienttests.artifactory.username' and 'clienttests.artifactory.password', " +
                        "or properties file with those properties in classpath," +
                        "or Environment variables '" + CLIENTTESTS_ARTIFACTORY_ENV_VAR_PREFIX + "URL', 'CLIENTTESTS_ARTIFACTORY_USERNAME' and 'CLIENTTESTS_ARTIFACTORY_PASSWORD'");
    }

    @AfterClass
    public void clean() {
        artifactory.close();
    }

    protected String curl(String path, String method) throws IOException {
        String authStringEnc = new String(encodeBase64((username + ":" + password).getBytes()));
        URLConnection urlConnection = new URL(url + "/" + path).openConnection();
        urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);
        if (urlConnection instanceof HttpURLConnection) {
            ((HttpURLConnection) urlConnection).setRequestMethod(method);
        }
        try (InputStream is = urlConnection.getInputStream()) {
            return textFrom(is);
        }
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
}
