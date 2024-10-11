package org.jfrog.artifactory.client.model.impl;

import org.jfrog.artifactory.client.model.PermissionTargetV2;
import org.jfrog.artifactory.client.model.PermissionV2;
import org.jfrog.artifactory.client.model.builder.PermissionTargetV2Builder;

public class PermissionTargetV2BuilderImpl implements PermissionTargetV2Builder {
    private String name;
    private PermissionV2 repo;
    private PermissionV2 build;
    private PermissionV2 releaseBundle;
    @Override
    public PermissionTargetV2Builder name(String name) {
        this.name = name;
        return this;
    }

    @Override
    public PermissionTargetV2Builder repo(PermissionV2 repo) {
        this.repo = repo;
        return this;
    }

    @Override
    public PermissionTargetV2Builder build(PermissionV2 build) {
        this.build = build;
        return this;
    }

    @Override
    public PermissionTargetV2Builder releaseBundle(PermissionV2 releaseBundle) {
        this.releaseBundle = releaseBundle;
        return this;
    }

    @Override
    public PermissionTargetV2 build() {
        return new PermissionTargetV2Impl(this.name,
                this.repo,
                this.build,
                this.releaseBundle);
    }
}
