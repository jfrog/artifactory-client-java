package org.jfrog.artifactory.client.model.impl;

import org.jfrog.artifactory.client.model.PackageType;
import org.jfrog.artifactory.client.model.VirtualRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jbaruch
 * @since 29/07/12
 */
public class VirtualRepositoryImpl extends RepositoryBase implements VirtualRepository {

    private List<String> repositories = new ArrayList<String>();
    private boolean artifactoryRequestsCanRetrieveRemoteArtifacts;
    private String keyPair;
    private PomRepositoryReferencesCleanupPolicy pomRepositoryReferencesCleanupPolicy = PomRepositoryReferencesCleanupPolicy.discard_active_reference;

    private VirtualRepositoryImpl() {
    }

    private VirtualRepositoryImpl(String key, PackageType packageType,
        String description, String excludesPattern, String includesPattern, String notes,
        boolean artifactoryRequestsCanRetrieveRemoteArtifacts, String keyPair,
        PomRepositoryReferencesCleanupPolicy pomRepositoryReferencesCleanupPolicy,
        List<String> repositories, String repoLayoutRef,
        boolean debianTrivialLayout) {

        super(key, packageType, description, excludesPattern, includesPattern, notes, repoLayoutRef, debianTrivialLayout);

        this.artifactoryRequestsCanRetrieveRemoteArtifacts = artifactoryRequestsCanRetrieveRemoteArtifacts;
        this.keyPair = keyPair;
        this.pomRepositoryReferencesCleanupPolicy = pomRepositoryReferencesCleanupPolicy;
        this.repositories = repositories;
    }

    @Override
    public List<String> getRepositories() {
        return repositories;
    }

    private void setRepositories(List<String> repositories) {
        this.repositories = repositories;
    }

    @Override
    public boolean isArtifactoryRequestsCanRetrieveRemoteArtifacts() {
        return artifactoryRequestsCanRetrieveRemoteArtifacts;
    }

    private void setArtifactoryRequestsCanRetrieveRemoteArtifacts(boolean artifactoryRequestsCanRetrieveRemoteArtifacts) {
        this.artifactoryRequestsCanRetrieveRemoteArtifacts = artifactoryRequestsCanRetrieveRemoteArtifacts;
    }

    @Override
    public String getKeyPair() {
        return keyPair;
    }

    private void setKeyPair(String keyPair) {
        this.keyPair = keyPair;
    }

    @Override
    public PomRepositoryReferencesCleanupPolicy getPomRepositoryReferencesCleanupPolicy() {
        return pomRepositoryReferencesCleanupPolicy;
    }

    private void setPomRepositoryReferencesCleanupPolicy(PomRepositoryReferencesCleanupPolicy pomRepositoryReferencesCleanupPolicy) {
        this.pomRepositoryReferencesCleanupPolicy = pomRepositoryReferencesCleanupPolicy;
    }

    @Override
    public RepositoryTypeImpl getRclass() {
        return RepositoryTypeImpl.VIRTUAL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        VirtualRepositoryImpl that = (VirtualRepositoryImpl) o;

        if (artifactoryRequestsCanRetrieveRemoteArtifacts != that.artifactoryRequestsCanRetrieveRemoteArtifacts)
            return false;
        if (keyPair != null ? !keyPair.equals(that.keyPair) : that.keyPair != null) return false;
        if (pomRepositoryReferencesCleanupPolicy != that.pomRepositoryReferencesCleanupPolicy) return false;
        if (repositories != null ? !repositories.equals(that.repositories) : that.repositories != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (repositories != null ? repositories.hashCode() : 0);
        result = 31 * result + (artifactoryRequestsCanRetrieveRemoteArtifacts ? 1 : 0);
        result = 31 * result + (keyPair != null ? keyPair.hashCode() : 0);
        result = 31 * result + (pomRepositoryReferencesCleanupPolicy != null ? pomRepositoryReferencesCleanupPolicy.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return super.toString() + "\nVirtualRepository{" +
                "artifactoryRequestsCanRetrieveRemoteArtifacts=" + artifactoryRequestsCanRetrieveRemoteArtifacts +
                ", repositories=" + repositories +
                ", keyPair='" + keyPair + '\'' +
                ", pomRepositoryReferencesCleanupPolicy=" + pomRepositoryReferencesCleanupPolicy +
                '}';
    }
}
