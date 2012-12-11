package org.artifactory.client.model.builder.impl

import org.artifactory.client.model.User
import org.artifactory.client.model.builder.UserBuilder
import org.artifactory.client.model.impl.UserImpl

/**
 *
 * Date: 10/18/12
 * Time: 11:38 AM
 * @author freds
 */
class UserBuilderImpl implements UserBuilder {
    String name
    String email
    String password
    boolean admin = false
    boolean profileUpdatable = true
    boolean internalPasswordDisabled = false
    Date lastLoggedIn
    Set<String> groups = new HashSet<String>(1)

    @Override
    UserBuilder name(String name) {
        this.name = name
        return this
    }

    @Override
    UserBuilder email(String email) {
        this.email = email
        return this
    }

    @Override
    UserBuilder password(String password) {
        this.password = password
        return this
    }

    @Override
    UserBuilder admin(boolean admin) {
        this.admin = admin
        return this
    }

    @Override
    UserBuilder profileUpdatable(boolean profileUpdatable) {
        this.profileUpdatable = profileUpdatable
        return this
    }

    @Override
    UserBuilder internalPasswordDisabled(boolean internalPasswordDisabled) {
        this.internalPasswordDisabled = internalPasswordDisabled
        return this
    }

    @Override
    UserBuilder groups(Collection<String> groups) {
        this.groups.addAll(groups)
        return this
    }

    @Override
    UserBuilder addGroup(String group) {
        this.groups << group
        return this
    }

    @SuppressWarnings("GroovyAccessibility")
    @Override
    User build() {
        new UserImpl(name, email, password, admin, profileUpdatable, internalPasswordDisabled,
                lastLoggedIn, groups)
    }
}
