package org.jfrog.artifactory.client.model.impl;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.jfrog.artifactory.client.model.BuildPermission;
import org.jfrog.artifactory.client.model.PermissionTarget;
import org.jfrog.artifactory.client.model.RepositoryPermission;

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
    public BuildPermission getBuildPermission() {
        return build;
    }

    @Override
    public RepositoryPermission getRepositoryPermission() {
        return repo;
    }
}
