package org.jfrog.artifactory.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.jfrog.artifactory.client.model.Group;
import org.jfrog.artifactory.client.model.PermissionTarget;
import org.jfrog.artifactory.client.model.PermissionTargetV2;
import org.jfrog.artifactory.client.model.User;
import org.jfrog.artifactory.client.model.builder.SecurityBuilders;

import java.util.Collection;
import java.util.List;

/**
 * Date: 10/18/12
 * Time: 9:25 AM
 *
 * @author freds
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface Security {

    SecurityBuilders builders();

    Collection<String> userNames();

    User user(String name);

    Group group(String name);

    List<String> groupNames();

    PermissionTarget permissionTarget(String name);

    PermissionTargetV2 permissionTargetV2(String name);

    List<String> permissionTargets();

    void createOrUpdate(User user);

    void createOrUpdateGroup(Group group);

    void createOrReplacePermissionTarget(PermissionTarget permissionTarget);

    void createOrReplacePermissionTargetV2(PermissionTargetV2 permissionTarget);

    String deleteUser(String name);

    String deleteGroup(String name);

    String deletePermissionTarget(String name);

    String deletePermissionTargetV2(String name);

    String getSecurityApi();

    String getSecurityV2Api();

    String getSecurityUsersApi();

    String getSecurityPermissionsApi();

    String getSecurityPermissionsV2Api();

    String getSecurityUserGroupsApi();
}
