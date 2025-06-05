package org.jfrog.artifactory.client.model.impl;

import org.jfrog.artifactory.client.model.Actions;
import org.jfrog.artifactory.client.model.PermissionV2;
import org.jfrog.artifactory.client.model.builder.PermissionV2Builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PermissionV2BuilderImpl implements PermissionV2Builder {


    private List<String> includePatterns;
    private List<String> excludePatterns;
    private List<String> repositories;
    private Actions actions;

    public PermissionV2BuilderImpl() {
        this.includePatterns = new ArrayList<>();
        this.excludePatterns = new ArrayList<>();
        this.repositories = new ArrayList<>();
    }

    @Override
    public PermissionV2Builder includePatterns(String... includePatterns) {
        this.includePatterns= Arrays.asList(includePatterns);
        return this;
    }

    @Override
    public PermissionV2Builder excludePatterns(String... excludePatterns) {
        this.excludePatterns= Arrays.asList(excludePatterns);
        return this;
    }

    @Override
    public PermissionV2Builder repositories(String... repositories) {
        this.repositories= Arrays.asList(repositories);
        return this;
    }

    @Override
    public PermissionV2Builder actions(Actions actions) {
        this.actions=actions;
        return this;
    }

    @Override
    public PermissionV2 build() {
        PermissionV2Impl permissionV2 = new PermissionV2Impl();
        permissionV2.setActions(this.actions);
        permissionV2.setRepositories(this.repositories);
        permissionV2.setIncludePatterns(this.includePatterns);
        permissionV2.setExcludePatterns(this.excludePatterns);
        return permissionV2;
    }
}
