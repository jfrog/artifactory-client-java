package org.jfrog.artifactory.client.model.impl;

import org.jfrog.artifactory.client.model.PermissionTarget;
import org.jfrog.artifactory.client.model.Principals;
import org.jfrog.artifactory.client.model.builder.PermissionTargetBuilder;

import java.util.Arrays;
import java.util.List;

/**
 * @author jbaruch
 * @since 26/11/12
 */
public class PermissionTargetBuilderImpl implements PermissionTargetBuilder {
    private String name;
    private String includesPattern;
    private String excludesPattern;
    private List<String> repositories;
    private Principals principals;

    public PermissionTargetBuilder name(String name) {
        this.name = name;
        return this;
    }

    public PermissionTargetBuilder includesPattern(String includesPattern) {
        this.includesPattern = includesPattern;
        return this;
    }

    public PermissionTargetBuilder excludesPattern(String excludesPattern) {
        this.excludesPattern = excludesPattern;
        return this;
    }

    public PermissionTargetBuilder repositories(String... repositories) {
        this.repositories = Arrays.asList(repositories);
        return this;
    }

    public PermissionTargetBuilder principals(Principals principals) {
        this.principals = principals;
        return this;
    }

    public PermissionTarget build() {
        return new PermissionTargetImpl(name, includesPattern, excludesPattern, repositories, principals);
    }
}
