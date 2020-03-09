
package org.jfrog.artifactory.client.model.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.jfrog.artifactory.client.model.Principal;
import org.jfrog.artifactory.client.model.Principals;
import org.jfrog.artifactory.client.model.Privilege;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

/**
 * @author Alix Lourme
 * @since v2.1.1
 */
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class PrincipalsImpl implements Principals {

    // Using the most simple bean to facilitate Jackson de/serialization
    // So no constructor with Beans usage should exist

    private Map<String, Set<String>> users = new HashMap<>();
    private Map<String, Set<String>> groups = new HashMap<>();

    protected void setPrincipals(List<Principal> users, List<Principal> groups) {
        this.users = setMapPrivilegesFrom(users);
        this.groups = setMapPrivilegesFrom(groups);
    }

    @Override
    public List<Principal> getUsers() {
        return getListPrincipalFrom(this.users);
    }

    @Override
    public List<Principal> getGroups() {
        return getListPrincipalFrom(this.groups);
    }

    private static Map<String, Set<String>> setMapPrivilegesFrom(List<Principal> list) {
        Map<String, Set<String>> map = new HashMap<>();
        if (list != null && !list.isEmpty()) {
            for (Principal principal : list) {
                Set<String> strings = new HashSet<>(principal.getPrivileges().size());
                for (Privilege privilege : principal.getPrivileges()) {
                    strings.add(privilege.getAbbreviation());
                }
                map.put(principal.getName(), strings);
            }
        }
        return map;
    }

    private static List<Principal> getListPrincipalFrom(Map<String, Set<String>> map) {
        if (map == null) {
            return Collections.emptyList();
        }
        List<Principal> list = new ArrayList<>();
        for (Entry<String, Set<String>> e : map.entrySet()) {
            Set<Privilege> set = new HashSet<>();
            for (String str : e.getValue()) {
                set.add(Privilege.fromAbbreviation(str));
            }
            list.add(new PrincipalImpl(e.getKey(), set));
        }
        return list;
    }

    @Override
    public Principal getUser(String name) {
        for (Principal principal : getUsers()) {
            if (principal.getName().equals(name)) {
                return principal;
            }
        }
        return null;
    }

    @Override
    public Principal getGroup(String name) {
        for (Principal principal : getGroups()) {
            if (principal.getName().equals(name)) {
                return principal;
            }
        }
        return null;
    }
}