package org.jfrog.artifactory.client.model.repository.settings.impl

import groovy.transform.EqualsAndHashCode
import org.jfrog.artifactory.client.model.PackageType
import org.jfrog.artifactory.client.model.impl.LocalRepoChecksumPolicyTypeImpl
import org.jfrog.artifactory.client.model.impl.PackageTypeImpl
import org.jfrog.artifactory.client.model.impl.RemoteRepoChecksumPolicyTypeImpl
import org.jfrog.artifactory.client.model.impl.SnapshotVersionBehaviorImpl
import org.jfrog.artifactory.client.model.repository.PomCleanupPolicy
import org.jfrog.artifactory.client.model.repository.settings.AbstractRepositorySettings
import org.jfrog.artifactory.client.model.repository.settings.MavenRepositorySettings

/**
 * GroovyBean implementation of the {@link MavenRepositorySettings}
 * 
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
@EqualsAndHashCode
class MavenRepositorySettingsImpl extends AbstractRepositorySettings implements MavenRepositorySettings {
    static String defaultLayout = "maven-2-default"

    Integer maxUniqueSnapshots
    Boolean handleReleases
    Boolean handleSnapshots
    Boolean suppressPomConsistencyChecks
    SnapshotVersionBehaviorImpl snapshotVersionBehavior
    LocalRepoChecksumPolicyTypeImpl checksumPolicyType
    Boolean fetchJarsEagerly
    Boolean fetchSourcesEagerly
    RemoteRepoChecksumPolicyTypeImpl remoteRepoChecksumPolicyType
    Boolean listRemoteFolderItems
    Boolean rejectInvalidJars
    PomCleanupPolicy pomRepositoryReferencesCleanupPolicy
    String keyPair

    public MavenRepositorySettingsImpl() {
        this.repoLayoutRef = defaultLayout
    }

    @Override
    public PackageType getPackageType() {
        return PackageTypeImpl.maven
    }
}
