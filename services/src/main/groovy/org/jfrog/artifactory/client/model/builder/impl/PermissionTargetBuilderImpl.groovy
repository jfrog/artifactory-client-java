package org.jfrog.artifactory.client.model.builder.impl

import org.jfrog.artifactory.client.model.ItemPermission
import org.jfrog.artifactory.client.model.PermissionTarget
import org.jfrog.artifactory.client.model.Principals
import org.jfrog.artifactory.client.model.builder.PermissionTargetBuilder
import org.jfrog.artifactory.client.model.impl.PermissionTargetImpl

/**
 * @author jbaruch
 * @since 26/11/12
 */
public class PermissionTargetBuilderImpl implements PermissionTargetBuilder {

    private String name
    private String includesPattern
    private String excludesPattern
    private List<String> repositories
    private Principals principals

    @Override
    public PermissionTargetBuilder name(String name) {
        this.name = name
        this
    }

    @Override
    public PermissionTargetBuilder includesPattern(String includesPattern) {
        this.includesPattern = includesPattern
        this
    }

    @Override
    public PermissionTargetBuilder excludesPattern(String excludesPattern) {
        this.excludesPattern = excludesPattern
        this
    }

    @Override
    public PermissionTargetBuilder repositories(String... repositories) {
        this.repositories = Arrays.asList(repositories)
        this
    }

    @Override
    PermissionTargetBuilder principals(Principals principals) {
        this.principals = principals
        this
    }

    @Override
    public PermissionTarget build() {
        return new PermissionTargetImpl(name, includesPattern, excludesPattern, repositories, principals)
    }
}
