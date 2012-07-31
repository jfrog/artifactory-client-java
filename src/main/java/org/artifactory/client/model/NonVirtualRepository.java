package org.artifactory.client.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.List;

/**
 * @author jbaruch
 * @since 30/07/12
 */
public interface NonVirtualRepository extends Repository {
    boolean isHandleReleases();

    boolean isHandleSnapshots();

    int getMaxUniqueSnapshots();

    SnapshotVersionBehavior getSnapshotVersionBehavior();

    boolean isSuppressPomConsistencyChecks();

    boolean isBlackedOut();

    List<String> getPropertySets();

    public enum SnapshotVersionBehavior {
        unique("unique"), non_unique("non-unique"), deployer("deployer");

        private String name;

        private SnapshotVersionBehavior(String name) {
            this.name = name;
        }

        @Override
        @JsonValue
        public String toString() {
            return name;
        }

    }
}
