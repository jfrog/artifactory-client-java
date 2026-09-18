package org.jfrog.artifactory.client.model.impl;

import org.jfrog.artifactory.client.model.Actions;
import org.jfrog.artifactory.client.model.PrivilegeV2;
import org.jfrog.artifactory.client.model.builder.ActionsBuilder;

import java.util.*;

public class ActionsBuilderImpl implements ActionsBuilder {
    private Map<String, Set<PrivilegeV2>> usersGrantedActions;
    private Map<String, Set<PrivilegeV2>> groupsGrantedActions;

    public ActionsBuilderImpl() {
        this.usersGrantedActions= new HashMap<>();
        this.groupsGrantedActions = new HashMap<>();
    }
    @Override
    public ActionsBuilder addUser(String user, PrivilegeV2... privileges) {
        Set<PrivilegeV2> userPrivileges = new HashSet<>(Arrays.asList(privileges));
        usersGrantedActions.put(user, userPrivileges);
        return this;
    }

    @Override
    public ActionsBuilder addGroup(String group, PrivilegeV2... privileges) {
        Set<PrivilegeV2> groupPrivileges = new HashSet<>(Arrays.asList(privileges));
        groupsGrantedActions.put(group, groupPrivileges);
        return this;
    }

    @Override
    public Actions build() {
       return new ActionsImpl(usersGrantedActions, groupsGrantedActions);
    }
}
