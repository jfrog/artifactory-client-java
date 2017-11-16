
package org.jfrog.artifactory.client.model.builder;

import org.jfrog.artifactory.client.model.LocalReplication;
import org.jfrog.artifactory.client.model.Principal;

public interface LocalReplicationBuilder {

    LocalReplicationBuilder enabled(boolean enabled);

    LocalReplicationBuilder cronExp(String cronExp);

    LocalReplicationBuilder syncDeletes(boolean syncDeletes);

    LocalReplicationBuilder syncProperties(boolean syncProperties);

    LocalReplicationBuilder repoKey(String repoKey);

    LocalReplicationBuilder url(String url);

    LocalReplicationBuilder socketTimeoutMillis(long socketTimeoutMillis);

    LocalReplicationBuilder username(String username);

    LocalReplicationBuilder password(String password);

    LocalReplicationBuilder enableEventReplication(boolean enableEventReplication);

    LocalReplicationBuilder syncStatistics(boolean syncStatistics);

    LocalReplicationBuilder pathPrefix(String pathPrefix);

    LocalReplication build();
}
