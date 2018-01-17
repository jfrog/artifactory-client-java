package org.jfrog.artifactory.client.model.impl;

import org.jfrog.artifactory.client.model.User;
import org.jfrog.artifactory.client.model.builder.UserBuilder;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Date: 10/18/12
 * Time: 11:38 AM
 *
 * @author freds
 */
public class UserBuilderImpl implements UserBuilder {
    private String name;
    private String email;
    private String password;
    private boolean admin = false;
    private boolean profileUpdatable = true;
    private boolean internalPasswordDisabled = false;
    private Set<String> groups = new HashSet<String>(1);

    public UserBuilder name(String name) {
        this.name = name;
        return this;
    }

    public UserBuilder email(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder password(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder admin(boolean admin) {
        this.admin = admin;
        return this;
    }

    public UserBuilder profileUpdatable(boolean profileUpdatable) {
        this.profileUpdatable = profileUpdatable;
        return this;
    }

    public UserBuilder internalPasswordDisabled(boolean internalPasswordDisabled) {
        this.internalPasswordDisabled = internalPasswordDisabled;
        return this;
    }

    public UserBuilder groups(Collection<String> groups) {
        this.groups.addAll(groups);
        return this;
    }

    public UserBuilder addGroup(String group) {
        this.groups.add(group);
        return this;
    }

    public void validate() {
        if ("anonymous".equals(name)) {
            throw new IllegalArgumentException("Cannot create or update user with name 'anonymous'.");
        }
    }

    public User build() {
        validate();
        return new UserImpl(name, email, password, admin, profileUpdatable, internalPasswordDisabled, groups);
    }
}
