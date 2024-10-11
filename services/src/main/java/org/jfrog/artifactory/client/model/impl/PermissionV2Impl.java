package org.jfrog.artifactory.client.model.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.jfrog.artifactory.client.model.Actions;
import org.jfrog.artifactory.client.model.PermissionV2;
import org.jfrog.artifactory.client.model.PrivilegeV2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class PermissionV2Impl implements PermissionV2 {
    @JsonProperty("include-patterns")
    private List<String> includePatterns;
    @JsonProperty("exclude-patterns")
    private List<String> excludePatterns;
    private List<String> repositories;
    @JsonDeserialize(as = ActionsImpl.class)
    private Actions actions;

    //Required for JSON parsing of PermissionV2Impl
    public PermissionV2Impl() {
        this.includePatterns= new ArrayList<>();
        this.excludePatterns= new ArrayList<>();
        this.repositories= new ArrayList<>();
        this.actions= new ActionsImpl();
    }

    public PermissionV2Impl(List<String> includePatterns, List<String> excludePatterns, List<String> repositories, Actions actions) {
        this.includePatterns = Optional.ofNullable(includePatterns).orElse(Collections.emptyList());
        this.excludePatterns = Optional.ofNullable(excludePatterns).orElse(Collections.emptyList());
        this.repositories = Optional.ofNullable(repositories).orElse(Collections.emptyList());
        this.actions = Optional.ofNullable(actions).orElse(new ActionsImpl());
    }

    public boolean isUserAllowedTo(String user, PrivilegeV2 privilege){
        return actions.isUserAllowedTo(user, privilege);
    }

    public boolean isGroupAllowedTo(String group, PrivilegeV2 privilege){
        return actions.isGroupAllowedTo(group, privilege);
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

    public void setActions(Actions actions) {
        this.actions = actions;
    }

    public void setRepositories(List<String> repositories) {
        this.repositories = repositories;
    }

    public void setIncludePatterns(List<String> includePatterns) {
        this.includePatterns = includePatterns;
    }

    public void setExcludePatterns(List<String> excludePatterns) {
        this.excludePatterns = excludePatterns;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof PermissionV2)) {
            return false;
        }
        PermissionV2 other = (PermissionV2) obj;
        boolean areEquals = (includePatterns==null && other.getIncludePatterns()==null) ||
                (includePatterns==null && other.getIncludePatterns().isEmpty()) ||
                (includePatterns.isEmpty() && other.getIncludePatterns()==null) ||
                (includePatterns!=null && includePatterns.equals(other.getIncludePatterns()));

        areEquals &= (excludePatterns==null && other.getExcludePatterns()==null) ||
                (excludePatterns==null && other.getExcludePatterns().isEmpty()) ||
                (excludePatterns.isEmpty() && other.getExcludePatterns()==null) ||
                (excludePatterns!=null && excludePatterns.equals(other.getExcludePatterns()));

        areEquals &= (repositories==null && other.getRepositories()==null) ||
                (repositories==null && other.getRepositories().isEmpty()) ||
                (repositories.isEmpty() && other.getRepositories()==null) ||
                (repositories!=null && repositories.equals(other.getRepositories()));

        areEquals &= (actions==null && other.getActions()==null) || (actions!=null && actions.equals(other.getActions()));
        return areEquals;
    }
}
