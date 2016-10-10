package org.jfrog.artifactory.client.model.builder.impl

import org.jfrog.artifactory.client.model.Permission
import org.jfrog.artifactory.client.model.Principals
import org.jfrog.artifactory.client.model.builder.PermissionBuilder
import org.jfrog.artifactory.client.model.impl.PermissionImpl

/**
 * @author jbaruch
 * @since 26/11/12
 */
public class PermissionBuilderImpl implements PermissionBuilder {

    private String name
    private String includesPattern
    private String excludesPattern
    private Principals principals
    private List<String> repositories

    @Override
    public PermissionBuilder name(String name) {
        this.name = name
        this
    }

    @Override
    public PermissionBuilder includesPattern(String includesPattern) {
        this.includesPattern = includesPattern
        this
    }

    @Override
    public PermissionBuilder excludesPattern(String excludesPattern) {
        this.excludesPattern = excludesPattern
        this
    }

    @Override
    public PermissionBuilder repositories(String... repositories) {
        this.repositories = Arrays.asList(repositories)
        this
    }

    @Override
    public PermissionBuilder principals(Principals principals) {
        this.principals = principals
        this
    }

    @Override
    public Permission build() {
        return new PermissionImpl(name, includesPattern, excludesPattern, repositories, principals)
    }
}
