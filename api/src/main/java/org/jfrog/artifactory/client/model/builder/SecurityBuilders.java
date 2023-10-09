package org.jfrog.artifactory.client.model.builder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.jfrog.artifactory.client.model.PermissionTarget;
import org.jfrog.artifactory.client.model.User;

/**
 * Date: 10/18/12
 * Time: 9:27 AM
 *
 * @author freds
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface SecurityBuilders {
    UserBuilder userBuilder();

    GroupBuilder groupBuilder();

    UserBuilder builderFrom(User from);

    PermissionTargetBuilder permissionTargetBuilder();

    PermissionTargetBuilder builderFrom(PermissionTarget from);

    PrincipalsBuilder principalsBuilder();

    PrincipalBuilder principalBuilder();

    PermissionTargetV2Builder permissionTargetV2Builder();

    PermissionV2Builder permissionV2Builder();

    ActionsBuilder actionsBuilder();
}
