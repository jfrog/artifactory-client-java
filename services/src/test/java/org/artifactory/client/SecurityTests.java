package org.artifactory.client;

import org.artifactory.client.model.Permissions;
import org.artifactory.client.model.Principal;
import org.testng.annotations.Test;

import static junit.framework.Assert.assertTrue;
import static org.artifactory.client.model.Permission.*;

/**
 * @author jbaruch
 * @since 22/11/12
 */
public class SecurityTests extends ArtifactoryTestsBase {

    @Test(groups = "security", dependsOnGroups = "uploadBasics")
    public void testEffectiveItemPermission() throws Exception {
        Permissions permissions = artifactory.repository(NEW_LOCAL).file(PATH).effectivePermissions();
        assertTrue(permissions.user("admin").isAllowedTo(ADMIN, DEPLOY, ANNOTATE, DELETE, READ));
        Principal anonymous = permissions.user("anonymous");
        assertTrue(anonymous.isAllowedTo(READ) && !anonymous.isAllowedTo(DEPLOY));
        Principal readers = permissions.group("readers");
        assertTrue(readers.isAllowedTo(READ) && !readers.isAllowedTo(DEPLOY));
    }
}
