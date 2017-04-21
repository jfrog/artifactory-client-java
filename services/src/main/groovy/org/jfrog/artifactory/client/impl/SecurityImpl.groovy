package org.jfrog.artifactory.client.impl

import com.fasterxml.jackson.core.type.TypeReference
import groovyx.net.http.ContentType
import org.apache.commons.collections.CollectionUtils
import org.jfrog.artifactory.client.Security
import org.jfrog.artifactory.client.model.Group
import org.jfrog.artifactory.client.model.PermissionTarget
import org.jfrog.artifactory.client.model.User
import org.jfrog.artifactory.client.model.builder.SecurityBuilders
import org.jfrog.artifactory.client.model.builder.impl.SecurityBuildersImpl
import org.jfrog.artifactory.client.model.impl.GroupImpl
import org.jfrog.artifactory.client.model.impl.PermissionTargetImpl
import org.jfrog.artifactory.client.model.impl.UserImpl

/**
 *
 * Date: 10/18/12
 * Time: 9:59 AM
 * @author freds
 */
class SecurityImpl implements Security {
    private ArtifactoryImpl artifactory

    private String baseApiPath

    static private SecurityBuilders builders = SecurityBuildersImpl.create()

    SecurityImpl(ArtifactoryImpl artifactory, String baseApiPath) {
        this.artifactory = artifactory
        this.baseApiPath = baseApiPath
    }

    @Override
    SecurityBuilders builders() {
        return builders
    }

    @Override
    Collection<String> userNames() {
        def users = artifactory.get(getSecurityUsersApi(), ContentType.JSON)
        users.collect { it.name }
    }

    @Override
    User user(String name) {
        artifactory.get("${getSecurityUsersApi()}/$name", ContentType.JSON, new TypeReference<UserImpl>() {})
    }

    @Override
    Group group(String name) {
        artifactory.get("${getSecurityUserGroupsApi()}/$name", ContentType.JSON, new TypeReference<GroupImpl>() {})
    }

    @Override
    PermissionTarget permissionTarget(String name) {
        artifactory.get("${getSecurityPermissionsApi()}/$name", ContentType.JSON, new TypeReference<PermissionTargetImpl>() {})

    }

    @Override
    List<String> groupNames() {
        def groups = artifactory.get("${getSecurityUserGroupsApi()}", ContentType.JSON)
        def groupNames = groups.collect { it.name }
        groupNames
    }

    @Override
    List<String> permissionTargets() {
        def permissionTargets = artifactory.get("${getSecurityPermissionsApi()}", ContentType.JSON)
        def permissionTargetNames = permissionTargets.collect { it.name }
        return permissionTargetNames
    }

    @Override
    void createOrUpdate(User user) {
        artifactory.put("${getSecurityUsersApi()}/${user.name}", [:], ContentType.ANY, null, ContentType.JSON, user)
    }

    @Override
    void createOrUpdateGroup(Group group) {
        artifactory.put("${getSecurityUserGroupsApi()}/${group.name}", [:], ContentType.ANY, null, ContentType.JSON, group)
    }

    @Override
    public void createOrReplacePermissionTarget(PermissionTarget permissionTarget) {
        if (CollectionUtils.isEmpty(permissionTarget.getRepositories())) {
            throw new UnsupportedOperationException("At least 1 repository is required in permission target (could be 'ANY', 'ANY LOCAL', 'ANY REMOTE')")
        }
        artifactory.put("${getSecurityPermissionsApi()}/${permissionTarget.name}", [:], ContentType.ANY, null, ContentType.JSON, permissionTarget)
    }

    @Override
    String deleteUser(String name) {
        artifactory.delete("${getSecurityUsersApi()}/$name")
    }

    @Override
    String deleteGroup(String name) {
        artifactory.delete("${getSecurityUserGroupsApi()}/$name")
    }

    @Override
    String deletePermissionTarget(String name) {
        artifactory.delete("${getSecurityPermissionsApi()}/$name")
    }

    @Override
    String getSecurityApi() {
        return baseApiPath + "/security/";
    }

    @Override
    String getSecurityUsersApi() {
        return getSecurityApi() + "users";
    }

    @Override
    String getSecurityPermissionsApi() {
        return getSecurityApi() + "permissions";
    }

    @Override
    String getSecurityUserGroupsApi() {
        return getSecurityApi() + "groups";
    }
}
