package org.jfrog.artifactory.client.model.impl;

import org.apache.commons.lang3.StringUtils;
import org.jfrog.artifactory.client.model.Actions;
import org.jfrog.artifactory.client.model.PrivilegeV2;

import java.util.*;

public class ActionsImpl implements Actions {

    private Map<String, Set<PrivilegeV2>> users;

    private Map<String, Set<PrivilegeV2>> groups;

    public ActionsImpl() {
        super();
        this.users=new HashMap<>();
        this.groups=new HashMap<>();
    }

    public ActionsImpl(Map<String, Set<PrivilegeV2>> users, Map<String, Set<PrivilegeV2>> groups) {
        this.users = Optional.ofNullable(users).orElse(Collections.emptyMap());
        this.groups = Optional.ofNullable(groups).orElse(Collections.emptyMap());
    }

    @Override
    public boolean isUserAllowedTo(String user, PrivilegeV2 privilege) {
        if(StringUtils.isBlank(user) || privilege == null) {
            return false;
        }
        return users.containsKey(user) && users.get(user).contains(privilege);
    }

    @Override
    public boolean isGroupAllowedTo(String group, PrivilegeV2 privilege) {
        if(StringUtils.isBlank(group) || privilege == null) {
            return false;
        }
        return groups.containsKey(group) && groups.get(group).contains(privilege);
    }

    @Override
    public Map<String, Set<PrivilegeV2>> getUsers() {
        return users;
    }

    @Override
    public Map<String, Set<PrivilegeV2>> getGroups() {
        return groups;
    }

    public void setUsers(Map<String, Set<PrivilegeV2>> users) {
        this.users = users;
    }

    public void setGroups(Map<String, Set<PrivilegeV2>> groups) {
        this.groups = groups;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Actions)) {
            return false;
        }
        Actions other = (Actions) obj;
        boolean areEquals = (users==null && other.getUsers()==null) ||
                (users==null && other.getUsers().isEmpty()) ||
                (users.isEmpty() && other.getUsers()==null) ||
                (users!=null && users.equals(other.getUsers()));
        areEquals &= (groups==null && other.getGroups()==null) ||
                (groups==null && other.getGroups().isEmpty()) ||
                (groups.isEmpty() && other.getGroups()==null) ||
                (groups!=null && groups.equals(other.getGroups()));
        return areEquals;
    }
}
