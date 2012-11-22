package org.artifactory.client;

import junit.framework.Assert;
import org.artifactory.client.model.Permissions;
import org.artifactory.client.model.Principal;
import org.artifactory.client.model.User;
import org.artifactory.client.model.builder.UserBuilder;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.Collection;

import static org.artifactory.client.model.Permission.*;
import static org.artifactory.client.model.Permission.DEPLOY;
import static org.artifactory.client.model.Permission.READ;
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
            Permissions permissions = artifactory.repository(NEW_LOCAL).file(PATH).effectivePermissions();
            Assert.assertTrue(permissions.user("admin").isAllowedTo(ADMIN, DEPLOY, ANNOTATE, DELETE, READ));
            Principal anonymous = permissions.user("anonymous");
            Assert.assertTrue(anonymous.isAllowedTo(READ) && !anonymous.isAllowedTo(DEPLOY));
            Principal readers = permissions.group("readers");
            Assert.assertTrue(readers.isAllowedTo(READ) && !readers.isAllowedTo(DEPLOY));
        }
}
