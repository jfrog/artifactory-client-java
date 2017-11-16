package org.jfrog.artifactory.client.model.builder.impl

import org.jfrog.artifactory.client.model.LocalReplication
import org.jfrog.artifactory.client.model.RemoteReplication
import org.jfrog.artifactory.client.model.builder.LocalReplicationBuilder
import org.jfrog.artifactory.client.model.builder.RemoteReplicationBuilder
import org.jfrog.artifactory.client.model.impl.LocalReplicationImpl
import org.jfrog.artifactory.client.model.impl.RemoteReplicationImpl

class RemoteReplicationBuilderImpl implements RemoteReplicationBuilder {

    private boolean enabled;

    private String cronExp;

    private boolean syncDeletes;

    private boolean syncProperties;

    private String repoKey;

    @Override
    RemoteReplicationBuilder enabled(boolean enabled) {
        this.enabled = enabled;

        return this;
    }

    @Override
    RemoteReplicationBuilder cronExp(String cronExp) {
        this.cronExp = cronExp;

        return this;
    }

    @Override
    RemoteReplicationBuilder syncDeletes(boolean syncDeletes) {
        this.syncDeletes = syncDeletes;

        return this;
    }

    @Override
    RemoteReplicationBuilder syncProperties(boolean syncProperties) {
        this.syncProperties = syncProperties;

        return this;
    }

    @Override
    RemoteReplicationBuilder repoKey(String repoKey) {
        this.repoKey = repoKey;

        return this;
    }

    RemoteReplication build() {
        new RemoteReplicationImpl(enabled, cronExp, syncDeletes, syncProperties, repoKey);
    }
}
