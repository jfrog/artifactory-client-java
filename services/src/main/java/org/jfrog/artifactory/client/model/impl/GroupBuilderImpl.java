package org.jfrog.artifactory.client.model.impl;

import org.jfrog.artifactory.client.model.Group;
import org.jfrog.artifactory.client.model.builder.GroupBuilder;

/**
 * Created by Jeka on 08/12/13.
 */
public class GroupBuilderImpl implements GroupBuilder {
    private String name;
    private String description;
    private String realm;
    private String realmAttributes;
    private boolean autoJoin;
    private boolean adminPrivileges;

    public GroupBuilder name(String name) {
        this.name = name;
        return this;
    }

    public GroupBuilder autoJoin(boolean autoJoin) {
        this.autoJoin = autoJoin;
        return this;
    }

    public GroupBuilder adminPrivileges(boolean adminPrivileges) {
        this.adminPrivileges = adminPrivileges;
        return this;
    }

    public GroupBuilder description(String description) {
        this.description = description;
        return this;
    }

    public GroupBuilder realm(String realm) {
        this.realm = realm;
        return this;
    }

    public GroupBuilder realmAttributes(String realmAttributes) {
        this.realmAttributes = realmAttributes;
        return this;
    }

    public Group build() {
        return new GroupImpl(name, autoJoin, description, realm, realmAttributes, adminPrivileges);
    }
}
