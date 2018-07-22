package org.jfrog.artifactory.client.v2.impl;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.jfrog.artifactory.client.v2.model.permissions.BuildPermission;
import org.jfrog.artifactory.client.v2.model.permissions.PermissionTarget;
import org.jfrog.artifactory.client.v2.model.permissions.RepositoryPermission;

/**
 * @author matank
 * @since 21/07/2018
 */
public class PermissionTargetImpl implements PermissionTarget {

    private String name;
    @JsonDeserialize(as = BuildPermissionImpl.class)
    private BuildPermission build;
    @JsonDeserialize(as = RepositoryPermissionImpl.class)
    private RepositoryPermission repo;

    //Required for JSON parsing of PermissionTargetImpl
    private PermissionTargetImpl() {
        super();
    }

    public PermissionTargetImpl(String name, BuildPermission build, RepositoryPermission repo) {
        this.name = name;
        this.build = build;
        this.repo = repo;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public BuildPermission getBuild() {
        return build;
    }

    @Override
    public RepositoryPermission getRepo() {
        return repo;
    }
}
