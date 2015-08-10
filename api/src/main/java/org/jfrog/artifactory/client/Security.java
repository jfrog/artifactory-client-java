package org.jfrog.artifactory.client;

import org.jfrog.artifactory.client.model.Group;
import org.jfrog.artifactory.client.model.PermissionTarget;
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
public interface Security {

    SecurityBuilders builders();

    Collection<String> userNames();

    User user(String name);

    Group group(String name);

    List<String> groupNames();

    PermissionTarget permissionTarget(String name);

    List<String> permissionTargets();

    void createOrUpdate(User user);

    void createOrUpdateGroup(Group group);

    String deleteUser(String name);

    String deleteGroup(String name);

    String getSecurityApi();

    String getSecurityUsersApi();

    String getSecurityPermissionsApi();

    String getSecurityUserGroupsApi();
}
