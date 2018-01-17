package org.jfrog.artifactory.client.model.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("principals")
    @JsonDeserialize(as = PrincipalsImpl.class)
    private Principals principals;

    //Required for JSON parsing of PermissionTargetImpl
    private PermissionTargetImpl() {
        super();
    }

    protected PermissionTargetImpl(String name, String includesPattern, String excludesPattern, List<String> repositories, Principals principals) {
        this.name = name;
        this.includesPattern = includesPattern;
        this.excludesPattern = excludesPattern;
        this.repositories = repositories;
        this.principals = principals;
    }

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

    public Principals getPrincipals() {
        return principals;
    }

    public void setRepositories(List<String> repositories) {
        this.repositories = repositories;
    }
}
