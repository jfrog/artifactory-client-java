package org.jfrog.artifactory.client.v2.model.builder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.jfrog.artifactory.client.v2.model.permissions.PermissionTarget;

/**
 * Date: 10/18/12
 * Time: 9:27 AM
 *
 * @author freds
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface SecurityBuilders {

    PermissionTargetBuilder permissionTargetBuilder();

    PermissionTargetBuilder builderFrom(PermissionTarget from);

    BuildPermissionBuilder buildPermissionBuilder();

    RepositoryPermissionBuilder repositoryPermissionBuilder();

    ActionsBuilder actionsBuilder();

    ActionBuilder actionBuilder();
}
