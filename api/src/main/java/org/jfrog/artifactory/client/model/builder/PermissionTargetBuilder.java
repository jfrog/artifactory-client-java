package org.jfrog.artifactory.client.model.builder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.jfrog.artifactory.client.model.BuildPermission;
import org.jfrog.artifactory.client.model.PermissionTarget;
import org.jfrog.artifactory.client.model.RepositoryPermission;

/**
 * @author matank
 * @since 21/07/2018
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface PermissionTargetBuilder {

    PermissionTargetBuilder name(String name);

    PermissionTargetBuilder repositoryPermission(RepositoryPermission repositoryPermission);

    PermissionTargetBuilder buildPermission(BuildPermission buildPermission);

    PermissionTarget build();
}
