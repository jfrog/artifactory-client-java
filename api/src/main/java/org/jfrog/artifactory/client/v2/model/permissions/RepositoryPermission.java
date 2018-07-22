package org.jfrog.artifactory.client.v2.model.permissions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public interface RepositoryPermission {
    List<String> getIncludePatterns();

    List<String> getExcludePatterns();

    List<String> getRepositories();

    Actions getActions();
}
