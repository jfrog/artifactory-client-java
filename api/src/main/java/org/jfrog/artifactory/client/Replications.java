package org.jfrog.artifactory.client;

import org.jfrog.artifactory.client.model.LocalReplication;
import org.jfrog.artifactory.client.model.Replication;

import java.util.Collection;
import java.util.List;

public interface Replications {

    // Supports local and remote repositories
    List<Replication> list();

    // Supports local and remote repositories
    void createOrReplaceReplication(Replication replication);

    // Supports only local repositories
    void createOrReplaceReplications(Collection<LocalReplication> replications);

    void delete();

    String getReplicationsApi();

    String getReplicationsMultipleApi();
}
