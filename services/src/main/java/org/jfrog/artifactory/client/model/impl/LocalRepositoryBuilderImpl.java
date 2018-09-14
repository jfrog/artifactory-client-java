package org.jfrog.artifactory.client.model.impl;

import org.jfrog.artifactory.client.model.LocalRepository;
import org.jfrog.artifactory.client.model.PackageType;
import org.jfrog.artifactory.client.model.RepositoryType;
import org.jfrog.artifactory.client.model.builder.LocalRepositoryBuilder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.jfrog.artifactory.client.model.impl.PackageTypeImpl.*;

/**
 * @author jbaruch
 * @since 31/07/12
 */
public class LocalRepositoryBuilderImpl extends NonVirtualRepositoryBuilderBase<LocalRepositoryBuilder, LocalRepository> implements LocalRepositoryBuilder {
    private static Set<PackageType> localRepositorySupportedTypes = new HashSet<PackageType>(Arrays.asList(
            bower, cocoapods, cran, conda, debian, docker, gems, generic, gitlfs, gradle, ivy, maven, npm, nuget, opkg, pypi, sbt, vagrant, yum, rpm, composer, conan, chef, puppet
    ));

    protected LocalRepositoryBuilderImpl() {
        super(localRepositorySupportedTypes);
    }

    public LocalRepository build() {
        validate();
        setRepoLayoutFromSettings();

        return new LocalRepositoryImpl(key, settings, xraySettings, description, excludesPattern,
                includesPattern, notes, blackedOut, propertySets, repoLayoutRef,
                archiveBrowsingEnabled, customProperties);
    }

    public RepositoryType getRepositoryType() {
        return RepositoryTypeImpl.LOCAL;
    }
}
