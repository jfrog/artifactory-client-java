package org.artifactory.client;

import junit.framework.Assert;
import org.artifactory.client.model.Group;
import org.artifactory.client.model.ItemPermission;
import org.artifactory.client.model.Subject;
import org.artifactory.client.model.User;
import org.artifactory.client.model.builder.UserBuilder;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.Collection;
import java.util.Set;

import static org.artifactory.client.model.Privilege.*;
import static org.testng.Assert.assertTrue;


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
    public void testEffectiveItemPermission() throws Exception {
        Set<ItemPermission> itemPermissions = artifactory.repository(NEW_LOCAL).file(PATH).effectivePermissions();
        for (ItemPermission itemPermission : itemPermissions) {
            Subject subject = itemPermission.getSubject();
            if (subject.getName().equals("admin")) {
                Assert.assertTrue(itemPermission.isAllowedTo(ADMIN, DEPLOY, ANNOTATE, DELETE, READ));
                Assert.assertFalse(subject.isGroup());
                Assert.assertTrue(subject instanceof User);
            }
            if (subject.getName().equals("anonymous")) {
                Assert.assertTrue(itemPermission.isAllowedTo(READ) && !itemPermission.isAllowedTo(DEPLOY));
                Assert.assertFalse(subject.isGroup());
                Assert.assertTrue(subject instanceof User);
            }
            if (subject.getName().equals("readers")) {
                Assert.assertTrue(itemPermission.isAllowedTo(READ) && !itemPermission.isAllowedTo(DEPLOY));
                Assert.assertTrue(subject.isGroup());
                Assert.assertTrue(subject instanceof Group);
            }
        }
    }
}