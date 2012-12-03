package org.artifactory.client;

import org.artifactory.client.model.Group;
import org.artifactory.client.model.ItemPermission;
import org.artifactory.client.model.Privilege;
import org.artifactory.client.model.Subject;
import org.artifactory.client.model.User;
import org.artifactory.client.model.builder.UserBuilder;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.Collection;
import java.util.Set;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.artifactory.client.model.Privilege.*;


/**
 * @author freds
 * @since 18/10/2012
 */
public class SecurityTests extends ArtifactoryTestsBase {

    private static final String USER_NAME = "test" + ("" + System.currentTimeMillis()).substring(5);

    @AfterMethod
    public void leaveItClean() {
        try {
            artifactory.security().deleteUser(USER_NAME);
        } catch (Exception ignore) {
        }
    }

    @Test
    public void testListUserNames() throws Exception {
        Collection<String> userNames = artifactory.security().userNames();
        assertTrue(userNames.size() > 2);
    }

    @Test
    public void testCreateUser() throws Exception {
        UserBuilder userBuilder = artifactory.security().builders().userBuilder();
        User user = userBuilder.name(USER_NAME).email("test@test.com").admin(false)
                .profileUpdatable(true)
                .password("test")
                .build();
        artifactory.security().createOrUpdate(user);
        String resp = curl(Security.SECURITY_USERS_API.substring(1));
        System.out.println(resp);
        assertTrue(resp.contains(USER_NAME));
    }

    @Test(groups = "security", dependsOnGroups = "uploadBasics")
    public void testEffectiveItemPermissions() throws Exception {
        Set<ItemPermission> itemPermissions = artifactory.repository(NEW_LOCAL).file(PATH).effectivePermissions();
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

    private void assertPermissions(ItemPermission itemPermission, boolean isGroup, Privilege[] allowedPrivileges,
            Privilege[] notAllowedPrivileges) {
        Subject subject = itemPermission.getSubject();
        assertTrue(itemPermission.isAllowedTo(allowedPrivileges));
        assertFalse(!"admin".equals(subject.getName()) && itemPermission.isAllowedTo(notAllowedPrivileges));
        assertTrue(subject.isGroup() == isGroup);
        assertTrue(isGroup ? subject instanceof Group : subject instanceof User);
    }
}