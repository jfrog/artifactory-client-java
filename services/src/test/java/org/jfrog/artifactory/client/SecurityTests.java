package org.jfrog.artifactory.client;

import org.apache.http.client.HttpResponseException;
import org.jfrog.artifactory.client.model.*;
import org.jfrog.artifactory.client.model.builder.GroupBuilder;
import org.jfrog.artifactory.client.model.builder.PermissionTargetV1Builder;
import org.jfrog.artifactory.client.model.builder.UserBuilder;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static junit.framework.Assert.*;
import static org.jfrog.artifactory.client.model.Privilege.*;
import static org.testng.Assert.assertTrue;

/**
 * @author freds
 * @since 18/10/2012
 */
public class SecurityTests extends ArtifactoryTestsBase {

    private static final String USER_NAME = "test" + ("" + System.currentTimeMillis()).substring(5);
    private static final String GROUP_NAME = "test_group" + ("" + System.currentTimeMillis()).substring(5);
    private static final String GROUP_ADMIN_NAME = "test_admin_group" + ("" + System.currentTimeMillis()).substring(5);
    private static final String GROUP_EXTERNAL_NAME = "test_group_external" + ("" + System.currentTimeMillis()).substring(5);
    private static final String PERMISSION_Target_NAME = "test_permission" + ("" + System.currentTimeMillis()).substring(5);
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

    @Test(enabled = false)
    public void testListUserNames() throws Exception {
        Collection<String> userNames = artifactory.security().userNames();
        assertTrue(userNames.size() > 2);
    }

    @Test
    public void testGetUser() {
        User user = artifactory.security().user("anonymous");
        assertEquals("anonymous", user.getName());
    }

    @Test
    public void testUserNotFound() {
        try {
            artifactory.security().user("blablabla");
        } catch (Exception e) {
            assertTrue(e instanceof HttpResponseException);
            assertTrue(((HttpResponseException) e).getStatusCode() == 404);
        }
    }

    @Test
    public void testGetGroup() {
        Group group = artifactory.security().group("readers");
        assertEquals("readers", group.getName());
    }

    @Test
    public void testGroupNotFound() {
        try {
            artifactory.security().group("blalbabla");
        } catch (Exception e) {
            assertTrue(e instanceof HttpResponseException);
            assertTrue(((HttpResponseException) e).getStatusCode() == 404);
        }
    }

    @Test
    public void testGroups() {
        List<String> groupNames = artifactory.security().groupNames();
        for (String groupName : groupNames) {
            Group group = artifactory.security().group(groupName);
            assertNotNull(group);
        }
    }

    @Test
    public void testCreatePermissionTarget() {
        //Repository Permission
        Action repositoryUserAction = artifactory.security().builders().actionBuilder().name("anonymous").actions(ActionType.READ, ActionType.WRITE).build();
        Action repositoryGroupAction = artifactory.security().builders().actionBuilder().name("readers").actions(ActionType.READ, ActionType.WRITE).build();
        Actions repositoryActions = artifactory.security().builders().actionsBuilder().users(repositoryUserAction).groups(repositoryGroupAction).build();
        RepositoryPermission repositoryPermission = artifactory.security().builders().repositoryPermissionBuilder()
                .includePatterns("a", "b", "c")
                .excludePatterns("x", "y", "z")
                .repositories("ANY REMOTE")
                .actions(repositoryActions)
                .build();
        //Build Permission
        Action buildUserAction = artifactory.security().builders().actionBuilder().name("anonymous").actions(ActionType.CREATE, ActionType.DELETE).build();
        Actions buildActions = artifactory.security().builders().actionsBuilder().users(buildUserAction).build();
        BuildPermission buildPermission = artifactory.security().builders().buildPermissionBuilder()
                .regex("a")
//                .builds("a","b","c")
                .actions(buildActions)
                .build();
        //Permission Target
        PermissionTarget v2PermissionTarget = artifactory.security().builders().permissionTargetBuilder()
                .name(PERMISSION_TARGET_CREATE_NAME)
                .buildPermission(buildPermission)
                .repositoryPermission(repositoryPermission)
                .build();
        artifactory.security().createPermissionTarget(v2PermissionTarget);

        PermissionTarget permissionTargetRes = artifactory.security().permissionTarget(PERMISSION_TARGET_CREATE_NAME);
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
    public void testUpdatePermissionTarget() {
        //Repository Permission
        Action repositoryUserAction = artifactory.security().builders().actionBuilder().name("anonymous").actions(ActionType.READ, ActionType.WRITE).build();
        Action repositoryGroupAction = artifactory.security().builders().actionBuilder().name("readers").actions(ActionType.READ, ActionType.WRITE).build();
        Actions repositoryActions = artifactory.security().builders().actionsBuilder().users(repositoryUserAction).groups(repositoryGroupAction).build();
        RepositoryPermission repositoryPermission = artifactory.security().builders().repositoryPermissionBuilder()
                .includePatterns("a", "b", "c")
                .excludePatterns("x", "y", "z")
                .repositories("ANY REMOTE")
                .actions(repositoryActions)
                .build();
        //Build Permission
        Action buildUserAction = artifactory.security().builders().actionBuilder().name("anonymous").actions(ActionType.CREATE, ActionType.DELETE).build();
        Actions buildActions = artifactory.security().builders().actionsBuilder().users(buildUserAction).build();
        BuildPermission buildPermission = artifactory.security().builders().buildPermissionBuilder()
                .regex("a.*")
                .actions(buildActions)
                .build();
        //Permission Target
        PermissionTarget v2PermissionTarget = artifactory.security().builders().permissionTargetBuilder()
                .name(PERMISSION_TARGET_UPDATE_NAME)
                .buildPermission(buildPermission)
                .repositoryPermission(repositoryPermission)
                .build();
        artifactory.security().createPermissionTarget(v2PermissionTarget);

        PermissionTarget permissionTargetRes = artifactory.security().permissionTarget(PERMISSION_TARGET_UPDATE_NAME);
        assertNotNull(permissionTargetRes);
        assertEquals(permissionTargetRes.getName(), PERMISSION_TARGET_UPDATE_NAME);
        assertEquals(v2PermissionTarget.getBuild().getRegex(), "a.*");

        buildPermission = artifactory.security().builders().buildPermissionBuilder().regex("b.*").build();

        PermissionTarget updatePermissionTarget = artifactory.security().builders().permissionTargetBuilder()
                .name(PERMISSION_TARGET_UPDATE_NAME)
                .buildPermission(buildPermission)
                .build();

        permissionTargetRes = artifactory.security().permissionTarget(PERMISSION_TARGET_UPDATE_NAME);
        artifactory.security().updatePermissionTarget(updatePermissionTarget);
        assertEquals("a.*", permissionTargetRes.getBuild().getRegex());
    }

    @Test
    public void testGetPermissionTargetV1() {
        PermissionTargetV1 permissionTargetV1 = artifactory.security().permissionTargetV1("Anything");
        assertEquals("Anything", permissionTargetV1.getName());
        assertTrue(permissionTargetV1.getIncludesPattern().contains("**"));
        assertTrue(permissionTargetV1.getRepositories().size() > 0);
        assertNotNull(permissionTargetV1.getPrincipals());
        assertNotNull(permissionTargetV1.getPrincipals().getUsers());
        final String user = permissionTargetV1.getPrincipals().getUsers().get(0).getName();
        assertFalse(permissionTargetV1.getPrincipals().getUser(user).getPrivileges().contains(Privilege.ADMIN));
        assertFalse(permissionTargetV1.getPrincipals().getUser(user).getPrivileges().contains(Privilege.DELETE));
        assertTrue(permissionTargetV1.getPrincipals().getUser(user).getPrivileges().contains(Privilege.READ));
        assertNotNull(permissionTargetV1.getPrincipals().getGroups());
    }

    @Test
    public void testGetPermissionTargetsV1() {
        List<String> permissionTargetNames = artifactory.security().permissionTargetsV1();
        for (String name : permissionTargetNames) {
            PermissionTargetV1 permissionTargetV1 = artifactory.security().permissionTargetV1(name);
            assertNotNull(permissionTargetV1);
        }
    }

    @Test(groups = "create")
    public void testCreatePermissionTargetV1() {
        // WARN: This test is using default Artifactory users/groups
        Principal userAno = artifactory.security().builders().principalBuilder().name("anonymous").privileges(Privilege.READ).build();
        Principal groupRead = artifactory.security().builders().principalBuilder().name("readers").privileges(Privilege.READ, Privilege.ANNOTATE)
                .build();

        Principals principals = artifactory.security().builders().principalsBuilder().users(userAno).groups(groupRead).build();

        PermissionTargetV1Builder permissionBuilder = artifactory.security().builders().permissionTargetV1Builder();
        PermissionTargetV1 permission = permissionBuilder.name(PERMISSION_Target_NAME).repositories("ANY REMOTE").includesPattern("com/company")
                .excludesPattern("org/blacklist/,org/bug/").principals(principals).build();

        try {
            artifactory.security().createOrReplacePermissionTargetV1(permission);
        } catch (Exception e) {
            if (e instanceof HttpResponseException) {
                throw new UnsupportedOperationException(((HttpResponseException) e).getMessage(), e);
            }
            throw new UnsupportedOperationException(e);
        }

        PermissionTargetV1 permissionTargetV1Res = artifactory.security().permissionTargetV1(PERMISSION_Target_NAME);
        assertNotNull(permissionTargetV1Res);
        assertEquals(permissionTargetV1Res.getName(), PERMISSION_Target_NAME);
        assertEquals("ANY REMOTE", permissionTargetV1Res.getRepositories().get(0));
        assertNotNull(permissionTargetV1Res.getPrincipals());
        assertEquals(permissionTargetV1Res.getPrincipals().getUsers().get(0).getName(), "anonymous");
        assertEquals(permissionTargetV1Res.getPrincipals().getUsers().get(0).getPrivileges(), userAno.getPrivileges());
        assertEquals(permissionTargetV1Res.getPrincipals().getGroups().get(0).getName(), "readers");
        assertEquals(permissionTargetV1Res.getPrincipals().getGroups().get(0).getPrivileges(), groupRead.getPrivileges());

    }

    @Test(dependsOnMethods = "testCreatePermissionTargetV1")
    public void testReplacePermissionTargetV1() {
        // WARN: This test is using default Artifactory users/groups
        PermissionTargetV1 permissionTargetV1 = artifactory.security().permissionTargetV1(PERMISSION_Target_NAME);
        assertNotNull(permissionTargetV1);
        Principal userAno = artifactory.security().builders().principalBuilder().name("anonymous").privileges(Privilege.READ, ANNOTATE).build();

        Principals principals = artifactory.security().builders().principalsBuilder().users(userAno).build();

        PermissionTargetV1Builder permissionBuilder = artifactory.security().builders().permissionTargetV1Builder();
        PermissionTargetV1 permission = permissionBuilder.name(PERMISSION_Target_NAME).repositories(getJCenterRepoName()).principals(principals).build();

        try {
            artifactory.security().createOrReplacePermissionTargetV1(permission);
        } catch (Exception e) {
            if (e instanceof HttpResponseException) {
                throw new UnsupportedOperationException(((HttpResponseException) e).getMessage(), e);
            }
            throw new UnsupportedOperationException(e);
        }

        PermissionTargetV1 permissionTargetV1Res = artifactory.security().permissionTargetV1(PERMISSION_Target_NAME);
        assertNotNull(permissionTargetV1Res);
        assertEquals(permissionTargetV1Res.getName(), PERMISSION_Target_NAME);
        assertEquals(getJCenterRepoName(), permissionTargetV1Res.getRepositories().get(0));
        assertNotNull(permissionTargetV1Res.getPrincipals());
        assertEquals(permissionTargetV1Res.getPrincipals().getUsers().get(0).getName(), "anonymous");
        assertEquals(permissionTargetV1Res.getPrincipals().getUsers().get(0).getPrivileges(), userAno.getPrivileges());
        assertEquals(permissionTargetV1Res.getPrincipals().getGroups().size(), 0);
    }

    @Test
    public void testCreateGroup() {
        GroupBuilder groupBuilder = artifactory.security().builders().groupBuilder();
        Group group = groupBuilder.name(GROUP_NAME).autoJoin(true).description("new test group").build();
        artifactory.security().createOrUpdateGroup(group);
        Group group1 = artifactory.security().group(GROUP_NAME);
        assertEquals(group.getDescription(), group1.getDescription());
        assertEquals(group.isAdminPrivileges(), group1.isAdminPrivileges());
    }

    @Test
    public void testCreateAdminGroup() {
        GroupBuilder groupBuilder = artifactory.security().builders().groupBuilder();
        Group group = groupBuilder.name(GROUP_ADMIN_NAME).autoJoin(false).adminPrivileges(true).description("new test admin group").build();
        artifactory.security().createOrUpdateGroup(group);
        Group group1 = artifactory.security().group(GROUP_ADMIN_NAME);
        assertEquals(group.isAdminPrivileges(), group1.isAdminPrivileges());
    }

    @Test
    public void testCreateGroupExternal() {
        GroupBuilder groupBuilder = artifactory.security().builders().groupBuilder();
        Group group = groupBuilder.name(GROUP_EXTERNAL_NAME).realm("ldap").realmAttributes("fake-only-check-the-support")
                .description("new test group external").build();
        artifactory.security().createOrUpdateGroup(group);
        Group group1 = artifactory.security().group(GROUP_EXTERNAL_NAME);
        assertEquals(group.getDescription(), group1.getDescription());
        assertEquals(group.getRealm(), group1.getRealm());
        assertEquals(group.getRealmAttributes(), group1.getRealmAttributes());
    }

    @Test
    public void testCreateUser() throws Exception {
        UserBuilder userBuilder = artifactory.security().builders().userBuilder();
        User user = userBuilder.name(USER_NAME).email("test@test.com").admin(false).profileUpdatable(true).password("test").build();
        artifactory.security().createOrUpdate(user);
        String resp = curl(artifactory.security().getSecurityUsersApi().substring(1));
        System.out.println(resp);
        assertTrue(resp.contains(USER_NAME));
    }

    @Test(groups = "security")
    public void testEffectiveItemPermissions() throws Exception {
        InputStream inputStream = this.getClass().getResourceAsStream("/sample.txt");
        Assert.assertNotNull(inputStream);
        File deployed = artifactory.repository(localRepository.getKey()).upload(PATH, inputStream).doUpload();
        Assert.assertNotNull(deployed);
        Set<ItemPermission> itemPermissions = artifactory.repository(localRepository.getKey()).file(PATH).effectivePermissions();
        assertItemPermissions(itemPermissions);
    }

    private void assertItemPermissions(Set<ItemPermission> itemPermissions) {
        for (ItemPermission itemPermission : itemPermissions) {
            Subject subject = itemPermission.getSubject();
            switch (subject.getName()) {
                case "admin":
                    assertPermissions(itemPermission, false, new Privilege[]{ADMIN, DEPLOY, ANNOTATE, DELETE, READ}, new Privilege[0]);
                    break;
                case "anonymous":
                    assertPermissions(itemPermission, false, new Privilege[]{READ}, new Privilege[]{DEPLOY});
                    break;
                case "readers":
                    assertPermissions(itemPermission, true, new Privilege[]{READ}, new Privilege[]{DEPLOY});
                    break;
            }
        }
    }

    @Test(groups = "security")
    public void testEffectiveRepoPermissions() {
        Set<ItemPermission> itemPermissions = artifactory.repository(localRepository.getKey()).effectivePermissions();
        assertItemPermissions(itemPermissions);
    }

    @AfterClass
    public void tearDown() {
        try {
            artifactory.security().deletePermissionTargetV1(PERMISSION_Target_NAME);
            artifactory.security().deletePermissionTarget(PERMISSION_TARGET_UPDATE_NAME);
            artifactory.security().deletePermissionTarget(PERMISSION_TARGET_CREATE_NAME);
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