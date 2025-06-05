package org.jfrog.artifactory.client.model.builder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.jfrog.artifactory.client.model.Actions;
import org.jfrog.artifactory.client.model.PermissionV2;

@JsonIgnoreProperties(ignoreUnknown = true)
public interface PermissionV2Builder {

    PermissionV2Builder includePatterns(String... includePatterns);
    PermissionV2Builder excludePatterns(String... excludePatterns);
    PermissionV2Builder repositories(String... repositories);
    PermissionV2Builder actions(Actions actions);
    PermissionV2 build();
}
