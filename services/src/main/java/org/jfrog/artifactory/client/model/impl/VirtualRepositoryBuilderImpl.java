package org.jfrog.artifactory.client.model.impl;

import org.jfrog.artifactory.client.model.PackageType;
import org.jfrog.artifactory.client.model.RepositoryType;
import org.jfrog.artifactory.client.model.VirtualRepository;
import org.jfrog.artifactory.client.model.builder.VirtualRepositoryBuilder;

import java.util.*;

import static org.jfrog.artifactory.client.model.impl.PackageTypeImpl.*;

/**
 * @author jbaruch
 * @since 31/07/12
 */
public class VirtualRepositoryBuilderImpl extends RepositoryBuilderBase<VirtualRepositoryBuilder, VirtualRepository> implements VirtualRepositoryBuilder {
    private static Set<PackageType> virtualRepositorySupportedTypes = new HashSet<PackageType>(Arrays.asList(
            bower, cran, conda, docker, debian, gems, generic, gitlfs, gradle, ivy, maven, npm, nuget, p2, pypi, sbt, yum, rpm, composer, conan, chef, puppet
    ));

    private Collection<String> repositories = Collections.emptyList();
    private boolean artifactoryRequestsCanRetrieveRemoteArtifacts;
    private String defaultDeploymentRepo;

    protected VirtualRepositoryBuilderImpl() {
        super(virtualRepositorySupportedTypes);
    }

    public VirtualRepositoryBuilder repositories(Collection<String> repositories) {
        this.repositories = repositories;
        return this;
    }

    public Collection<String> getRepositories() {
        return repositories;
    }

    public VirtualRepositoryBuilder artifactoryRequestsCanRetrieveRemoteArtifacts(boolean artifactoryRequestsCanRetrieveRemoteArtifacts) {
        this.artifactoryRequestsCanRetrieveRemoteArtifacts = artifactoryRequestsCanRetrieveRemoteArtifacts;
        return this;
    }

    public boolean isArtifactoryRequestsCanRetrieveRemoteArtifacts() {
        return artifactoryRequestsCanRetrieveRemoteArtifacts;
    }

    public VirtualRepositoryBuilder defaultDeploymentRepo(String deploymentRepo) {
        this.defaultDeploymentRepo = deploymentRepo;
        return this;
    }

    public String getDefaultDeploymentRepo() {
        return defaultDeploymentRepo;
    }

    public VirtualRepository build() {
        validate();
        setRepoLayoutFromSettings();

        return new VirtualRepositoryImpl(key, settings, description, excludesPattern,
                includesPattern, notes, artifactoryRequestsCanRetrieveRemoteArtifacts,
                repositories, repoLayoutRef, defaultDeploymentRepo, customProperties);
    }

    public RepositoryType getRepositoryType() {
        return RepositoryTypeImpl.VIRTUAL;
    }
}
