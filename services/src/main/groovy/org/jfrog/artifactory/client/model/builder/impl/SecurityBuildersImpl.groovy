package org.jfrog.artifactory.client.model.builder.impl

import org.jfrog.artifactory.client.model.PermissionTarget
import org.jfrog.artifactory.client.model.User
import org.jfrog.artifactory.client.model.builder.PermissionTargetBuilder
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
        return new UserBuilderImpl()
    }

    @Override
    UserBuilder builderFrom(User from) {
        return null  //TODO implement copy builder for User
    }

    @Override
    PermissionTargetBuilder permissionTargetBuilder() {
        return new PermissionTargetBuilderImpl();
    }

    @Override
    PermissionTargetBuilder builderFrom(PermissionTarget from) {
        return null  //TODO implement copy builder for PermissionTarget
    }
}
