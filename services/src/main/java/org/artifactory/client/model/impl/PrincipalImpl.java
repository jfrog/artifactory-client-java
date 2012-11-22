package org.artifactory.client.model.impl;

import org.artifactory.client.model.Permission;
import org.artifactory.client.model.Principal;

import java.util.List;

/**
 * @author jbaruch
 * @since 22/11/12
 */
public class PrincipalImpl implements Principal {

    private final String name;
    private final List<Permission> permissions;

    public PrincipalImpl(String name, List<Permission> permissions) {
        this.name = name;
        this.permissions = permissions;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<Permission> getPermissions() {
        return permissions;
    }

    @Override
    public boolean isAllowedTo(Permission... permissions) {
        for (Permission permission : permissions) {
            if(!this.permissions.contains(permission)) {
                return false;
            }
        }
        return true;
    }
}
