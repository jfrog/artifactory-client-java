package org.jfrog.artifactory.client;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.jfrog.artifactory.client.model.Privilege.ADMIN;
import static org.jfrog.artifactory.client.model.Privilege.ANNOTATE;
import static org.jfrog.artifactory.client.model.Privilege.DELETE;
import static org.jfrog.artifactory.client.model.Privilege.DEPLOY;
import static org.jfrog.artifactory.client.model.Privilege.READ;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.jfrog.artifactory.client.model.Group;
import org.jfrog.artifactory.client.model.ItemPermission;
import org.jfrog.artifactory.client.model.Permission;
import org.jfrog.artifactory.client.model.Principal;
import org.jfrog.artifactory.client.model.Principals;
import org.jfrog.artifactory.client.model.Privilege;
import org.jfrog.artifactory.client.model.Subject;
import org.jfrog.artifactory.client.model.User;
import org.jfrog.artifactory.client.model.builder.GroupBuilder;
import org.jfrog.artifactory.client.model.builder.PermissionBuilder;
import org.jfrog.artifactory.client.model.builder.UserBuilder;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import groovyx.net.http.HttpResponseException;

/**
 * @author freds
 * @since 18/10/2012
 */
public class SecurityTests extends ArtifactoryTestsBase {

    private static final String USER_NAME = "test_user" + ("" + System.currentTimeMillis()).substring(5);
    private static final String GROUP_NAME = "test_group" + ("" + System.currentTimeMillis()).substring(5);
    private static final String GROUP_EXTERNAL_NAME = "test_group_external" + ("" + System.currentTimeMillis()).substring(5);
    private static final String PERMISSION_NAME = "test_permission" + ("" + System.currentTimeMillis()).substring(5);

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
        try {
            artifactory.security().deletePermission(PERMISSION_NAME);
        } catch (Exception ignore) {
        }

    }

    @Test
    public void testListUserNames() throws Exception {
        Collection<String> userNames = artifactory.security().userNames();
        // By default, 'admin' & 'anonymous' exist
        assertTrue(userNames.size() >= 2);
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
    public void testGetPermission() {
        // WARN: This test is using default Artifactory users/groups
        Permission permissionTarget = artifactory.security().permission("Anything");
        assertEquals("Anything", permissionTarget.getName());
        assertTrue(permissionTarget.getIncludesPattern().contains("**"));
        assertTrue(permissionTarget.getRepositories().size() > 0);
        assertNotNull(permissionTarget.getPrincipals());
        assertNotNull(permissionTarget.getPrincipals().getUsers());
        assertTrue(permissionTarget.getPrincipals().getUsers().size() > 0);
        final String user = "anonymous";
        assertNotNull(permissionTarget.getPrincipals().getUser(user));
        assertEquals(user, permissionTarget.getPrincipals().getUser(user).getName());
        assertFalse(permissionTarget.getPrincipals().getUser(user).getPrivileges().contains(Privilege.ADMIN));
        assertFalse(permissionTarget.getPrincipals().getUser(user).getPrivileges().contains(Privilege.DELETE));
        assertFalse(permissionTarget.getPrincipals().getUser(user).getPrivileges().contains(Privilege.DEPLOY));
        assertFalse(permissionTarget.getPrincipals().getUser(user).getPrivileges().contains(Privilege.ANNOTATE));
        assertTrue(permissionTarget.getPrincipals().getUser(user).getPrivileges().contains(Privilege.READ));
        assertNotNull(permissionTarget.getPrincipals().getGroups());
        assertTrue(permissionTarget.getPrincipals().getGroups().size() > 0);
        final String group = "readers";
        assertEquals(group, permissionTarget.getPrincipals().getGroup(group).getName());
        assertFalse(permissionTarget.getPrincipals().getGroup(group).getPrivileges().contains(Privilege.ADMIN));
        assertFalse(permissionTarget.getPrincipals().getGroup(group).getPrivileges().contains(Privilege.DELETE));
        assertFalse(permissionTarget.getPrincipals().getGroup(group).getPrivileges().contains(Privilege.DEPLOY));
        assertFalse(permissionTarget.getPrincipals().getGroup(group).getPrivileges().contains(Privilege.ANNOTATE));
        assertTrue(permissionTarget.getPrincipals().getGroup(group).getPrivileges().contains(Privilege.READ));
    }

    @Test
    public void testCreateOrUpdatePermission() {
        // WARN: This test is using default Artifactory users/groups
        Principal userAno = artifactory.security().builders().principalBuilder().name("anonymous").privileges(Privilege.READ).build();
        Principal userAdmin = artifactory.security().builders().principalBuilder().name("admin").privileges(Privilege.ADMIN).build();
        Principal groupRead = artifactory.security().builders().principalBuilder().name("readers").privileges(Privilege.READ, Privilege.ANNOTATE)
                .build();
        Principal groupAdmin = artifactory.security().builders().principalBuilder().name("Admin")
                .privileges(Privilege.DEPLOY, Privilege.DELETE, Privilege.DELETE).build();

        Principals principals = artifactory.security().builders().principalsBuilder().users(userAno, userAdmin).groups(groupRead, groupAdmin).build();

        PermissionBuilder permissionBuilder = artifactory.security().builders().permissionBuilder();
        Permission permission = permissionBuilder.name(PERMISSION_NAME).repositories("ANY REMOTE", "jcenter").includesPattern("com/company")
                .excludesPattern("org/blacklist/,org/bug/").principals(principals).build();

        try {
            artifactory.security().createOrUpdatePermission(permission);
        } catch (Exception e) {
            if (e instanceof HttpResponseException) {
                throw new UnsupportedOperationException(((HttpResponseException) e).getResponse().getData().toString(), e);
            }
            throw new UnsupportedOperationException(e);
        }

        Permission permissionRes = artifactory.security().permission(PERMISSION_NAME);
        assertNotNull(permissionRes);
        assertEquals(permission.getName(), permissionRes.getName());
        assertTrue(permissionRes.getRepositories().size() == 2);
        assertEquals("ANY REMOTE", permissionRes.getRepositories().get(0));
        assertEquals("jcenter", permissionRes.getRepositories().get(1));
        assertNotNull(permissionRes.getPrincipals());
        assertNotNull(permissionRes.getPrincipals().getUsers());
        assertNotNull(permissionRes.getPrincipals().getUser("anonymous"));
        assertEquals("anonymous", permissionRes.getPrincipals().getUser("anonymous").getName());
        assertEquals(userAno.getPrivileges(), permissionRes.getPrincipals().getUser("anonymous").getPrivileges());
        assertNotNull(permissionRes.getPrincipals().getUser("admin"));
        assertEquals("admin", permissionRes.getPrincipals().getUser("admin").getName());
        assertTrue(permissionRes.getPrincipals().getUser("admin").isAllowedTo(Privilege.ADMIN));
        assertEquals(userAdmin.getPrivileges(), permissionRes.getPrincipals().getUser("admin").getPrivileges());
        assertNotNull(permissionRes.getPrincipals().getGroups());
        assertNotNull(permissionRes.getPrincipals().getGroup("Admin"));
        assertEquals("Admin", permissionRes.getPrincipals().getGroup("Admin").getName());
        assertEquals(groupAdmin.getPrivileges(), permissionRes.getPrincipals().getGroup("Admin").getPrivileges());
        assertNotNull(permissionRes.getPrincipals().getGroup("readers"));
        assertEquals("readers", permissionRes.getPrincipals().getGroup("readers").getName());
        assertEquals(groupRead.getPrivileges(), permissionRes.getPrincipals().getGroup("readers").getPrivileges());
    }

    @Test
    public void testCreateOrUpdatePermissionMinimal() {
        PermissionBuilder permissionBuilder = artifactory.security().builders().permissionBuilder();
        Permission permission = permissionBuilder.name(PERMISSION_NAME).repositories("ANY REMOTE", "jcenter").build();

        try {
            artifactory.security().createOrUpdatePermission(permission);
        } catch (Exception e) {
            if (e instanceof HttpResponseException) {
                throw new UnsupportedOperationException(((HttpResponseException) e).getResponse().getData().toString(), e);
            }
            throw new UnsupportedOperationException(e);
        }

        Permission permissionRes = artifactory.security().permission(PERMISSION_NAME);
        assertNotNull(permissionRes);
        assertEquals(permission.getName(), permissionRes.getName());
        assertTrue(permissionRes.getRepositories().size() == 2);
        assertEquals("ANY REMOTE", permissionRes.getRepositories().get(0));
        assertEquals("jcenter", permissionRes.getRepositories().get(1));
        assertNotNull(permissionRes.getPrincipals());
        assertNotNull(permissionRes.getPrincipals().getUsers());
        assertTrue(permissionRes.getPrincipals().getUsers().isEmpty());
        assertNotNull(permissionRes.getPrincipals().getGroups());
        assertTrue(permissionRes.getPrincipals().getGroups().isEmpty());
    }

    @Test
    public void testGetPermissions() {
        List<String> permissionTargetNames = artifactory.security().permissions();
        for (String name : permissionTargetNames) {
            Permission permissionTarget = artifactory.security().permission(name);
            assertNotNull(permissionTarget);
        }
    }

    @Test
    public void testCreateGroup() {
        GroupBuilder groupBuilder = artifactory.security().builders().groupBuilder();
        Group group = groupBuilder.name(GROUP_NAME).autoJoin(true).description("new test group").build();
        artifactory.security().createOrUpdateGroup(group);
        Group group1 = artifactory.security().group(GROUP_NAME);
        assertEquals(group.getDescription(), group1.getDescription());
        assertEquals("group should be internal by default", "artifactory", group1.getRealm());
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
                assertPermissions(itemPermission, false, new Privilege[] { ADMIN, DEPLOY, ANNOTATE, DELETE, READ }, new Privilege[0]);
                break;
            case "anonymous":
                assertPermissions(itemPermission, false, new Privilege[] { READ }, new Privilege[] { DEPLOY });
                break;
            case "readers":
                assertPermissions(itemPermission, true, new Privilege[] { READ }, new Privilege[] { DEPLOY });
                break;
            }
        }
    }

    @Test(groups = "security", dependsOnGroups = "repositoryBasics")
    public void testEffectiveRepoPermissions() {
        Set<ItemPermission> itemPermissions = artifactory.repository(NEW_LOCAL).effectivePermissions();
        assertItemPermissions(itemPermissions);
    }

    private void assertPermissions(ItemPermission itemPermission, boolean isGroup, Privilege[] allowedPrivileges, Privilege[] notAllowedPrivileges) {
        Subject subject = itemPermission.getSubject();
        assertTrue(itemPermission.isAllowedTo(allowedPrivileges));
        assertFalse(!"admin".equals(subject.getName()) && itemPermission.isAllowedTo(notAllowedPrivileges));
        assertTrue(subject.isGroup() == isGroup);
        assertTrue(isGroup ? subject instanceof Group : subject instanceof User);
    }
}