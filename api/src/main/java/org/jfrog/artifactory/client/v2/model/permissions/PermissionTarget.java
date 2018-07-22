package org.jfrog.artifactory.client.v2.model.permissions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public interface PermissionTarget {

    String getName();

    BuildPermission getBuild();

    RepositoryPermission getRepo();
}
