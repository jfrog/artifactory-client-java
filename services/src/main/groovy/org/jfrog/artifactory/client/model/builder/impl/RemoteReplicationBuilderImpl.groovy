package org.jfrog.artifactory.client.model.builder.impl

import org.jfrog.artifactory.client.model.RemoteReplication
import org.jfrog.artifactory.client.model.builder.RemoteReplicationBuilder
import org.jfrog.artifactory.client.model.impl.RemoteReplicationImpl

class RemoteReplicationBuilderImpl extends ReplicationBuilderBase<RemoteReplicationBuilder> implements RemoteReplicationBuilder {

    RemoteReplicationBuilderImpl() {
    }

    RemoteReplicationBuilderImpl(RemoteReplication replication) {
        super(replication);
    }

    RemoteReplication build() {
        new RemoteReplicationImpl(enabled, cronExp, syncDeletes, syncProperties, repoKey);
    }
}
