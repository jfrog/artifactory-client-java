package org.jfrog.artifactory.client.model.impl;

import org.jfrog.artifactory.client.model.FederatedMember;
import org.jfrog.artifactory.client.model.FederatedRepository;
import org.jfrog.artifactory.client.model.PackageType;
import org.jfrog.artifactory.client.model.RepositoryType;
import org.jfrog.artifactory.client.model.builder.FederatedRepositoryBuilder;

import java.util.*;

import static org.jfrog.artifactory.client.model.impl.PackageTypeImpl.*;

/**
 * @author Siva Singaravelan
 * @since 01/07/2022
 */
public class FederatedRepositoryBuilderImpl extends NonVirtualRepositoryBuilderBase<FederatedRepositoryBuilder, FederatedRepository> implements FederatedRepositoryBuilder {
    private static Set<PackageType> federatedRepositorySupportedTypes = new HashSet<>(Arrays.asList(
            bower, cocoapods, cran, conda, debian, docker, gems, generic, gitlfs, gradle, ivy, maven, npm, nuget, opkg, pypi, sbt, vagrant, yum, rpm, composer, conan, chef, puppet, helm, helmoci, go, cargo, terraform, oci
    ));

    protected List<FederatedMember> members = new ArrayList<>();

    protected FederatedRepositoryBuilderImpl() {
        super(federatedRepositorySupportedTypes);
    }

    public FederatedRepository build() {
        validate();
        setRepoLayoutFromSettings();

        return new FederatedRepositoryImpl(key, members,settings, xraySettings, description, excludesPattern,
                includesPattern, notes, blackedOut, propertySets, repoLayoutRef,
                archiveBrowsingEnabled, customProperties);
    }

    public RepositoryType getRepositoryType() {
        return RepositoryTypeImpl.FEDERATED;
    }

    public FederatedRepositoryBuilder members(List<FederatedMember> members) {
        this.members=members;
        return this;
    }

    public List<FederatedMember> getMembers() {
        return members;
    }
}
