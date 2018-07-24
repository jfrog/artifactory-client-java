package org.jfrog.artifactory.client.impl

import org.apache.http.entity.ContentType
import org.jfrog.artifactory.client.Security
import org.jfrog.artifactory.client.impl.util.Util
import org.jfrog.artifactory.client.model.Group
import org.jfrog.artifactory.client.model.PermissionTarget
import org.jfrog.artifactory.client.model.PermissionTargetV1
import org.jfrog.artifactory.client.model.User
import org.jfrog.artifactory.client.model.builder.SecurityBuilders
import org.jfrog.artifactory.client.model.impl.*

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
        UserBuilderImpl[] users = artifactory.get(getSecurityUsersApi(), UserBuilderImpl[], null)
        users.collect { it.name }
    }

    @Override
    User user(String name) {
        name = Util.encodeParams(name);
        return artifactory.get("${getSecurityUsersApi()}/$name", UserImpl, User)
    }

    @Override
    Group group(String name) {
        name = Util.encodeParams(name);
        artifactory.get("${getSecurityUserGroupsApi()}/$name", GroupImpl, Group)
    }

    @Override
    PermissionTargetV1 permissionTargetV1(String name) {
        name = Util.encodeParams(name);
        return artifactory.get("${getSecurityPermissionsV1Api()}/$name", PermissionTargetV1Impl, PermissionTargetV1)
    }

    @Override
    List<String> groupNames() {
        GroupImpl[] groups = artifactory.get("${getSecurityUserGroupsApi()}", GroupImpl[], null)
        def groupNames = groups.collect { it.name }
        groupNames
    }

    @Override
    List<String> permissionTargetsV1() {
        ArrayList permissionTargets = artifactory.get("${getSecurityPermissionsV1Api()}", ArrayList, List)
        def permissionTargetNames = permissionTargets.collect { it.name }
        return permissionTargetNames
    }

    @Override
    void createOrUpdate(User user) {
        String name = Util.encodeParams(user.name);
        artifactory.put("${getSecurityUsersApi()}/$name", ContentType.APPLICATION_JSON, Util.getStringFromObject(user),
                new HashMap<String, String>(), null, -1, String, null)
    }

    @Override
    void createOrUpdateGroup(Group group) {
        String name = Util.encodeParams(group.name);
        artifactory.put("${getSecurityUserGroupsApi()}/$name", ContentType.APPLICATION_JSON, Util.getStringFromObject(group),
                new HashMap<String, String>(), null, -1, String, null)
    }

    @Override
    public void createOrReplacePermissionTargetV1(PermissionTargetV1 permissionTarget) {
        List<String> repositories = permissionTarget.getRepositories()
        if (repositories == null || repositories.isEmpty()) {
            throw new UnsupportedOperationException("At least 1 repository is required in permission target (could be 'ANY', 'ANY LOCAL', 'ANY REMOTE')")
        }
        String name = Util.encodeParams(permissionTarget.name);
        artifactory.put("${getSecurityPermissionsV1Api()}/$name", ContentType.APPLICATION_JSON, Util.getStringFromObject(permissionTarget),
                new HashMap<String, String>(), null, -1, String, null)
    }

    @Override
    PermissionTarget permissionTarget(String name) {
        name = Util.encodeParams(name);
        return artifactory.get("${getSecurityPermissionsApi()}/$name", PermissionTargetImpl, PermissionTarget)
    }

    @Override
    List<String> permissionTargets() {
        ArrayList permissionTargets = artifactory.get("${getSecurityPermissionsApi()}", ArrayList, List)
        def permissionTargetNames = permissionTargets.collect { it.name }
        return permissionTargetNames
    }

    @Override
    public void createPermissionTarget(PermissionTarget permissionTarget) {
        String name = Util.encodeParams(permissionTarget.name);
        artifactory.post("${getSecurityPermissionsApi()}/$name", ContentType.APPLICATION_JSON, Util.getStringFromObject(permissionTarget),
                new HashMap<String, String>(), String, null)
    }

    @Override
    public void updatePermissionTarget(PermissionTarget permissionTarget) {
        String name = Util.encodeParams(permissionTarget.name);
        artifactory.put("${getSecurityPermissionsApi()}/$name", ContentType.APPLICATION_JSON, Util.getStringFromObject(permissionTarget),
                new HashMap<String, String>(), null, -1, String, null)
    }

    @Override
    String deletePermissionTarget(String name) {
        name = Util.encodeParams(name);
        artifactory.delete("${getSecurityPermissionsApi()}/$name")
    }

    @Override
    String deleteUser(String name) {
        name = Util.encodeParams(name);
        artifactory.delete("${getSecurityUsersApi()}/$name")
    }

    @Override
    String deleteGroup(String name) {
        name = Util.encodeParams(name);
        artifactory.delete("${getSecurityUserGroupsApi()}/$name")
    }

    @Override
    String deletePermissionTargetV1(String name) {
        name = Util.encodeParams(name);
        artifactory.delete("${getSecurityPermissionsV1Api()}/$name")
    }

    @Override
    String getSecurityV1Api() {
        return baseApiPath + "/security/";
    }

    @Override
    String getSecurityApi() {
        return baseApiPath + "/v2/security/";
    }

    @Override
    String getSecurityUsersApi() {
        return getSecurityV1Api() + "users";
    }

    @Override
    String getSecurityPermissionsV1Api() {
        return getSecurityV1Api() + "permissions";
    }

    @Override
    String getSecurityPermissionsApi() {
        return getSecurityApi() + "permissions";
    }

    @Override
    String getSecurityUserGroupsApi() {
        return getSecurityV1Api() + "groups";
    }
}
