package org.jfrog.artifactory.client.model.builder;

import org.jfrog.artifactory.client.model.PermissionTarget;
import org.jfrog.artifactory.client.model.User;

/**
 * Date: 10/18/12
 * Time: 9:27 AM
 *
 * @author freds
 */
public interface SecurityBuilders {
    UserBuilder userBuilder();

    UserBuilder builderFrom(User from);

    PermissionTargetBuilder permissionTargetBuilder();

    PermissionTargetBuilder builderFrom(PermissionTarget from);

}
