package org.jfrog.artifactory.client.v2.model.permissions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public interface BuildPermission {
    String getRegex();

    List<String> getBuilds();

    Actions getActions();
}
