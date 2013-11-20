/* Copyright (c) 2013, Yahoo! Inc.  All rights reserved. */

package org.jfrog.artifactory.client;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.http.client.HttpResponseException;
import org.jfrog.artifactory.client.Artifactory;
import org.jfrog.artifactory.client.DownloadableArtifact;
import org.jfrog.artifactory.client.ItemHandle;
import org.jfrog.artifactory.client.RepositoryHandle;
import org.jfrog.artifactory.client.UploadableArtifact;
import org.jfrog.artifactory.client.model.File;
import org.jfrog.artifactory.client.model.Item;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.ning.http.client.Cookie;
import com.ning.http.client.AsyncHttpClient.BoundRequestBuilder;

/**
 * @author charlesk
 */
public class ArtifactoryClientTest {

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
        InputStream inputStream = this.getClass().getResourceAsStream(
                "/artifactory-client.properties");//this file is not in GitHub. Create your own in src/test/resources.
        if (inputStream != null) {
            props.load(inputStream);
            url = props.getProperty(CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX + "url");
            username = props.getProperty(CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX + "username");
            password = props.getProperty(CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX + "password").toCharArray();
            repo = props.getProperty(CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX + "repo");
            filePath = props.getProperty(CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX + "filepath");
            fileName = props.getProperty(CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX + "filename");
        } else {
            //url
            url = System.getProperty(CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX + "url");
            if (url == null) {
                url = System.getenv(CLIENTTESTS_ARTIFACTORY_ENV_VAR_PREFIX + "URL");
            }
            if (url == null) {
                failInit();
            }
            //repo
            repo = System.getProperty(CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX + "repo");
            if (repo == null) {
                repo = System.getenv(CLIENTTESTS_ARTIFACTORY_ENV_VAR_PREFIX + "REPO");
            }
            if (repo == null) {
                failInit();
            }
            //filepath
            filePath = System.getProperty(CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX + "filepath");
            if (filePath == null) {
                filePath = System.getenv(CLIENTTESTS_ARTIFACTORY_ENV_VAR_PREFIX + "FILEPATH");
            }
            if (filePath == null) {
                failInit();
            }
            //fileName
            fileName = System.getProperty(CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX + "filename");
            if (fileName == null) {
                fileName = System.getenv(CLIENTTESTS_ARTIFACTORY_ENV_VAR_PREFIX + "FILENAME");
            }
            if (fileName == null) {
                failInit();
            }
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
        Assert.fail(
                "Failed to load test Artifactory instance credentials." +
                        "Looking for System properties '" + CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX + "url', 'clienttests.artifactory.username' and 'clienttests.artifactory.password', " +
                        "or properties file with those properties in classpath," +
                        "or Environment variables '" + CLIENTTESTS_ARTIFACTORY_ENV_VAR_PREFIX + "URL', 'CLIENTTESTS_ARTIFACTORY_USERNAME' and 'CLIENTTESTS_ARTIFACTORY_PASSWORD'");
    }

    class TestNingRequestImpl implements org.jfrog.artifactory.client.ning.NingRequest {
        public BoundRequestBuilder getBoundRequestBuilder(BoundRequestBuilder requestBuilder){
            return requestBuilder.addCookie(new Cookie(".com", "name", "value", "/", -1, false))
                    .addHeader("header_name", "header_value");
            
        }
    }

}
