package org.artifactory.client.model.impl;

import org.artifactory.client.model.Privilege;
import org.artifactory.client.model.Principal;

import java.util.List;

/**
 * @author jbaruch
 * @since 22/11/12
 */
public class PrincipalImpl implements Principal {

    private final String name;
    private final List<Privilege> privileges;

    public PrincipalImpl(String name, List<Privilege> privileges) {
        this.name = name;
        this.privileges = privileges;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<Privilege> getPrivileges() {
        return privileges;
    }

    @Override
    public boolean isAllowedTo(Privilege... privileges) {
        for (Privilege privilege : privileges) {
            if(!this.privileges.contains(privilege)) {
                return false;
            }
        }
        return true;
    }
}
