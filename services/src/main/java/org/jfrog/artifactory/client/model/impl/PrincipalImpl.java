package org.jfrog.artifactory.client.model.impl;

import static java.util.Collections.unmodifiableSet;

import java.util.Set;

import org.jfrog.artifactory.client.model.Principal;
import org.jfrog.artifactory.client.model.Privilege;

/**
 * @author jbaruch
 * @since 22/11/12
 */
public class PrincipalImpl implements Principal {

    private final String name;
    private final Set<Privilege> privileges;

    // TODO : http://stackoverflow.com/questions/25185545/jackson-object-mapper-annotations-to-deserialize-a-inner-collection ?

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
