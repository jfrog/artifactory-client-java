package org.jfrog.artifactory.client.model.impl;


import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;

import org.jfrog.artifactory.client.Artifactory;
import org.jfrog.artifactory.client.ArtifactoryClientBuilder;
import org.jfrog.artifactory.client.ArtifactoryTestsBase;
import org.jfrog.artifactory.client.model.RemoteRepository;
import org.jfrog.artifactory.client.model.repository.settings.impl.GenericRepositorySettingsImpl;

import static org.apache.commons.lang.StringUtils.isEmpty;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class RemoteRepositoryImplTest {
  private static final Random rnd = new Random();

  private Artifactory artifactory;
  private String localRepositoryKey;

  @BeforeClass
  public void initClass() throws IOException {
    Properties props = new Properties();
    // This file is not in GitHub. Create your own in src/test/resources.
    InputStream inputStream = this.getClass().getResourceAsStream("/artifactory-client.properties");
    if (inputStream != null) {
      props.load(inputStream);
    }

    String url = ArtifactoryTestsBase.readParam(props, "url");
    if (!url.endsWith("/")) {
      url += "/";
    }
    String username = ArtifactoryTestsBase.readParam(props, "username");
    String password = ArtifactoryTestsBase.readParam(props, "password");
    artifactory = ArtifactoryClientBuilder.create()
        .setUrl(url)
        .setUsername(username)
        .setPassword(password)
        .build();
  }

  @BeforeMethod
  public void initTest() throws IOException {
    localRepositoryKey = "remote-" + getClass().getSimpleName() + "-" + rnd.nextInt();
  }

  @AfterMethod
  public void cleanTest() throws IOException {
    deleteRepoIfExists(localRepositoryKey);
  }

  protected void deleteRepoIfExists(String repoName) throws IOException {
    if (isEmpty(repoName)) {
      return;
    }
    try {
      artifactory.repository(repoName).delete();
    } catch (Exception ignored) {
      int i =0;
    }
  }

  @AfterClass
  public void cleanClass() throws IOException {
    artifactory.close();
  }

  /**
   * We have not ability to create certificate through java client.
   * @TODOs Remove `(enabled = false)`. Create certificate with name "clientTlsCertificate".
   */
  @Test(enabled = false)
  public void testClientTlsCertificate() throws Exception {
    String clientTlsCertificate = "clientTlsCertificate";

    RemoteRepository remoteRepository = artifactory.repositories().builders().remoteRepositoryBuilder()
        .key(localRepositoryKey)
        .url("http://test.com/")
        .description("create remote repo with clientTlsCertificate")
        .clientTlsCertificate(clientTlsCertificate)
        .repositorySettings(new GenericRepositorySettingsImpl())
        .build();

    artifactory.repositories().create(1, remoteRepository);
    RemoteRepository repository = (RemoteRepository) artifactory.repository(remoteRepository.getKey()).get();

    assertNotNull(repository);
    assertEquals(repository.getClientTlsCertificate(), clientTlsCertificate);
  }
}
