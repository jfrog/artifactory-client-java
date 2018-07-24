package org.jfrog.artifactory.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.jfrog.artifactory.client.model.Group;
import org.jfrog.artifactory.client.model.PermissionTarget;
import org.jfrog.artifactory.client.model.PermissionTargetV1;
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

    /**
     * Since Artifactory 6.2.0, use permissionTarget instead.
     */
    @Deprecated
    PermissionTargetV1 permissionTargetV1(String name);

    /**
     * Since Artifactory 6.2.0, use permissionTargets instead.
     */
    @Deprecated
    List<String> permissionTargetsV1();

    void createOrUpdate(User user);

    void createOrUpdateGroup(Group group);

    /**
     * Since Artifactory 6.2.0, use createPermissionTarget instead.
     */
    @Deprecated
    void createOrReplacePermissionTargetV1(PermissionTargetV1 permissionTargetV1);

    String deleteUser(String name);

    String deleteGroup(String name);

    /**
     * Since Artifactory 6.2.0, use deletePermissionTarget instead.
     */
    @Deprecated
    String deletePermissionTargetV1(String name);

    PermissionTarget permissionTarget(String name);

    List<String> permissionTargets();

    void createPermissionTarget(PermissionTarget permissionTarget);

    void updatePermissionTarget(PermissionTarget permissionTarget);

    String deletePermissionTarget(String name);

    String getSecurityV1Api();

    String getSecurityApi();

    String getSecurityUsersApi();

    String getSecurityPermissionsV1Api();

    String getSecurityPermissionsApi();

    String getSecurityUserGroupsApi();
}
