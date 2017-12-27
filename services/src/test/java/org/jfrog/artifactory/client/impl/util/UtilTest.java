package org.jfrog.artifactory.client.impl.util;

import org.apache.commons.lang.NullArgumentException;
import org.apache.http.entity.ContentType;
import org.testng.annotations.Test;

import java.net.URL;

import org.jfrog.artifactory.client.ArtifactoryRequest;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class UtilTest {

  @Test
  public void getContentTypeJson() {
    final ContentType contentType = Util.getContentType(ArtifactoryRequest.ContentType.JSON);
    assertThat(contentType, is(ContentType.APPLICATION_JSON));
  }

  @Test
  public void getContentTypeText() {
    final ContentType contentType = Util.getContentType(ArtifactoryRequest.ContentType.TEXT);
    assertThat(contentType, is(ContentType.TEXT_PLAIN));
  }

  @Test
  public void getContentTypeUrlEnc() {
    final ContentType contentType = Util.getContentType(ArtifactoryRequest.ContentType.URLENC);
    assertThat(contentType, is(ContentType.APPLICATION_FORM_URLENCODED));
  }

  @Test
  public void getContentTypeAny() {
    final ContentType contentType = Util.getContentType(ArtifactoryRequest.ContentType.ANY);
    assertThat(contentType, is(ContentType.WILDCARD));
  }

  @Test(expectedExceptions = NullArgumentException.class)
  public void getContentTypeNull() {
    Util.getContentType(null);
  }

  @Test
  public void createUrlOk(){
    final String urlString = "http://localhost/artifactory";
    URL url = Util.createUrl(urlString);
    assertThat(url.toString(), equalTo(urlString));
  }

  @Test(expectedExceptions = NullArgumentException.class)
  public void createUrlNull(){
    URL url = Util.createUrl(null);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void createUrlEmpty(){
    URL url = Util.createUrl("");
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void createUrlInvalid(){
    URL url = Util.createUrl("invalid url");
  }
}
