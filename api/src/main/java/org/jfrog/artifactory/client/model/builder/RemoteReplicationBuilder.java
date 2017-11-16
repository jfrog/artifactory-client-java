
package org.jfrog.artifactory.client.model.builder;

import org.jfrog.artifactory.client.model.RemoteReplication;

public interface RemoteReplicationBuilder {

    RemoteReplicationBuilder enabled(boolean enabled);

    RemoteReplicationBuilder cronExp(String cronExp);

    RemoteReplicationBuilder syncDeletes(boolean syncDeletes);

    RemoteReplicationBuilder syncProperties(boolean syncProperties);

    RemoteReplicationBuilder repoKey(String repoKey);

    RemoteReplication build();
}
