package org.artifactory.client.model.builder.impl

import org.artifactory.client.model.User
import org.artifactory.client.model.builder.SecurityBuilders
import org.artifactory.client.model.builder.UserBuilder

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
        return null  //To change body of implemented methods use File | Settings | File Templates.
    }
}
