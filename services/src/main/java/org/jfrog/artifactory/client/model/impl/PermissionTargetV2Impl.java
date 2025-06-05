package org.jfrog.artifactory.client.model.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.apache.commons.lang3.StringUtils;
import org.jfrog.artifactory.client.model.PermissionTargetV2;
import org.jfrog.artifactory.client.model.PermissionV2;

import java.util.Optional;

public class PermissionTargetV2Impl implements PermissionTargetV2 {
    private String name;
    @JsonProperty("repo")
    @JsonDeserialize(as = PermissionV2Impl.class)
    private PermissionV2 repo;
    @JsonProperty("build")
    @JsonDeserialize(as = PermissionV2Impl.class)
    private PermissionV2 build;
    @JsonProperty("releaseBundle")
    @JsonDeserialize(as = PermissionV2Impl.class)
    private PermissionV2 releaseBundle;

    public PermissionTargetV2Impl() {
        this.repo=new PermissionV2Impl();
        this.build= new PermissionV2Impl();
        this.releaseBundle= new PermissionV2Impl();
    }

    public PermissionTargetV2Impl(String name, PermissionV2 repo, PermissionV2 build, PermissionV2 releaseBundle) {
        this.name = Optional.ofNullable(name).orElse(StringUtils.EMPTY);
        this.repo = Optional.ofNullable(repo).orElse(new PermissionV2Impl());
        this.build = Optional.ofNullable(build).orElse(new PermissionV2Impl());
        this.releaseBundle = Optional.ofNullable(releaseBundle).orElse(new PermissionV2Impl());
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public PermissionV2 getRepo() {
        return repo;
    }
    @Override
    public PermissionV2 getBuild() {
        return build;
    }
    @Override
    public PermissionV2 getReleaseBundle() {
        return releaseBundle;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRepo(PermissionV2 repo) {
        this.repo = repo;
    }

    public void setBuild(PermissionV2 build) {
        this.build = build;
    }

    public void setReleaseBundle(PermissionV2 releaseBundle) {
        this.releaseBundle = releaseBundle;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PermissionTargetV2)) {
            return false;
        }
        PermissionTargetV2 other = (PermissionTargetV2) obj;
        boolean areEquals = StringUtils.equals(this.name, other.getName());
        areEquals &= (this.repo == null && other.getRepo() == null) || (this.repo != null && this.repo.equals(other.getRepo()));
        areEquals &= (this.build == null && other.getBuild() == null) || (this.build != null && this.build.equals(other.getBuild()));
        areEquals &= (this.releaseBundle == null && other.getReleaseBundle() == null) || (this.releaseBundle != null && this.releaseBundle.equals(other.getReleaseBundle()));
        return areEquals;
    }
}
