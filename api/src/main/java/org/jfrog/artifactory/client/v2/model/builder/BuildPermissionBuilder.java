package org.jfrog.artifactory.client.v2.model.builder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.jfrog.artifactory.client.v2.model.permissions.Actions;
import org.jfrog.artifactory.client.v2.model.permissions.BuildPermission;

/**
 * @author matank
 * @since 21/07/2018
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface BuildPermissionBuilder {

    BuildPermissionBuilder regex(String regex);

    BuildPermissionBuilder builds(String... build);

    BuildPermissionBuilder actions(Actions actions);

    BuildPermission build();
}
