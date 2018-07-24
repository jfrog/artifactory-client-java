package org.jfrog.artifactory.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public interface PermissionTarget {

    String getName();

    BuildPermission getBuild();

    RepositoryPermission getRepo();
}
