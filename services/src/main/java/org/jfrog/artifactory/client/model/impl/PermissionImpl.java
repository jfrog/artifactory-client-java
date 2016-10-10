package org.jfrog.artifactory.client.model.impl;

import java.util.List;

import org.jfrog.artifactory.client.model.Permission;
import org.jfrog.artifactory.client.model.Principals;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @author jbaruch
 * @since 26/11/12
 */
public class PermissionImpl implements Permission {

    private String name;
    private String includesPattern;
    private String excludesPattern;
    private List<String> repositories;
    @JsonDeserialize(as = PrincipalsImpl.class)
    private Principals principals;

    public PermissionImpl() {
        super();
    }

    public PermissionImpl(String name, String includesPattern, String excludesPattern, List<String> repositories, Principals principals) {
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

    @Override
    public String getIncludesPattern() {
        return includesPattern;
    }

    @Override
    public String getExcludesPattern() {
        return excludesPattern;
    }

    @Override
    public List<String> getRepositories() {
        return repositories;
    }

    @Override
    public Principals getPrincipals() {
        return principals;
    }

}
