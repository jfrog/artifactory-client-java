package org.jfrog.artifactory.client.v2.impl;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.jfrog.artifactory.client.v2.model.permissions.Actions;
import org.jfrog.artifactory.client.v2.model.permissions.BuildPermission;

import java.util.List;

public class BuildPermissionImpl implements BuildPermission {

    private String regex;
    private List<String> builds;
    @JsonDeserialize(as = ActionsImpl.class)
    private Actions actions;

    public BuildPermissionImpl() {
        super();
    }

    public BuildPermissionImpl(String regex, List<String> builds, Actions actions) {
        this.regex = regex;
        this.builds = builds;
        this.actions = actions;
    }

    @Override
    public String getRegex() {
        return regex;
    }

    @Override
    public List<String> getBuilds() {
        return builds;
    }

    @Override
    public Actions getActions() {
        return actions;
    }

}
