package org.jfrog.artifactory.client.model.impl;

import org.jfrog.artifactory.client.model.BuildPermission;
import org.jfrog.artifactory.client.model.PermissionTarget;
import org.jfrog.artifactory.client.model.RepositoryPermission;
import org.jfrog.artifactory.client.model.builder.PermissionTargetBuilder;

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
