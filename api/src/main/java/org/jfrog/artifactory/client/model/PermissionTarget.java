package org.jfrog.artifactory.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public interface PermissionTarget {

    String getName();

    @JsonProperty("build")
    BuildPermission getBuildPermission();

    @JsonProperty("repo")
    RepositoryPermission getRepositoryPermission();
}
