/* Copyright 2013 Yahoo! Inc. Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or 
 * agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR 
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and 
 * limitations under the License. See accompanying LICENSE file.  */

package org.jfrog.artifactory.client.ning;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.HttpResponseException;
import org.jfrog.artifactory.client.Artifactory;
import org.jfrog.artifactory.client.DownloadableArtifact;
import org.jfrog.artifactory.client.ItemHandle;
import org.jfrog.artifactory.client.RepositoryHandle;
import org.jfrog.artifactory.client.UploadableArtifact;
import org.jfrog.artifactory.client.model.File;
import org.jfrog.artifactory.client.model.Item;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClient.BoundRequestBuilder;
import com.ning.http.client.AsyncHttpClientConfig;
import com.ning.http.client.cookie.Cookie;

/**
 * @author charlesk
 */
public class ArtifactoryNingClientTest {
	private String url;
	private String repo;
	private String username = null;
	private String password = null;
	private String filePath;
	private String fileName;
	private TestNingRequestImpl testNingRequestImpl = new TestNingRequestImpl();
	private Artifactory artifactory;
	private static final String CLIENTTESTS_ARTIFACTORY_ENV_VAR_PREFIX = "CLIENTTESTS_ARTIFACTORY_";
	private static final String CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX = "clienttests.artifactory.";

	@BeforeClass
	public void init() throws Exception {
		Properties props = new Properties();
		// this file is not in GitHub. Create your own in src/test/resources.
		InputStream inputStream = this.getClass().getResourceAsStream(
				"/artifactory-ning-client.properties");
		if (inputStream != null) {
			props.load(inputStream);
			url = props.getProperty(CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX
					+ "url");
			username = props
					.getProperty(CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX
							+ "username");
			password = props
					.getProperty(CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX
							+ "password");
			repo = props.getProperty(CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX
					+ "repo");
			filePath = props
					.getProperty(CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX
							+ "filepath");
			fileName = props
					.getProperty(CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX
							+ "filename");
		} else {
			// url
			url = System.getProperty(CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX
					+ "url");
			if (url == null) {
				url = System.getenv(CLIENTTESTS_ARTIFACTORY_ENV_VAR_PREFIX
						+ "URL");
			}
			if (url == null) {
				failInit();
			}
			// username
			username = System
					.getProperty(CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX
							+ "username");
			if (username == null) {
				username = System.getenv(CLIENTTESTS_ARTIFACTORY_ENV_VAR_PREFIX
						+ "USERNAME");
			}
			if (username == null) {
				failInit();
			}
			// password
			password = System
					.getProperty(CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX
							+ "password");
			if (password == null) {
				password = System.getenv(CLIENTTESTS_ARTIFACTORY_ENV_VAR_PREFIX
						+ "PASSWORD");
			}
			if (password == null) {
				failInit();
			}
			// repo
			repo = System.getProperty(CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX
					+ "repo");
			if (repo == null) {
				repo = System.getenv(CLIENTTESTS_ARTIFACTORY_ENV_VAR_PREFIX
						+ "REPO");
			}
			if (repo == null) {
				failInit();
			}
			// filepath
			filePath = System
					.getProperty(CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX
							+ "filepath");
			if (filePath == null) {
				filePath = System.getenv(CLIENTTESTS_ARTIFACTORY_ENV_VAR_PREFIX
						+ "FILEPATH");
			}
			if (filePath == null) {
				failInit();
			}
			// fileName
			fileName = System
					.getProperty(CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX
							+ "filename");
			if (fileName == null) {
				fileName = System.getenv(CLIENTTESTS_ARTIFACTORY_ENV_VAR_PREFIX
						+ "FILENAME");
			}
			if (fileName == null) {
				failInit();
			}
		}
		artifactory = org.jfrog.artifactory.client.ning.ArtifactoryClient
				.create(url, username, password.toCharArray(),
						testNingRequestImpl);
		// Upload first.
		testUpload(filePath + "/" + fileName, "Test upload".getBytes("UTF-8"));
	}

	@AfterClass
	public void clean() {
		artifactory.close();
	}

	@Test
	public void testCreateArtifactoryClient() throws Exception {
		ExecutorService executorService = new ThreadPoolExecutor(20, 100, 60L,
				TimeUnit.SECONDS, new SynchronousQueue<Runnable>(),
				new ThreadFactory() {
					public Thread newThread(Runnable r) {
						Thread t = new Thread(r, "AsyncHttpClient-Callback");
						t.setDaemon(true);
						return t;
					}
				});
		AsyncHttpClient ningHttpClient = new AsyncHttpClient(
				new AsyncHttpClientConfig.Builder().setExecutorService(
						executorService).build());
		Artifactory artifactory = org.jfrog.artifactory.client.ning.ArtifactoryClient
				.create(url, ningHttpClient, testNingRequestImpl);
		RepositoryHandle repositoryHandle = artifactory.repository(repo);
		Assert.assertNotNull(repositoryHandle);
		ItemHandle itemHandle = repositoryHandle
				.file(filePath + "/" + fileName);
		Assert.assertNotNull(itemHandle);
		Item item = itemHandle.info();
		Assert.assertNotNull(item);
	}

	@Test
	public void testGetMetaData() throws Exception {
		// meta-data Get
		RepositoryHandle repositoryHandle = artifactory.repository(repo);
		Assert.assertNotNull(repositoryHandle);
		ItemHandle itemHandle = repositoryHandle
				.file(filePath + "/" + fileName);
		Assert.assertNotNull(itemHandle);
		Item item = itemHandle.info();
		Assert.assertNotNull(item);
	}

	@Test
	public void testDownloadContent() throws Exception {
		// download contents
		RepositoryHandle repositoryHandle = artifactory.repository(repo);
		Assert.assertNotNull(repositoryHandle);
		DownloadableArtifact downloadableArtifact = repositoryHandle
				.download(filePath + "/" + fileName);
		Assert.assertNotNull(downloadableArtifact);
		InputStream content = downloadableArtifact.doDownload();
		Assert.assertNotNull(content);
	}

	@Test
	public void test404() throws Exception {
		RepositoryHandle repositoryHandle = artifactory.repository(repo);
		Assert.assertNotNull(repositoryHandle);
		ItemHandle itemHandle = repositoryHandle.file(filePath + "/" + fileName
				+ "NOT_FOUND");
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

	private void testUpload(String fileName, byte[] contentByte)
			throws Exception {
		// upload
		InputStream content = new ByteArrayInputStream(contentByte);
		RepositoryHandle repositoryHandle = artifactory.repository(repo);
		Assert.assertNotNull(repositoryHandle);
		UploadableArtifact uploadableArtifact = repositoryHandle.upload(
				fileName, content);
		Assert.assertNotNull(uploadableArtifact);
		File file = uploadableArtifact.doUpload();
		Assert.assertNotNull(file);
		// md5
		uploadableArtifact = repositoryHandle.upload(fileName + ".md5",
				new ByteArrayInputStream(MD5(contentByte).getBytes()));
		Assert.assertNotNull(uploadableArtifact);
		file = uploadableArtifact.doUpload();
		// sha1
		uploadableArtifact = repositoryHandle.upload(fileName + ".sha1",
				new ByteArrayInputStream(Sha1(contentByte).getBytes()));
		Assert.assertNotNull(uploadableArtifact);
		file = uploadableArtifact.doUpload();
	}

	private String MD5(byte[] md5) throws Exception {
		java.security.MessageDigest md = java.security.MessageDigest
				.getInstance("MD5");
		byte[] array = md.digest(md5);
		return byteArrayToHexString(array);
	}

	private String Sha1(byte[] sha1) throws Exception {
		java.security.MessageDigest md = java.security.MessageDigest
				.getInstance("SHA1");
		byte[] array = md.digest(sha1);
		return byteArrayToHexString(array);
	}

	private static final String byteArrayToHexString(byte[] byteArray) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteArray.length; ++i) {
			sb.append(Integer.toHexString((byteArray[i] & 0xFF) | 0x100)
					.substring(1, 3));
		}
		return sb.toString();
	}

	private void failInit() {
		StringBuilder failMessage = new StringBuilder(
				"Failed to load test Artifactory instance credentials.");
		failMessage
				.append("Looking for System properties ")
				.append(CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX)
				.append("url ")
				.append(CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX)
				.append("username ")
				.append(CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX)
				.append("password ")
				.append(CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX)
				.append("repo ")
				.append(CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX)
				.append("filepath ")
				.append(CLIENTTESTS_ARTIFACTORY_PROPERTIES_PREFIX)
				.append("filename ")
				.append("or properties file with those properties in classpath, ")
				.append("or Environment variables ")
				.append(CLIENTTESTS_ARTIFACTORY_ENV_VAR_PREFIX).append("URL ")
				.append(CLIENTTESTS_ARTIFACTORY_ENV_VAR_PREFIX)
				.append("USERNAME ")
				.append(CLIENTTESTS_ARTIFACTORY_ENV_VAR_PREFIX)
				.append("PASSWORD ")
				.append(CLIENTTESTS_ARTIFACTORY_ENV_VAR_PREFIX).append("REPO ")
				.append(CLIENTTESTS_ARTIFACTORY_ENV_VAR_PREFIX)
				.append("FILEPATH ")
				.append(CLIENTTESTS_ARTIFACTORY_ENV_VAR_PREFIX)
				.append("FILENAME ");
		Assert.fail(failMessage.toString());
	}

	class TestNingRequestImpl implements
			org.jfrog.artifactory.client.ning.NingRequest {
		public BoundRequestBuilder getBoundRequestBuilder(
				BoundRequestBuilder requestBuilder) {
			return requestBuilder.addCookie(Cookie.newValidCookie("name", "value",
					".com", "value", "/", -1, -1, false, false)).addHeader("header_name", "header_value");

		}
	}
}
