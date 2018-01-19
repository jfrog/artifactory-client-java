package org.jfrog.artifactory.client.model.impl;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import org.jfrog.artifactory.client.model.Repository;
import org.jfrog.artifactory.client.model.builder.impl.RepositoryBuildersImpl;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class LocalRepositoryImplTest {

  @Test
  public void testEquals() {
    Repository repository1 = RepositoryBuildersImpl.create().localRepositoryBuilder()
        .key("repo-key-1")
        .build();

    Repository repository2 = RepositoryBuildersImpl.create().localRepositoryBuilder()
        .key("repo-key-2")
        .build();

    boolean actual = repository1.equals(repository2);

    assertThat(actual, is(false));
  }

  @Test
  public void testCustomPropertiesEquals() {
    Map<String, Object> customProperties1 = new HashMap<>();
    customProperties1.put("key", "val");

    Repository repository1 = RepositoryBuildersImpl.create().localRepositoryBuilder()
        .key("repo-key")
        .customProperties(customProperties1)
        .build();

    Map<String, Object> customProperties2 = new HashMap<>();
    customProperties2.put("key", "val");
    Repository repository2 = RepositoryBuildersImpl.create().localRepositoryBuilder()
        .key("repo-key")
        .customProperties(customProperties2)
        .build();

    boolean actual = repository1.equals(repository2);

    assertThat(actual, is(true));
  }

  @Test
  public void testCustomPropertiesNotEquals() {
    Map<String, Object> customProperties1 = new HashMap<>();
    customProperties1.put("key", "val");

    Repository repository1 = RepositoryBuildersImpl.create().localRepositoryBuilder()
        .key("repo-key")
        .customProperties(customProperties1)
        .build();

    Map<String, Object> customProperties2 = new HashMap<>();
    customProperties1.put("key", "val");

    Repository repository2 = RepositoryBuildersImpl.create().localRepositoryBuilder()
        .key("repo-key")
        .customProperties(customProperties2)
        .build();

    boolean actual = repository1.equals(repository2);

    assertThat(actual, is(false));
  }
}
