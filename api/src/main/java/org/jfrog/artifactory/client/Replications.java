package org.jfrog.artifactory.client;

import org.jfrog.artifactory.client.model.LocalReplication;
import org.jfrog.artifactory.client.model.Replication;

import java.util.Collection;
import java.util.List;

public interface Replications {

    // Supports local and remote repositories
    void createOrReplace(Replication replication);

    void delete();

    String getReplicationsApi();
}
