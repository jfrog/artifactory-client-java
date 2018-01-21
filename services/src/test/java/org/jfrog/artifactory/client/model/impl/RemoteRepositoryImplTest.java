package org.jfrog.artifactory.client.model.impl;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import org.jfrog.artifactory.client.model.Repository;
import org.jfrog.artifactory.client.model.builder.impl.RepositoryBuildersImpl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class RemoteRepositoryImplTest {

  @Test
  public void testCustomPropertiesHashCode() {
    Repository repository0 = RepositoryBuildersImpl.create().localRepositoryBuilder()
        .key("repo-key")
        .build();
    int hashCode0 = repository0.hashCode();

    Repository repository1 = RepositoryBuildersImpl.create().localRepositoryBuilder()
        .key("repo-key")
        .build();
    int hashCode1 = repository1.hashCode();

    assertThat(hashCode0, equalTo(hashCode1));

    Map<String, Object> customProperties = new HashMap<>();
    customProperties.put("key", "val");
    Repository repository2 = RepositoryBuildersImpl.create().localRepositoryBuilder()
        .key("repo-key")
        .customProperties(customProperties)
        .build();
    int hashCode2 = repository2.hashCode();
    
    assertThat(hashCode1, not(equalTo(hashCode2)));
  }
}
