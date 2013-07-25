package org.jfrog.artifactory.client.model.impl;

import com.fasterxml.jackson.annotation.JsonValue;
import org.jfrog.artifactory.client.model.SnapshotVersionBehavior;

/**
 * @author jbaruch
 * @since 13/08/12
 */
public enum SnapshotVersionBehaviorImpl implements SnapshotVersionBehavior {
    unique("unique"), non_unique("non-unique"), deployer("deployer");

    private String name;

    SnapshotVersionBehaviorImpl(String name) {
        this.name = name;
    }

    @Override
    @JsonValue
    public String toString() {
        return name;
    }

}
