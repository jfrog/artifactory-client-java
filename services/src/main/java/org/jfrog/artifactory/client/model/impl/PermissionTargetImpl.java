package org.jfrog.artifactory.client.model.impl;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.jfrog.artifactory.client.model.PermissionTarget;
import org.jfrog.artifactory.client.model.Principals;

import java.util.List;

/**
 * @author jbaruch
 * @since 26/11/12
 */
public class PermissionTargetImpl implements PermissionTarget {

    private String name;
    private String includesPattern;
    private String excludesPattern;
    private List<String> repositories;
    @JsonDeserialize(as = PrincipalsImpl.class)
    private Principals principals;

    public PermissionTargetImpl() {
        super();
    }

    public PermissionTargetImpl(String name, String includesPattern, String excludesPattern, List<String> repositories, Principals principals) {
        this.name = name;
        this.includesPattern = includesPattern;
        this.excludesPattern = excludesPattern;
        this.repositories = repositories;
        this.principals = principals;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getIncludesPattern() {
        return includesPattern;
    }

    public String getExcludesPattern() {
        return excludesPattern;
    }

    public List<String> getRepositories() {
        return repositories;
    }

    @Override
    public Principals getPrincipals() {
        return principals;
    }
}
