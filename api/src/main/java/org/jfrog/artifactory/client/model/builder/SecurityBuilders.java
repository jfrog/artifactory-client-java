package org.jfrog.artifactory.client.model.builder;

import org.jfrog.artifactory.client.model.Permission;
import org.jfrog.artifactory.client.model.User;

/**
 * Date: 10/18/12 Time: 9:27 AM
 *
 * @author freds
 */
public interface SecurityBuilders {
    UserBuilder userBuilder();

    GroupBuilder groupBuilder();

    UserBuilder builderFrom(User from);

    PermissionBuilder permissionBuilder();

    PermissionBuilder builderFrom(Permission from);

    PrincipalsBuilder principalsBuilder();

    PrincipalBuilder principalBuilder();

}
