package org.jfrog.artifactory.client.model.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.jfrog.artifactory.client.model.PermissionTargetV1;
import org.jfrog.artifactory.client.model.Principals;

import java.util.List;

/**
 * @author jbaruch
 * @since 26/11/12
 */
public class PermissionTargetV1Impl implements PermissionTargetV1 {

    private String name;
    private String includesPattern;
    private String excludesPattern;
    private List<String> repositories;
    @JsonProperty("principals")
    @JsonDeserialize(as = PrincipalsImpl.class)
    private Principals principals;

    //Required for JSON parsing of PermissionTargetImpl
    private PermissionTargetV1Impl() {
        super();
    }

    protected PermissionTargetV1Impl(String name, String includesPattern, String excludesPattern, List<String> repositories, Principals principals) {
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
