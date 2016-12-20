package org.jfrog.artifactory.client.model.builder;

import org.jfrog.artifactory.client.model.Permission;
import org.jfrog.artifactory.client.model.Principals;

/**
 * @author jbaruch
 * @since 26/11/12
 */
public interface PermissionBuilder {

    PermissionBuilder name(String name);

    PermissionBuilder includesPattern(String includesPattern);

    PermissionBuilder excludesPattern(String excludesPattern);

    PermissionBuilder repositories(String... repositories);

    PermissionBuilder principals(Principals principals);

    Permission build();
}
