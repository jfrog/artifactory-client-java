package org.jfrog.artifactory.client.model.builder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.jfrog.artifactory.client.model.User;

import java.util.Collection;

/**
 * Date: 10/18/12
 * Time: 9:28 AM
 *
 * @author freds
 */
@JsonIgnoreProperties(ignoreUnknown = true)
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

    void validate();
}
