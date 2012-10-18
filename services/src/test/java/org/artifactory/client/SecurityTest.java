package org.artifactory.client;

import org.testng.annotations.Test;

import java.util.Collection;

import static org.testng.Assert.assertTrue;


/**
 * @author freds
 * @since 18/10/2012
 */
public class SecurityTest extends ArtifactoryTestBase {

    private static final String LIST_USERS_PATH = Security.SECURITY_API + "users";
    private static final String USER_NAME = "testUser" + ("" + System.currentTimeMillis()).substring(5);

    @Test
    public void testListUserNames() throws Exception {
        Collection<String> userNames = artifactory.security().userNames();
        assertTrue(userNames.size() > 2);
/*
        if (userNames.contains(USER_NAME)) {
            artifactory.security().deleteUser(USER_NAME);
        }
        assertFalse(curl(LIST_USERS_PATH).contains(USER_NAME));
*/
    }
}
