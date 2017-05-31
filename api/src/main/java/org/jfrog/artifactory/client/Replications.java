package org.jfrog.artifactory.client;

import org.jfrog.artifactory.client.model.Replication;

import java.util.List;

public interface Replications {

    List<Replication> list();

    void createOrReplaceReplication(Replication replication);

    void delete();

    String getReplicationsApi();
}
