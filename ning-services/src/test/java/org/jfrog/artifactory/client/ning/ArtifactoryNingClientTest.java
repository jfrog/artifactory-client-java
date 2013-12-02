/* Copyright 2013 Yahoo! Inc. Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or 
 * agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR 
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and 
 * limitations under the License. See accompanying LICENSE file.  */

package org.jfrog.artifactory.client.ning;

import com.ning.http.client.AsyncHttpClient.BoundRequestBuilder;
import com.ning.http.client.Cookie;
import org.apache.http.client.HttpResponseException;
import org.jfrog.artifactory.client.*;
import org.jfrog.artifactory.client.model.File;
import org.jfrog.artifactory.client.model.Item;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author charlesk
 */
public class ArtifactoryNingClientTest {

    private String url;
    private String repo;
    private String username = null;
    private char[] password = null;
    private String filePath;
    private String fileName;
    private TestNingRequestImpl testNingRequestImpl = new TestNingRequestImpl();
    private Artifactory artifactory;
    private static final String CLIENTTESTS_ARTIFACTORY_ENV_VAR_PREFIX = "CLIENTTESTS_ARTIFACTORY_";
    private static final String CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX = "clienttests.artifactory.";

    @BeforeClass
    public void init() throws Exception {
        Properties props = new Properties();
        //this file is not in GitHub. Create your own in src/test/resources.
        InputStream inputStream = this.getClass().getResourceAsStream(
                "/artifactory-ning-client.properties");
        if (inputStream != null) {
            props.load(inputStream);
        }
        //url
        url = props.getProperty(CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX + "url");
        if (url == null) {
            url = System.getProperty(CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX + "url");
        }
        if (url == null) {
            url = System.getenv(CLIENTTESTS_ARTIFACTORY_ENV_VAR_PREFIX + "URL");
        }
        if (url == null) {
            failInit();
        }
        //username
        username = props.getProperty(CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX + "username");
        if (username == null) {
            username = System.getProperty(CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX + "username");
        }
        if (username == null) {
            username = System.getenv(CLIENTTESTS_ARTIFACTORY_ENV_VAR_PREFIX + "USERNAME");
        }
        if (username == null) {
            failInit();
        }
        //password
        password = props.getProperty(CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX + "password").toCharArray();
        if (password == null) {
            password = System.getProperty(CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX + "password").toCharArray();
        }        if (password == null) {
            password = System.getenv(CLIENTTESTS_ARTIFACTORY_ENV_VAR_PREFIX + "PASSWORD").toCharArray();
        }
        if (password == null) {
            failInit();
        }
        //repo
        repo = props.getProperty(CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX + "repo");
        if (repo == null) {
            repo = System.getProperty(CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX + "repo");
        }        if (repo == null) {
            repo = System.getenv(CLIENTTESTS_ARTIFACTORY_ENV_VAR_PREFIX + "REPO");
        }
        if (repo == null) {
            failInit();
        }
        //filepath
        filePath = props.getProperty(CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX + "filepath");
        if (filePath == null) {
            filePath = System.getProperty(CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX + "filepath");
        }        if (filePath == null) {
            filePath = System.getenv(CLIENTTESTS_ARTIFACTORY_ENV_VAR_PREFIX + "FILEPATH");
        }
        if (filePath == null) {
            failInit();
        }
        //fileName
        fileName = props.getProperty(CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX + "filename");
        if (fileName == null) {
            fileName = System.getProperty(CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX + "filename");
        }        if (fileName == null) {
            fileName = System.getenv(CLIENTTESTS_ARTIFACTORY_ENV_VAR_PREFIX + "FILENAME");
        }
        if (fileName == null) {
            failInit();
        }

        artifactory = org.jfrog.artifactory.client.ning.ArtifactoryClient.create(url, username, password, testNingRequestImpl);

        //Upload first.
        testUpload();
    }

    @AfterClass
    public void clean() {
        artifactory.close();
    }

    @Test
    public void testGetMetaData() throws Exception {
        // meta-data Get
        RepositoryHandle repositoryHandle = artifactory.repository(repo);
        Assert.assertNotNull(repositoryHandle);
        ItemHandle itemHandle = repositoryHandle.file(filePath + "/" + fileName);
        Assert.assertNotNull(itemHandle);
        Item item = itemHandle.info();
        Assert.assertNotNull(item);
    }

    @Test
    public void testDownloadContent() throws Exception {
        // download contents
        RepositoryHandle repositoryHandle = artifactory.repository(repo);
        Assert.assertNotNull(repositoryHandle);
        DownloadableArtifact downloadableArtifact = repositoryHandle.download(filePath + "/" + fileName);
        Assert.assertNotNull(downloadableArtifact);
        InputStream content = downloadableArtifact.doDownload();
        Assert.assertNotNull(content);
    }


    @Test
    public void test404() throws Exception {
        RepositoryHandle repositoryHandle = artifactory.repository(repo);
        Assert.assertNotNull(repositoryHandle);
        ItemHandle itemHandle = repositoryHandle.file(filePath + "/" + fileName + "NOT_FOUND");
        Assert.assertNotNull(itemHandle);
        try {
            Item item = itemHandle.info();
            Assert.fail("Should have failed.");
        } catch (Exception e) {
            if (!(e instanceof org.apache.http.client.HttpResponseException)) {
                throw e;
            }
            HttpResponseException status = (org.apache.http.client.HttpResponseException) e;
            // GOOD
            Assert.assertEquals(status.getStatusCode(), 404);
        }
    }

    private void testUpload() throws Exception {
        // upload
        InputStream content = new ByteArrayInputStream("I want to test my upload!".getBytes());
        RepositoryHandle repositoryHandle = artifactory.repository(repo);
        Assert.assertNotNull(repositoryHandle);
        UploadableArtifact uploadableArtifact = repositoryHandle.upload(filePath + "/" + fileName, content);
        Assert.assertNotNull(uploadableArtifact);
        File file = uploadableArtifact.doUpload();
        Assert.assertNotNull(file);
    }

    private void failInit() {
        StringBuilder failMessage = new StringBuilder("Failed to load test Artifactory instance credentials.");
        failMessage.append("Looking for System properties ")
                .append(CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX).append("url ")
                .append(CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX).append("username ")
                .append(CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX).append("password ")
                .append(CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX).append("repo ")
                .append(CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX).append("filepath ")
                .append(CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX).append("filename ")
                .append("or properties file with those properties in classpath, ")
                .append("or Environment variables ")
                .append(CLIENTTESTS_ARTIFACTORY_ENV_VAR_PREFIX).append("URL ")
                .append(CLIENTTESTS_ARTIFACTORY_ENV_VAR_PREFIX).append("USERNAME ")
                .append(CLIENTTESTS_ARTIFACTORY_ENV_VAR_PREFIX).append("PASSWORD ")
                .append(CLIENTTESTS_ARTIFACTORY_ENV_VAR_PREFIX).append("REPO ")
                .append(CLIENTTESTS_ARTIFACTORY_ENV_VAR_PREFIX).append("FILEPATH ")
                .append(CLIENTTESTS_ARTIFACTORY_ENV_VAR_PREFIX).append("FILENAME ");
        Assert.fail(failMessage.toString());
    }

    class TestNingRequestImpl implements org.jfrog.artifactory.client.ning.NingRequest {
        public BoundRequestBuilder getBoundRequestBuilder(BoundRequestBuilder requestBuilder) {
            return requestBuilder.addCookie(new Cookie(".com", "name", "value", "/", -1, false))
                    .addHeader("header_name", "header_value");

        }
    }

}
