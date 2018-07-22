package org.jfrog.artifactory.client.v2;

import org.jfrog.artifactory.client.ArtifactoryTestsBase;
import org.jfrog.artifactory.client.model.*;
import org.jfrog.artifactory.client.v2.model.permissions.*;
import org.jfrog.artifactory.client.v2.model.permissions.PermissionTarget;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import static junit.framework.Assert.*;
import static org.testng.Assert.assertTrue;

/**
 * @author matank
 * @since 21/07/2018
 */
public class SecurityTests extends ArtifactoryTestsBase {

    private static final String USER_NAME = "test" + ("" + System.currentTimeMillis()).substring(5);
    private static final String GROUP_NAME = "test_group" + ("" + System.currentTimeMillis()).substring(5);
    private static final String GROUP_EXTERNAL_NAME = "test_group_external" + ("" + System.currentTimeMillis()).substring(5);
    private static final String PERMISSION_TARGET_CREATE_NAME = "test_permission_create_" + ("" + System.currentTimeMillis()).substring(5);
    private static final String PERMISSION_TARGET_UPDATE_NAME = "test_permission_update_" + ("" + System.currentTimeMillis()).substring(5);

    @AfterMethod
    public void leaveItClean() {
        try {
            artifactory.security().deleteUser(USER_NAME);
        } catch (Exception ignore) {
        }
        try {
            artifactory.security().deleteGroup(GROUP_NAME);
        } catch (Exception ignore) {
        }
        try {
            artifactory.security().deleteGroup(GROUP_EXTERNAL_NAME);
        } catch (Exception ignore) {
        }

    }

    @Test
    public void testCreateV2PermissionTarget() {
        //Repository Permission
        Action repositoryUserAction = artifactory.v2().security().builders().actionBuilder().name("anonymous").actions(ActionType.READ, ActionType.WRITE).build();
        Action repositoryGroupAction = artifactory.v2().security().builders().actionBuilder().name("readers").actions(ActionType.READ, ActionType.WRITE).build();
        Actions repositoryActions = artifactory.v2().security().builders().actionsBuilder().users(repositoryUserAction).groups(repositoryGroupAction).build();
        RepositoryPermission repositoryPermission = artifactory.v2().security().builders().repositoryPermissionBuilder()
                .includePatterns("a", "b", "c")
                .excludePatterns("x", "y", "z")
                .repositories("ANY REMOTE")
                .actions(repositoryActions)
                .build();
        //Build Permission
        Action buildUserAction = artifactory.v2().security().builders().actionBuilder().name("anonymous").actions(ActionType.CREATE, ActionType.DELETE).build();
        Actions buildActions = artifactory.v2().security().builders().actionsBuilder().users(buildUserAction).build();
        BuildPermission buildPermission = artifactory.v2().security().builders().buildPermissionBuilder()
                .regex("a")
//                .builds("a","b","c")
                .actions(buildActions)
                .build();
        //Permission Target
        PermissionTarget v2PermissionTarget = artifactory.v2().security().builders().permissionTargetBuilder()
                .name(PERMISSION_TARGET_CREATE_NAME)
                .buildPermission(buildPermission)
                .repositoryPermission(repositoryPermission)
                .build();
        artifactory.v2().security().createPermissionTarget(v2PermissionTarget);

        PermissionTarget permissionTargetRes = artifactory.v2().security().permissionTarget(PERMISSION_TARGET_CREATE_NAME);
        assertNotNull(permissionTargetRes);
        assertEquals(permissionTargetRes.getName(), PERMISSION_TARGET_CREATE_NAME);
        assertEquals("ANY REMOTE", permissionTargetRes.getRepo().getRepositories().get(0));
        assertNotNull(permissionTargetRes.getRepo().getActions());
        assertEquals(permissionTargetRes.getRepo().getActions().getUsers().get(0).getName(), "anonymous");
        assertEquals(permissionTargetRes.getRepo().getActions().getUsers().get(0).getActionTypes(), repositoryUserAction.getActionTypes());
        assertEquals(permissionTargetRes.getRepo().getActions().getGroups().get(0).getName(), "readers");
        assertEquals(permissionTargetRes.getRepo().getActions().getGroups().get(0).getActionTypes(), repositoryGroupAction.getActionTypes());
    }

    @Test(enabled = false)
    public void testUpdateV2PermissionTarget() {
        //Repository Permission
        Action repositoryUserAction = artifactory.v2().security().builders().actionBuilder().name("anonymous").actions(ActionType.READ, ActionType.WRITE).build();
        Action repositoryGroupAction = artifactory.v2().security().builders().actionBuilder().name("readers").actions(ActionType.READ, ActionType.WRITE).build();
        Actions repositoryActions = artifactory.v2().security().builders().actionsBuilder().users(repositoryUserAction).groups(repositoryGroupAction).build();
        RepositoryPermission repositoryPermission = artifactory.v2().security().builders().repositoryPermissionBuilder()
                .includePatterns("a", "b", "c")
                .excludePatterns("x", "y", "z")
                .repositories("ANY REMOTE")
                .actions(repositoryActions)
                .build();
        //Build Permission
        Action buildUserAction = artifactory.v2().security().builders().actionBuilder().name("anonymous").actions(ActionType.CREATE, ActionType.DELETE).build();
        Actions buildActions = artifactory.v2().security().builders().actionsBuilder().users(buildUserAction).build();
        BuildPermission buildPermission = artifactory.v2().security().builders().buildPermissionBuilder()
                .regex("a.*")
                .actions(buildActions)
                .build();
        //Permission Target
        PermissionTarget v2PermissionTarget = artifactory.v2().security().builders().permissionTargetBuilder()
                .name(PERMISSION_TARGET_UPDATE_NAME)
                .buildPermission(buildPermission)
                .repositoryPermission(repositoryPermission)
                .build();
        artifactory.v2().security().createPermissionTarget(v2PermissionTarget);

        PermissionTarget permissionTargetRes = artifactory.v2().security().permissionTarget(PERMISSION_TARGET_UPDATE_NAME);
        assertNotNull(permissionTargetRes);
        assertEquals(permissionTargetRes.getName(), PERMISSION_TARGET_UPDATE_NAME);
        assertEquals(v2PermissionTarget.getBuild().getRegex(), "a.*");

        buildPermission = artifactory.v2().security().builders().buildPermissionBuilder().regex("b.*").build();

        PermissionTarget updatePermissionTarget = artifactory.v2().security().builders().permissionTargetBuilder()
                .name(PERMISSION_TARGET_UPDATE_NAME)
                .buildPermission(buildPermission)
                .build();

        permissionTargetRes = artifactory.v2().security().permissionTarget(PERMISSION_TARGET_UPDATE_NAME);
        artifactory.v2().security().updatePermissionTarget(updatePermissionTarget);
        assertEquals("a.*", permissionTargetRes.getBuild().getRegex());
    }

    @AfterClass
    public void tearDown() {
        try {
            artifactory.v2().security().deletePermissionTarget(PERMISSION_TARGET_UPDATE_NAME);
            artifactory.v2().security().deletePermissionTarget(PERMISSION_TARGET_CREATE_NAME);
        } catch (Exception ignore) {
        }
    }

    private void assertPermissions(ItemPermission itemPermission, boolean isGroup, Privilege[] allowedPrivileges, Privilege[] notAllowedPrivileges) {
        Subject subject = itemPermission.getSubject();
        assertTrue(itemPermission.isAllowedTo(allowedPrivileges));
        assertFalse(!"admin".equals(subject.getName()) && itemPermission.isAllowedTo(notAllowedPrivileges));
        assertTrue(subject.isGroup() == isGroup);
        assertTrue(isGroup ? subject instanceof Group : subject instanceof User);
    }
}