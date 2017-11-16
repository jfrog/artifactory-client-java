package org.jfrog.artifactory.client.model.builder.impl

import org.jfrog.artifactory.client.model.LocalReplication
import org.jfrog.artifactory.client.model.builder.LocalReplicationBuilder
import org.jfrog.artifactory.client.model.impl.LocalReplicationImpl

class LocalReplicationBuilderImpl extends ReplicationBuilderBase<LocalReplicationBuilder> {

    private String url;

    private long socketTimeoutMillis;

    private String username;

    private String password;

    private boolean enableEventReplication;

    private boolean syncStatistics;

    private String pathPrefix;

    LocalReplicationBuilder url(String url) {
        this.url = url;

        return this;
    }

    LocalReplicationBuilder socketTimeoutMillis(long socketTimeoutMillis) {
        this.socketTimeoutMillis = socketTimeoutMillis;

        return this;
    }

    LocalReplicationBuilder username(String username) {
        this.username = username;

        return this;
    }

    LocalReplicationBuilder password(String password) {
        this.password = password;

        return this;
    }

    LocalReplicationBuilder enableEventReplication(boolean enableEventReplication) {
        this.enableEventReplication = enableEventReplication;

        return this;
    }

    LocalReplicationBuilder syncStatistics(boolean syncStatistics) {
        this.syncStatistics = syncStatistics;

        return this;
    }

    LocalReplicationBuilder pathPrefix(String pathPrefix) {
        this.pathPrefix = pathPrefix;

        return this;
    }

    LocalReplication build() {
        new LocalReplicationImpl(url, socketTimeoutMillis, username, password, enableEventReplication, syncStatistics,
                enabled, cronExp, syncDeletes, syncProperties, pathPrefix, repoKey);
    }
}
