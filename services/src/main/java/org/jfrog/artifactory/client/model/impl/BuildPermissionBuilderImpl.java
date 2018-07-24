package org.jfrog.artifactory.client.model.impl;

import org.jfrog.artifactory.client.model.Actions;
import org.jfrog.artifactory.client.model.BuildPermission;
import org.jfrog.artifactory.client.model.builder.BuildPermissionBuilder;

import java.util.Arrays;
import java.util.List;

public class BuildPermissionBuilderImpl implements BuildPermissionBuilder {

    private String regex;
    private List<String> builds;
    private Actions actions;

    @Override
    public BuildPermissionBuilder regex(String regex) {
        this.regex = regex;
        return this;
    }

    @Override
    public BuildPermissionBuilder builds(String... build) {
        this.builds = Arrays.asList(build);
        return this;
    }

    @Override
    public BuildPermissionBuilder actions(Actions actions) {
        this.actions = actions;
        return this;
    }

    @Override
    public BuildPermission build() {
        return new BuildPermissionImpl(regex, builds, actions);
    }
}
