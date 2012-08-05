package org.artifactory.client;

import junit.framework.Assert;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

import static org.apache.commons.codec.binary.Base64.encodeBase64;
import static org.artifactory.client.Artifactory.create;

/**
 * @author jbaruch
 * @since 30/07/12
 */
public abstract class ArtifactoryTestBase {
    protected static final String NEW_LOCAL = "new-local";
    protected static final String PATH = "m/a/b/c.txt";
    protected static final String LIBS_RELEASES_LOCAL = "libs-releases-local";
    protected static final String REPO1 = "repo1";
    protected static final String REPO1_CACHE = REPO1 + "-cache";
    protected Artifactory artifactory;
    protected String username;
    private String password;
    protected String url;

    @BeforeMethod
    public void init() throws IOException {

        Properties props = new Properties();
        InputStream inputStream = this.getClass().getResourceAsStream(
                "/artifactory.client.properties");//this file is not in GitHub. Create your own in src/test/resources.
        if (inputStream == null) {
            Assert.fail(
                    "Credentials file is missing, create credentials.properties with 'username' and 'password' properties under src/test/resources");
        }
        props.load(inputStream);
        username = props.getProperty("username");
        password = props.getProperty("password");
        //url = "http://clienttests.artifactoryonline.com/clienttests";
        url = "http://localhost:8080/artifactory";
        artifactory = create(url, username, password);
    }

    protected String curl(String path) throws IOException {
        String authStringEnc = new String(encodeBase64((username + ":" + password).getBytes()));
        URLConnection urlConnection = new URL(url + "/" + path).openConnection();
        urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);
        try (InputStream is = urlConnection.getInputStream()) {
            return textFrom(is);
        }
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
