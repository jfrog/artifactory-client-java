package org.jfrog.artifactory.client.model.builder.impl

import org.jfrog.artifactory.client.model.Permission
import org.jfrog.artifactory.client.model.User
import org.jfrog.artifactory.client.model.builder.GroupBuilder
import org.jfrog.artifactory.client.model.builder.PermissionBuilder
import org.jfrog.artifactory.client.model.builder.PrincipalBuilder
import org.jfrog.artifactory.client.model.builder.PrincipalsBuilder
import org.jfrog.artifactory.client.model.builder.SecurityBuilders
import org.jfrog.artifactory.client.model.builder.UserBuilder

/**
 *
 * Date: 10/18/12
 * Time: 11:30 AM
 * @author freds
 */
class SecurityBuildersImpl implements SecurityBuilders {

    static SecurityBuilders create() {
        new SecurityBuildersImpl()
    }

    private SecurityBuildersImpl() {
    }

    @Override
    UserBuilder userBuilder() {
        new UserBuilderImpl()
    }

    @Override
    GroupBuilder groupBuilder() {
        new GroupBuilderImpl()
    }

    @Override
    UserBuilder builderFrom(User from) {
        //TODO implement copy builder for User
        throw new UnsupportedOperationException("Not Yet Implemented");
    }

    @Override
    PermissionBuilder permissionBuilder() {
        return new PermissionBuilderImpl();
    }

    @Override
    PermissionBuilder builderFrom(Permission from) {
        //TODO implement copy builder for PermissionTarget
        throw new UnsupportedOperationException("Not Yet Implemented");
    }

    @Override
    public PrincipalsBuilder principalsBuilder() {
        return new PrincipalsBuilderImpl();
    }

    @Override
    public PrincipalBuilder principalBuilder() {
        return new PrincipalBuilderImpl();
    }
}
