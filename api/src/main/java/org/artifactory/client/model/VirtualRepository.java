package org.artifactory.client.model;

import java.util.List;

/**
 * @author jbaruch
 * @since 30/07/12
 */
public interface VirtualRepository extends Repository {
    List<String> getRepositories();

    boolean isArtifactoryRequestsCanRetrieveRemoteArtifacts();

    String getKeyPair();

    PomRepositoryReferencesCleanupPolicy getPomRepositoryReferencesCleanupPolicy();

    public enum PomRepositoryReferencesCleanupPolicy {
        discard_active_reference, discard_any_reference, nothing
    }
}
