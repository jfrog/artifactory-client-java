package org.jfrog.artifactory.client.impl

import org.apache.commons.lang3.StringUtils
import org.apache.http.entity.ContentType
import org.jfrog.artifactory.client.Security
import org.jfrog.artifactory.client.impl.util.Util
import org.jfrog.artifactory.client.model.Group
import org.jfrog.artifactory.client.model.PermissionTarget
import org.jfrog.artifactory.client.model.PermissionTargetV2
import org.jfrog.artifactory.client.model.User
import org.jfrog.artifactory.client.model.builder.SecurityBuilders
import org.jfrog.artifactory.client.model.impl.PermissionTargetV2Impl
import org.jfrog.artifactory.client.model.impl.SecurityBuildersImpl
import org.jfrog.artifactory.client.model.impl.UserBuilderImpl
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
    PermissionTarget permissionTarget(String name) {
        name = Util.encodeParams(name);
        return artifactory.get("${getSecurityPermissionsApi()}/$name", PermissionTargetImpl, PermissionTarget)
    }

    @Override
    PermissionTargetV2 permissionTargetV2(String name){
        name = Util.encodeParams(name);
        return artifactory.get("${getSecurityPermissionsV2Api()}/$name", PermissionTargetV2Impl, PermissionTargetV2)
    }

    @Override
    List<String> groupNames() {
        GroupImpl[] groups = artifactory.get("${getSecurityUserGroupsApi()}", GroupImpl[], null)
        def groupNames = groups.collect { it.name }
        groupNames
    }

    @Override
    List<String> permissionTargets() {
        ArrayList permissionTargets = artifactory.get("${getSecurityPermissionsApi()}", ArrayList, List)
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
    public void createOrReplacePermissionTarget(PermissionTarget permissionTarget) {
        List<String> repositories = permissionTarget.getRepositories()
        if (repositories == null || repositories.isEmpty()) {
            throw new UnsupportedOperationException("At least 1 repository is required in permission target (could be 'ANY', 'ANY LOCAL', 'ANY REMOTE')")
        }
        String name = Util.encodeParams(permissionTarget.name);
        artifactory.put("${getSecurityPermissionsApi()}/$name", ContentType.APPLICATION_JSON, Util.getStringFromObject(permissionTarget),
                new HashMap<String, String>(), null, -1, String, null)
    }

    @Override
    void createOrReplacePermissionTargetV2(PermissionTargetV2 permissionTarget) {
        if(permissionTarget == null || StringUtils.isBlank(permissionTarget.getName())){
            throw new IllegalArgumentException("Permission target name is required")
        }
        if (permissionTarget.getRepo() == null || permissionTarget.getRepo().getRepositories()==null ||  permissionTarget.getRepo().getRepositories().isEmpty()) {
            throw new UnsupportedOperationException("At least 1 repository is required in permission target (could be 'ANY', 'ANY LOCAL', 'ANY REMOTE')")
        }
        if(permissionTarget.getBuild() != null && permissionTarget.getBuild().getRepositories() !=null){
            List<String> buildRepositories = permissionTarget.getBuild().getRepositories();
            if(buildRepositories.size() !=1 || !buildRepositories.contains("artifactory-build-info")){
                throw new UnsupportedOperationException("Only 'artifactory-build-info' repository is supported for build permission target")
            }
        }
        String name = Util.encodeParams(permissionTarget.getName());
        artifactory.put("${getSecurityPermissionsV2Api()}/$name", ContentType.APPLICATION_JSON, Util.getStringFromObject(permissionTarget),
                new HashMap<String, String>(), null, -1, String, null)
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
    String deletePermissionTarget(String name) {
        name = Util.encodeParams(name);
        artifactory.delete("${getSecurityPermissionsApi()}/$name")
    }

    @Override
    String deletePermissionTargetV2(String name) {
        name = Util.encodeParams(name);
        artifactory.delete("${getSecurityPermissionsV2Api()}/$name")
    }

    @Override
    String getSecurityApi() {
        return baseApiPath + "/security/";
    }

    @Override
    String getSecurityV2Api() {
        return baseApiPath + "/v2/security/";
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
    String getSecurityPermissionsV2Api() {
        return getSecurityV2Api() + "permissions";
    }

    @Override
    String getSecurityUserGroupsApi() {
        return getSecurityApi() + "groups";
    }
}
