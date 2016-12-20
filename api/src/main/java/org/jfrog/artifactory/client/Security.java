package org.jfrog.artifactory.client;

import java.util.Collection;
import java.util.List;

import org.jfrog.artifactory.client.model.Group;
import org.jfrog.artifactory.client.model.Permission;
import org.jfrog.artifactory.client.model.User;
import org.jfrog.artifactory.client.model.builder.SecurityBuilders;

/**
 * Date: 10/18/12 Time: 9:25 AM
 *
 * @author freds
 */
public interface Security {

    SecurityBuilders builders();

    Collection<String> userNames();

    User user(String name);

    Group group(String name);

    List<String> groupNames();

    Permission permission(String name);

    List<String> permissions();

    void createOrUpdate(User user);

    void createOrUpdateGroup(Group group);

    void createOrUpdatePermission(Permission permission);

    void deleteUser(String name);

    void deleteGroup(String name);

    void deletePermission(String name);

    String getSecurityApi();

    String getSecurityUsersApi();

    String getSecurityPermissionsApi();

    String getSecurityUserGroupsApi();
}
