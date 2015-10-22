package org.jfrog.artifactory.client;

import org.jfrog.artifactory.client.model.*;
import org.jfrog.artifactory.client.model.builder.GroupBuilder;
import org.jfrog.artifactory.client.model.builder.UserBuilder;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static junit.framework.Assert.*;
import static org.jfrog.artifactory.client.model.Privilege.*;


/**
 * @author freds
 * @since 18/10/2012
 */
public class SecurityTests extends ArtifactoryTestsBase {

    private static final String USER_NAME = "test" + ("" + System.currentTimeMillis()).substring(5);
    private static final String GROUP_NAME = "test_group" + ("" + System.currentTimeMillis()).substring(5);

    @AfterMethod
    public void leaveItClean() {
        try {
            artifactory.security().deleteUser(USER_NAME);
            artifactory.security().deleteGroup(GROUP_NAME);
        } catch (Exception ignore) {
        }
    }

    @Test
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
            assertEquals("Not Found", e.getMessage());
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
            assertEquals("Not Found", e.getMessage());
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
    public void testPermissionTarget() {
        PermissionTarget permissionTarget = artifactory.security().permissionTarget("Anything");
        assertEquals("Anything", permissionTarget.getName());
    }

    @Test
    public void testGetPermissionTargets() {
        List<String> permissionTargetNames = artifactory.security().permissionTargets();
        for (String name : permissionTargetNames) {
            PermissionTarget permissionTarget = artifactory.security().permissionTarget(name);
            assertNotNull(permissionTarget);
        }
    }

    @Test
    public void testCreateGroup() {
        GroupBuilder groupBuilder = artifactory.security().builders().groupBuilder();
        Group group = groupBuilder.name(GROUP_NAME).autoJoin(true).
                description("new test group").
                build();
        artifactory.security().createOrUpdateGroup(group);
        Group group1 = artifactory.security().group(GROUP_NAME);
        assertEquals(group.getDescription(), group1.getDescription());
    }

    @Test
    public void testCreateUser() throws Exception {
        UserBuilder userBuilder = artifactory.security().builders().userBuilder();
        User user = userBuilder.name(USER_NAME).email("test@test.com").admin(false)
                .profileUpdatable(true)
                .password("test")
                .build();
        artifactory.security().createOrUpdate(user);
        String resp = curl(artifactory.security().getSecurityUsersApi().substring(1));
        System.out.println(resp);
        assertTrue(resp.contains(USER_NAME));
    }

    @Test(groups = "security", dependsOnGroups = "uploadBasics")
    public void testEffectiveItemPermissions() throws Exception {
        Set<ItemPermission> itemPermissions = artifactory.repository(NEW_LOCAL).file(PATH).effectivePermissions();
        assertItemPermissions(itemPermissions);
    }

    private void assertItemPermissions(Set<ItemPermission> itemPermissions) {
        for (ItemPermission itemPermission : itemPermissions) {
            Subject subject = itemPermission.getSubject();
            switch (subject.getName()) {
                case "admin":
                    assertPermissions(itemPermission, false, new Privilege[]{ADMIN, DEPLOY, ANNOTATE, DELETE, READ},
                            new Privilege[0]);
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

    @Test(groups = "security", dependsOnGroups = "repositoryBasics")
    public void testEffectiveRepoPermissions() {
        Set<ItemPermission> itemPermissions = artifactory.repository(NEW_LOCAL).effectivePermissions();
        assertItemPermissions(itemPermissions);
    }

    private void assertPermissions(ItemPermission itemPermission, boolean isGroup, Privilege[] allowedPrivileges,
                                   Privilege[] notAllowedPrivileges) {
        Subject subject = itemPermission.getSubject();
        assertTrue(itemPermission.isAllowedTo(allowedPrivileges));
        assertFalse(!"admin".equals(subject.getName()) && itemPermission.isAllowedTo(notAllowedPrivileges));
        assertTrue(subject.isGroup() == isGroup);
        assertTrue(isGroup ? subject instanceof Group : subject instanceof User);
    }
}