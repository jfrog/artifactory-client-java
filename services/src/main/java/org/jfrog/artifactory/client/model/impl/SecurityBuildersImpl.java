package org.jfrog.artifactory.client.model.impl;

import org.jfrog.artifactory.client.model.PermissionTarget;
import org.jfrog.artifactory.client.model.User;
import org.jfrog.artifactory.client.model.builder.*;

/**
 * Date: 10/18/12
 * Time: 11:30 AM
 *
 * @author freds
 */
public class SecurityBuildersImpl implements SecurityBuilders {
    public static SecurityBuilders create() {
        return new SecurityBuildersImpl();
    }

    private SecurityBuildersImpl() {
    }

    public UserBuilder userBuilder() {
        return new UserBuilderImpl();
    }

    public GroupBuilder groupBuilder() {
        return new GroupBuilderImpl();
    }

    public UserBuilder builderFrom(User from) {
        return null;//TODO implement copy builder for User
    }

    public PermissionTargetBuilder permissionTargetBuilder() {
        return new PermissionTargetBuilderImpl();
    }

    public PermissionTargetBuilder builderFrom(PermissionTarget from) {
        return null;//TODO implement copy builder for PermissionTarget
    }

    public PrincipalsBuilder principalsBuilder() {
        return new PrincipalsBuilderImpl();
    }

    public PrincipalBuilder principalBuilder() {
        return new PrincipalBuilderImpl();
    }
}
