package org.jfrog.artifactory.client.v2;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.jfrog.artifactory.client.v2.model.builder.SecurityBuilders;
import org.jfrog.artifactory.client.v2.model.permissions.PermissionTarget;

import java.util.List;

/**
 * @author matank
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface Security {

    SecurityBuilders builders();

    PermissionTarget permissionTarget(String name);

    List<String> permissionTargets();

    void createPermissionTarget(PermissionTarget permissionTarget);

    void updatePermissionTarget(PermissionTarget permissionTarget);

    String deletePermissionTarget(String name);

    String getSecurityApi();

    String getSecurityPermissionsApi();

}
