package org.jfrog.artifactory.client;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.HttpResponseException;
import org.jfrog.artifactory.client.model.*;
import org.jfrog.artifactory.client.model.builder.GroupBuilder;
import org.jfrog.artifactory.client.model.builder.PermissionTargetBuilder;
import org.jfrog.artifactory.client.model.builder.UserBuilder;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.jfrog.artifactory.client.model.Privilege.*;
import static org.testng.Assert.*;

/**
 * @author freds
 * @since 18/10/2012
 */
public class SecurityTests extends ArtifactoryTestsBase {

    private static final String USER_NAME = "test" + ("" + System.currentTimeMillis()).substring(5);
    private static final String GROUP_NAME = "test_group" + ("" + System.currentTimeMillis()).substring(5);
    private static final String GROUP_ADMIN_NAME = "test_admin_group" + ("" + System.currentTimeMillis()).substring(5);
    private static final String GROUP_EXTERNAL_NAME = "test_group_external" + ("" + System.currentTimeMillis()).substring(5);
    private static final String PERMISSION_TARGET_NAME = "test_permission" + ("" + System.currentTimeMillis()).substring(5);
    private static final int PERM_TARGET_RETRIES = 12;
    private static final long PERM_TARGET_SLEEP_INTERVAL_MILLIS = TimeUnit.SECONDS.toMillis(10);

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
    public void testGetPermissionTarget() {
        PermissionTarget permissionTarget = artifactory.security().permissionTarget("Anything");
        assertEquals("Anything", permissionTarget.getName());
        assertTrue(permissionTarget.getIncludesPattern().contains("**"));
        assertFalse(permissionTarget.getRepositories().isEmpty());
        assertEquals("ANY", permissionTarget.getRepositories().get(0));
        assertNotNull(permissionTarget.getPrincipals());
        assertNotNull(permissionTarget.getPrincipals().getUsers());
        assertNotNull(permissionTarget.getPrincipals().getGroups());
    }

    @Test
    public void testGetPermissionTargets() {
        List<String> permissionTargetNames = artifactory.security().permissionTargets();
        for (String name : permissionTargetNames) {
            PermissionTarget permissionTarget = artifactory.security().permissionTarget(name);
            assertNotNull(permissionTarget);
        }
    }

    @Test(groups = "create")
    public void testCreatePermissionTarget() throws Exception {
        // WARN: This test is using default Artifactory users/groups
        Principal userAno = artifactory.security().builders().principalBuilder().name("anonymous").privileges(Privilege.READ).build();
        Principal groupRead = artifactory.security().builders().principalBuilder().name("readers").privileges(Privilege.READ, Privilege.ANNOTATE)
                .build();

        Principals principals = artifactory.security().builders().principalsBuilder().users(userAno).groups(groupRead).build();

        PermissionTargetBuilder permissionBuilder = artifactory.security().builders().permissionTargetBuilder();
        PermissionTarget permission = permissionBuilder.name(PERMISSION_TARGET_NAME).repositories("ANY REMOTE").includesPattern("com/company")
                .excludesPattern("org/blacklist/,org/bug/").principals(principals).build();
        artifactory.security().createOrReplacePermissionTarget(permission);

        PermissionTarget permissionTargetRes = getAndAssertPermissionTarget("ANY REMOTE");
        assertNotNull(permissionTargetRes);
        assertNotNull(permissionTargetRes.getPrincipals());
        assertEquals(permissionTargetRes.getPrincipals().getUsers().get(0).getName(), "anonymous");
        assertEquals(permissionTargetRes.getPrincipals().getUsers().get(0).getPrivileges(), userAno.getPrivileges());
        assertEquals(permissionTargetRes.getPrincipals().getGroups().get(0).getName(), "readers");
        assertEquals(permissionTargetRes.getPrincipals().getGroups().get(0).getPrivileges(), groupRead.getPrivileges());

    }

    @Test(dependsOnMethods = "testCreatePermissionTarget")
    public void testReplacePermissionTarget() throws Exception {
        // WARN: This test is using default Artifactory users/groups
        PermissionTarget permissionTarget = artifactory.security().permissionTarget(PERMISSION_TARGET_NAME);
        assertNotNull(permissionTarget);
        Principal userAno = artifactory.security().builders().principalBuilder().name("anonymous").privileges(Privilege.READ, ANNOTATE).build();

        Principals principals = artifactory.security().builders().principalsBuilder().users(userAno).build();

        PermissionTargetBuilder permissionBuilder = artifactory.security().builders().permissionTargetBuilder();
        PermissionTarget permission = permissionBuilder.name(PERMISSION_TARGET_NAME).repositories(getJCenterRepoName()).principals(principals).build();
        artifactory.security().createOrReplacePermissionTarget(permission);

        PermissionTarget permissionTargetRes = getAndAssertPermissionTarget(getJCenterRepoName());
        assertNotNull(permissionTargetRes);
        assertNotNull(permissionTargetRes.getPrincipals());
        assertEquals(permissionTargetRes.getPrincipals().getUsers().get(0).getName(), "anonymous");
        assertEquals(permissionTargetRes.getPrincipals().getUsers().get(0).getPrivileges(), userAno.getPrivileges());
        assertEquals(permissionTargetRes.getPrincipals().getGroups().size(), 0);
    }

    /**
     * Get and assert permission target with PERMISSION_TARGET_NAME as its name and the input expectedRepoName as its repository.
     *
     * @param expectedRepoName - The expected repository name
     * @return the permission target. Never null.
     * @throws Exception in case of permission target not found.
     */
    private PermissionTarget getAndAssertPermissionTarget(String expectedRepoName) throws Exception {
        Exception permissionTargetException = null;
        for (int i = 0; i < PERM_TARGET_RETRIES; i++) {
            try {
                PermissionTarget permissionTarget = artifactory.security().permissionTarget(PERMISSION_TARGET_NAME);
                String actualRepoName = permissionTarget.getRepositories().get(0);
                if (StringUtils.equals(actualRepoName, expectedRepoName)) {
                    // Permission target found and validated
                    return permissionTarget;
                }
                permissionTargetException = new IOException("Permission target repo is '" + actualRepoName + "', but expected to be '" + expectedRepoName + "'.");
            } catch (Exception e) {
                permissionTargetException = e;
            }
            Thread.sleep(PERM_TARGET_SLEEP_INTERVAL_MILLIS);
        }
        throw permissionTargetException;
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
        User user = userBuilder.name(USER_NAME).email("test@test.com").admin(false).profileUpdatable(true).password("YouShallNotPass123!").build();
        artifactory.security().createOrUpdate(user);
        String resp = curl(artifactory.security().getSecurityUsersApi().substring(1));
        assertTrue(resp.contains(USER_NAME));
    }

    @Test(groups = "security")
    public void testEffectiveItemPermissions() throws Exception {
        InputStream inputStream = this.getClass().getResourceAsStream("/sample.txt");
        assertNotNull(inputStream);
        File deployed = artifactory.repository(localRepository.getKey()).upload(PATH, inputStream).doUpload();
        assertNotNull(deployed);
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
            artifactory.security().deletePermissionTarget(PERMISSION_TARGET_NAME);
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
