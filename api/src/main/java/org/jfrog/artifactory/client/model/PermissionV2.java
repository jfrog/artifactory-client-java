package org.jfrog.artifactory.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Map;
@JsonIgnoreProperties(ignoreUnknown = true)
public interface PermissionV2 {
    List<String> getIncludePatterns();
    List<String> getExcludePatterns();
    List<String> getRepositories();
    Actions getActions();
    @Override
    boolean equals(Object o);
}
