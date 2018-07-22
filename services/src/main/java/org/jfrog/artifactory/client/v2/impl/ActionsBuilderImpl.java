package org.jfrog.artifactory.client.v2.impl;

import org.jfrog.artifactory.client.v2.model.builder.ActionsBuilder;
import org.jfrog.artifactory.client.v2.model.permissions.Action;
import org.jfrog.artifactory.client.v2.model.permissions.Actions;

import java.util.Arrays;
import java.util.List;

public class ActionsBuilderImpl implements ActionsBuilder {

    private List<Action> users;
    private List<Action> groups;

    @Override
    public ActionsBuilder users(Action... users) {
        this.users = Arrays.asList(users);
        return this;
    }

    @Override
    public ActionsBuilder groups(Action... groups) {
        this.groups = Arrays.asList(groups);
        return this;
    }

    @Override
    public Actions build() {
        ActionsImpl act = new ActionsImpl();
        act.setActions(users, groups);
        return act;
    }
}
