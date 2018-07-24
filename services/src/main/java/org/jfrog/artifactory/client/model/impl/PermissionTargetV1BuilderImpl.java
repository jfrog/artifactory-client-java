package org.jfrog.artifactory.client.model.impl;

import org.jfrog.artifactory.client.model.PermissionTargetV1;
import org.jfrog.artifactory.client.model.Principals;
import org.jfrog.artifactory.client.model.builder.PermissionTargetV1Builder;

import java.util.Arrays;
import java.util.List;

/**
 * @author jbaruch
 * @since 26/11/12
 */
public class PermissionTargetV1BuilderImpl implements PermissionTargetV1Builder {
    private String name;
    private String includesPattern;
    private String excludesPattern;
    private List<String> repositories;
    private Principals principals;

    public PermissionTargetV1Builder name(String name) {
        this.name = name;
        return this;
    }

    public PermissionTargetV1Builder includesPattern(String includesPattern) {
        this.includesPattern = includesPattern;
        return this;
    }

    public PermissionTargetV1Builder excludesPattern(String excludesPattern) {
        this.excludesPattern = excludesPattern;
        return this;
    }

    public PermissionTargetV1Builder repositories(String... repositories) {
        this.repositories = Arrays.asList(repositories);
        return this;
    }

    public PermissionTargetV1Builder principals(Principals principals) {
        this.principals = principals;
        return this;
    }

    public PermissionTargetV1 build() {
        return new PermissionTargetV1Impl(name, includesPattern, excludesPattern, repositories, principals);
    }
}
