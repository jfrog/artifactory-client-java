package org.jfrog.artifactory.client.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Actions {
    Map<String, Set<PrivilegeV2>> getUsers();
    Map<String, Set<PrivilegeV2>> getGroups();

    boolean isUserAllowedTo(String user, PrivilegeV2 privilege);
    boolean isGroupAllowedTo(String group, PrivilegeV2 privilege);
    @Override
    boolean equals(Object o);
}
