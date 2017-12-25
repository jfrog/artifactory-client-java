
package org.jfrog.artifactory.client.model.builder;

import org.jfrog.artifactory.client.model.RemoteReplication;

public interface RemoteReplicationBuilder extends ReplicationBuilder<RemoteReplicationBuilder> {

    RemoteReplication build();
}
