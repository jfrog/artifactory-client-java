package org.jfrog.artifactory.client.model.impl;

import org.jfrog.artifactory.client.model.Principal;
import org.jfrog.artifactory.client.model.Privilege;

import java.util.Set;

import static java.util.Collections.unmodifiableSet;

/**
 * @author jbaruch
 * @since 22/11/12
 */
public class PrincipalImpl implements Principal {

    private final String name;
    private final Set<Privilege> privileges;

    public PrincipalImpl(String name, Set<Privilege> privileges) {
        this.name = name;
        this.privileges = unmodifiableSet(privileges);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Set<Privilege> getPrivileges() {
        return privileges;
    }

    @Override
    public boolean isAllowedTo(Privilege... privileges) {
        for (Privilege privilege : privileges) {
            if (!this.privileges.contains(privilege)) {
                return false;
            }
        }
        return true;
    }
}
