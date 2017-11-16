package org.jfrog.artifactory.client.model.builder.impl

import org.jfrog.artifactory.client.model.Group
import org.jfrog.artifactory.client.model.LocalReplication
import org.jfrog.artifactory.client.model.builder.GroupBuilder
import org.jfrog.artifactory.client.model.builder.LocalReplicationBuilder
import org.jfrog.artifactory.client.model.impl.GroupImpl
import org.jfrog.artifactory.client.model.impl.LocalReplicationImpl

class LocalReplicationBuilderImpl implements LocalReplicationBuilder {

    private boolean enabled;

    private String cronExp;

    private boolean syncDeletes;

    private boolean syncProperties;

    private String repoKey;

    private String url;

    private long socketTimeoutMillis;

    private String username;

    private String password;

    private boolean enableEventReplication;

    private boolean syncStatistics;

    private String pathPrefix;

    @Override
    LocalReplicationBuilder enabled(boolean enabled) {
        this.enabled = enabled;

        return this;
    }

    @Override
    LocalReplicationBuilder cronExp(String cronExp) {
        this.cronExp = cronExp;

        return this;
    }

    @Override
    LocalReplicationBuilder syncDeletes(boolean syncDeletes) {
        this.syncDeletes = syncDeletes;

        return this;
    }

    @Override
    LocalReplicationBuilder syncProperties(boolean syncProperties) {
        this.syncProperties = syncProperties;

        return this;
    }

    @Override
    LocalReplicationBuilder repoKey(String repoKey) {
        this.repoKey = repoKey;

        return this;
    }

    @Override
    LocalReplicationBuilder url(String url) {
        this.url = url;

        return this;
    }

    @Override
    LocalReplicationBuilder socketTimeoutMillis(long socketTimeoutMillis) {
        this.socketTimeoutMillis = socketTimeoutMillis;

        return this;
    }

    @Override
    LocalReplicationBuilder username(String username) {
        this.username = username;

        return this;
    }

    @Override
    LocalReplicationBuilder password(String password) {
        this.password = password;

        return this;
    }

    @Override
    LocalReplicationBuilder enableEventReplication(boolean enableEventReplication) {
        this.enableEventReplication = enableEventReplication;

        return this;
    }

    @Override
    LocalReplicationBuilder syncStatistics(boolean syncStatistics) {
        this.syncStatistics = syncStatistics;

        return this;
    }

    @Override
    LocalReplicationBuilder pathPrefix(String pathPrefix) {
        this.pathPrefix = pathPrefix;

        return this;
    }

    @Override
    LocalReplication build() {
        new LocalReplicationImpl(url, socketTimeoutMillis, username, password, enableEventReplication, syncStatistics,
                enabled, cronExp, syncDeletes, syncProperties, pathPrefix, repoKey);
    }
}
