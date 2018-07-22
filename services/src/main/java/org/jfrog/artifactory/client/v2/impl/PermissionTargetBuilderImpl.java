package org.jfrog.artifactory.client.v2.impl;

import org.jfrog.artifactory.client.v2.model.builder.PermissionTargetBuilder;
import org.jfrog.artifactory.client.v2.model.permissions.BuildPermission;
import org.jfrog.artifactory.client.v2.model.permissions.PermissionTarget;
import org.jfrog.artifactory.client.v2.model.permissions.RepositoryPermission;

public class PermissionTargetBuilderImpl implements PermissionTargetBuilder {

    private String name;
    private RepositoryPermission repositoryPermission;
    private BuildPermission buildPermission;

    @Override
    public PermissionTargetBuilder name(String name) {
        this.name = name;
        return this;
    }

    @Override
    public PermissionTargetBuilder repositoryPermission(RepositoryPermission repositoryPermission) {
        this.repositoryPermission = repositoryPermission;
        return this;
    }

    @Override
    public PermissionTargetBuilder buildPermission(BuildPermission buildPermission) {
        this.buildPermission = buildPermission;
        return this;
    }

    @Override
    public PermissionTarget build() {
        return new PermissionTargetImpl(name, buildPermission, repositoryPermission);
    }
}
