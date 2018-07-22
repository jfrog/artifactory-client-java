package org.jfrog.artifactory.client.v2.impl

import org.apache.http.entity.ContentType
import org.jfrog.artifactory.client.impl.ArtifactoryImpl
import org.jfrog.artifactory.client.impl.util.Util
import org.jfrog.artifactory.client.v2.Security
import org.jfrog.artifactory.client.v2.model.builder.SecurityBuilders
import org.jfrog.artifactory.client.v2.model.permissions.PermissionTarget

/**
 * @author matank
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
    String getSecurityApi() {
        return baseApiPath + "/security/";
    }

    @Override
    String getSecurityPermissionsApi() {
        return getSecurityApi() + "permissions";
    }

}
