package org.jfrog.artifactory.client.model.repository.settings;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.jfrog.artifactory.client.model.SnapshotVersionBehavior;
import org.jfrog.artifactory.client.model.repository.LocalRepoChecksumPolicyType;
import org.jfrog.artifactory.client.model.repository.PomCleanupPolicy;
import org.jfrog.artifactory.client.model.repository.RemoteRepoChecksumPolicyType;

/**
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface MavenRepositorySettings extends RepositorySettings {

    // ** local ** //

    Integer getMaxUniqueSnapshots();

    Boolean getHandleReleases();

    Boolean getHandleSnapshots();

    Boolean getSuppressPomConsistencyChecks();

    SnapshotVersionBehavior getSnapshotVersionBehavior();

    LocalRepoChecksumPolicyType getChecksumPolicyType();

    // ** remote ** //

    Boolean getFetchJarsEagerly();

    Boolean getFetchSourcesEagerly();

    RemoteRepoChecksumPolicyType getRemoteRepoChecksumPolicyType();

    Boolean getListRemoteFolderItems();

    Boolean getRejectInvalidJars();

    // ** virtual ** //

    PomCleanupPolicy getPomRepositoryReferencesCleanupPolicy();

    String getKeyPair();

}
