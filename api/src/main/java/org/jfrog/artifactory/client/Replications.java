package org.jfrog.artifactory.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.jfrog.artifactory.client.model.Replication;

@JsonIgnoreProperties(ignoreUnknown = true)
public interface Replications {

    // Supports local and remote repositories
    void createOrReplace(Replication replication);

    void delete();

    String getReplicationsApi();
}
