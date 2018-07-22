package org.jfrog.artifactory.client.v2.impl;

import org.jfrog.artifactory.client.v2.model.builder.*;
import org.jfrog.artifactory.client.v2.model.permissions.PermissionTarget;

/**
 * @author matank
 */
public class SecurityBuildersImpl implements SecurityBuilders {
    private SecurityBuildersImpl() {
    }

    public static SecurityBuilders create() {
        return new SecurityBuildersImpl();
    }

    @Override
    public PermissionTargetBuilder permissionTargetBuilder() {
        return new PermissionTargetBuilderImpl();
    }

    @Override
    public PermissionTargetBuilder builderFrom(PermissionTarget from) {
        return null;
    }

    @Override
    public BuildPermissionBuilder buildPermissionBuilder() {
        return new BuildPermissionBuilderImpl();
    }

    @Override
    public RepositoryPermissionBuilder repositoryPermissionBuilder() {
        return new RepositoryPermissionBuilderImpl();
    }

    @Override
    public ActionsBuilder actionsBuilder() {
        return new ActionsBuilderImpl();
    }

    @Override
    public ActionBuilder actionBuilder() {
        return new ActionBuilderImpl();
    }
}
