package org.jfrog.artifactory.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public interface PermissionTargetV2 {
    String getName();
    PermissionV2 getRepo();
    PermissionV2 getBuild();
    PermissionV2 getReleaseBundle();
    @Override
    boolean equals(Object o);
}
