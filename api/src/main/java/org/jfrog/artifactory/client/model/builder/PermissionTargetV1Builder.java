package org.jfrog.artifactory.client.model.builder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.jfrog.artifactory.client.model.PermissionTargetV1;
import org.jfrog.artifactory.client.model.Principals;

/**
 * @author jbaruch
 * @since 26/11/12
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface PermissionTargetV1Builder {

    PermissionTargetV1Builder name(String name);

    PermissionTargetV1Builder includesPattern(String includesPattern);

    PermissionTargetV1Builder excludesPattern(String excludesPattern);

    PermissionTargetV1Builder repositories(String... repositories);

    PermissionTargetV1Builder principals(Principals principals);

    PermissionTargetV1 build();
}
