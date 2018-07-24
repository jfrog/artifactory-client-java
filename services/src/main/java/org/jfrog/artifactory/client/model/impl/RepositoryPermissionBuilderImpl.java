package org.jfrog.artifactory.client.model.impl;

import org.jfrog.artifactory.client.model.Actions;
import org.jfrog.artifactory.client.model.RepositoryPermission;
import org.jfrog.artifactory.client.model.builder.RepositoryPermissionBuilder;

import java.util.Arrays;
import java.util.List;

public class RepositoryPermissionBuilderImpl implements RepositoryPermissionBuilder {

    private List<String> includePatterns;
    private List<String> excludePatterns;
    private List<String> repositories;
    private Actions actions;

    @Override
    public RepositoryPermissionBuilder includePatterns(String... includePatterns) {
        this.includePatterns = Arrays.asList(includePatterns);
        return this;
    }

    @Override
    public RepositoryPermissionBuilder excludePatterns(String... excludePatterns) {
        this.excludePatterns = Arrays.asList(excludePatterns);
        return this;
    }

    @Override
    public RepositoryPermissionBuilder repositories(String... repositories) {
        this.repositories = Arrays.asList(repositories);
        return this;
    }

    @Override
    public RepositoryPermissionBuilder actions(Actions actions) {
        this.actions = actions;
        return this;
    }

    @Override
    public RepositoryPermission build() {
        return new RepositoryPermissionImpl(includePatterns, excludePatterns, repositories, actions);
    }
}
