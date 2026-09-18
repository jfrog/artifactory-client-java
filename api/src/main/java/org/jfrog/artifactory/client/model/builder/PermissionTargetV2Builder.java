package org.jfrog.artifactory.client.model.builder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.jfrog.artifactory.client.model.PermissionTargetV2;
import org.jfrog.artifactory.client.model.PermissionV2;

@JsonIgnoreProperties(ignoreUnknown = true)
public interface PermissionTargetV2Builder {

    PermissionTargetV2Builder name(String name);
    PermissionTargetV2Builder repo(PermissionV2 repo);
    PermissionTargetV2Builder build(PermissionV2 build);
    PermissionTargetV2Builder releaseBundle(PermissionV2 releaseBundle);
    PermissionTargetV2 build();

}
