package org.jfrog.artifactory.client.v2.impl;

import org.jfrog.artifactory.client.v2.model.builder.ActionBuilder;
import org.jfrog.artifactory.client.v2.model.permissions.Action;
import org.jfrog.artifactory.client.v2.model.permissions.ActionType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ActionBuilderImpl implements ActionBuilder {

    private String name;
    private Set<ActionType> actions = new HashSet<>();

    @Override
    public ActionBuilder name(String name) {
        this.name = name;
        return this;
    }

    @Override
    public ActionBuilder actions(ActionType... actions) {
        if (!this.actions.isEmpty()) {
            this.actions.clear();
        }

        this.actions.addAll(Arrays.asList(actions));
        return this;
    }

    @Override
    public Action build() {
        return new ActionImpl(name, actions);
    }
}
