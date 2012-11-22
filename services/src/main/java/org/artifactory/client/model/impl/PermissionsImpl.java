package org.artifactory.client.model.impl;

import org.artifactory.client.model.Permissions;
import org.artifactory.client.model.Principal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author jbaruch
 * @since 22/11/12
 */
public class PermissionsImpl implements Permissions {

    private final Map<String, Principal> users;
    private final Map<String, Principal> groups;

    public PermissionsImpl(Map<String, Principal> users, Map<String, Principal> groups) {
        this.users = users;
        this.groups = groups;
    }

    @Override
    public List<? extends Principal> getUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public List<? extends Principal> getGroups() {
        return new ArrayList<>(groups.values());
    }

    @Override
    public Principal user(String user) {
        return users.get(user);
    }

    @Override
    public Principal group(String group) {
        return groups.get(group);
    }
}
