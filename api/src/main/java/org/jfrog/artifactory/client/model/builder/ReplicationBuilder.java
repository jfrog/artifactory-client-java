
package org.jfrog.artifactory.client.model.builder;

import org.jfrog.artifactory.client.model.LocalReplication;

public interface ReplicationBuilder<B extends ReplicationBuilder> {

    B enabled(boolean enabled);

    B cronExp(String cronExp);

    B syncDeletes(boolean syncDeletes);

    B syncProperties(boolean syncProperties);

    B repoKey(String repoKey);
}
