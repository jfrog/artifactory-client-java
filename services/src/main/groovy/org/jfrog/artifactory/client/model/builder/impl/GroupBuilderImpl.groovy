package org.jfrog.artifactory.client.model.builder.impl

import org.jfrog.artifactory.client.model.Group
import org.jfrog.artifactory.client.model.builder.GroupBuilder
import org.jfrog.artifactory.client.model.impl.GroupImpl

/**
 * Created by Jeka on 08/12/13.
 */
class GroupBuilderImpl implements GroupBuilder {
    String name
    String description
    String realm
    String realmAttributes
    boolean autoJoin

    @Override
    GroupBuilder name(String name) {
        this.name = name
        this
    }

    @Override
    GroupBuilder autoJoin(boolean autoJoin) {
        this.autoJoin = autoJoin
        return this
    }

    @Override
    GroupBuilder description(String description) {
        this.description = description
        return this
    }

    @Override
    public GroupBuilder realm(String realm) {
        this.realm = realm;
        return this
    }

    @Override
    public GroupBuilder realmAttributes(String realmAttributes) {
        this.realmAttributes = realmAttributes;
        return this
    }

    @Override
    Group build() {
        new GroupImpl(name, autoJoin, description, realm, realmAttributes)
    }
}
