package org.artifactory.client.model.builder;

import org.artifactory.client.model.User;

import java.util.Collection;

/**
 * Date: 10/18/12
 * Time: 9:28 AM
 *
 * @author freds
 */
public interface UserBuilder {
    UserBuilder name(String name);

    UserBuilder email(String email);

    UserBuilder password(String password);

    UserBuilder admin(boolean admin);

    UserBuilder profileUpdatable(boolean profileUpdatable);

    UserBuilder internalPasswordDisabled(boolean internalPasswordDisabled);

    UserBuilder groups(Collection<String> groups);

    UserBuilder addGroup(String group);

    User build();
}
