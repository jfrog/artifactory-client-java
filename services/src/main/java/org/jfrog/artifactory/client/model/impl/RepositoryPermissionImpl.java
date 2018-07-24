package org.jfrog.artifactory.client.model.impl;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.jfrog.artifactory.client.model.Actions;
import org.jfrog.artifactory.client.model.RepositoryPermission;

import java.util.List;

public class RepositoryPermissionImpl implements RepositoryPermission {

    private List<String> includePatterns;
    private List<String> excludePatterns;
    private List<String> repositories;
    @JsonDeserialize(as = ActionsImpl.class)
    private Actions actions;

    public RepositoryPermissionImpl() {
        super();
    }

    public RepositoryPermissionImpl(List<String> includePatterns, List<String> excludePatterns, List<String> repositories, Actions actions) {
        this.includePatterns = includePatterns;
        this.excludePatterns = excludePatterns;
        this.repositories = repositories;
        this.actions = actions;
    }

    @Override
    public List<String> getIncludePatterns() {
        return includePatterns;
    }

    @Override
    public List<String> getExcludePatterns() {
        return excludePatterns;
    }

    @Override
    public List<String> getRepositories() {
        return repositories;
    }

    @Override
    public Actions getActions() {
        return actions;
    }
}
