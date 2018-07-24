package org.jfrog.artifactory.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public interface BuildPermission {
    String getRegex();

    List<String> getBuilds();

    Actions getActions();
}
