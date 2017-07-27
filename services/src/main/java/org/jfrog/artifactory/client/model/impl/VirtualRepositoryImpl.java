package org.jfrog.artifactory.client.model.impl;

import org.jfrog.artifactory.client.model.VirtualRepository;
import org.jfrog.artifactory.client.model.repository.settings.RepositorySettings;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * @author jbaruch
 * @since 29/07/12
 */
public class VirtualRepositoryImpl extends RepositoryBase implements VirtualRepository {

    private Collection<String> repositories = new ArrayList<String>();
    private boolean artifactoryRequestsCanRetrieveRemoteArtifacts;
    private String deploymentRepo;

    private VirtualRepositoryImpl() {
    }

    private VirtualRepositoryImpl(String key, RepositorySettings settings,
        String description, String excludesPattern, String includesPattern, String notes,
        boolean artifactoryRequestsCanRetrieveRemoteArtifacts, Collection<String> repositories,
        String repoLayoutRef, String deploymentRepo, Map<String, String> customProperties) {

        super(key, settings, description, excludesPattern, includesPattern, notes, repoLayoutRef, customProperties);

        this.artifactoryRequestsCanRetrieveRemoteArtifacts = artifactoryRequestsCanRetrieveRemoteArtifacts;
        this.repositories = repositories;
        this.deploymentRepo = deploymentRepo;
    }

    @Override
    public Collection<String> getRepositories() {
        return repositories;
    }

    private void setRepositories(Collection<String> repositories) {
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
    public String getDefaultDeploymentRepo() {
        return deploymentRepo;
    }

    private void setDefaultDeploymentRepo(String deploymentRepo) {
        this.deploymentRepo = deploymentRepo;
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
        if (repositories != null ? !repositories.equals(that.repositories) : that.repositories != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (repositories != null ? repositories.hashCode() : 0);
        result = 31 * result + (artifactoryRequestsCanRetrieveRemoteArtifacts ? 1 : 0);

        return result;
    }

    @Override
    public String toString() {
        return super.toString() + "\nVirtualRepository{" +
                "artifactoryRequestsCanRetrieveRemoteArtifacts=" + artifactoryRequestsCanRetrieveRemoteArtifacts +
                ", repositories=" + repositories +
                '}';
    }
}
