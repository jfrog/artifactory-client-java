package org.jfrog.artifactory.client.model.builder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.jfrog.artifactory.client.model.PermissionTarget;
import org.jfrog.artifactory.client.model.PermissionTargetV1;
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

    PermissionTargetV1Builder permissionTargetV1Builder();

    PermissionTargetV1Builder builderFrom(PermissionTargetV1 from);

    PrincipalsBuilder principalsBuilder();

    PrincipalBuilder principalBuilder();

    PermissionTargetBuilder permissionTargetBuilder();

    PermissionTargetBuilder builderFrom(PermissionTarget from);

    BuildPermissionBuilder buildPermissionBuilder();

    RepositoryPermissionBuilder repositoryPermissionBuilder();

    ActionsBuilder actionsBuilder();

    ActionBuilder actionBuilder();
}
