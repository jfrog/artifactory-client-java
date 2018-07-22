package org.jfrog.artifactory.client.v2.impl;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.jfrog.artifactory.client.v2.model.permissions.Action;
import org.jfrog.artifactory.client.v2.model.permissions.ActionType;
import org.jfrog.artifactory.client.v2.model.permissions.Actions;

import java.util.*;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class ActionsImpl implements Actions {

    private Map<String, Set<String>> users = new HashMap<>();
    private Map<String, Set<String>> groups = new HashMap<>();

    private static Map<String, Set<String>> setMapActionsFrom(List<Action> list) {
        Map<String, Set<String>> map = new HashMap<>();
        if (list != null && !list.isEmpty()) {
            for (Action action : list) {
                Set<String> types = new HashSet<>(action.getActionTypes().size());
                for (ActionType actionType : action.getActionTypes()) {
                    types.add(actionType.getAbbreviation());
                }
                map.put(action.getName(), types);
            }
        }
        return map;
    }

    private static List<Action> getListActionFrom(Map<String, Set<String>> map) {
        if (map == null) {
            return Collections.emptyList();
        }
        List<Action> list = new ArrayList<>();
        for (Map.Entry<String, Set<String>> e : map.entrySet()) {
            Set<ActionType> set = new HashSet<>();
            for (String c : e.getValue()) {
                set.add(ActionType.fromAbbreviation(c));
            }
            list.add(new ActionImpl(e.getKey(), set));
        }
        return list;
    }

    protected void setActions(List<Action> users, List<Action> groups) {
        this.users = setMapActionsFrom(users);
        this.groups = setMapActionsFrom(groups);
    }

    @Override
    public List<Action> getUsers() {
        return getListActionFrom(this.users);
    }

    @Override
    public List<Action> getGroups() {
        return getListActionFrom(this.groups);
    }

    @Override
    public Action getUser(String name) {
        for (Action action : getUsers()) {
            if (action.getName().equals(name)) {
                return action;
            }
        }
        return null;
    }

    @Override
    public Action getGroup(String name) {
        for (Action action : getGroups()) {
            if (action.getName().equals(name)) {
                return action;
            }
        }
        return null;
    }
}
