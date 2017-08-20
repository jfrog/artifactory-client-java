package org.jfrog.artifactory.client.model.builder.impl

import org.jfrog.artifactory.client.model.LocalRepository
import org.jfrog.artifactory.client.model.RepositoryType
import org.jfrog.artifactory.client.model.builder.LocalRepositoryBuilder
import org.jfrog.artifactory.client.model.impl.LocalRepositoryImpl
import org.jfrog.artifactory.client.model.impl.RepositoryTypeImpl

import static org.jfrog.artifactory.client.model.impl.PackageTypeImpl.*

/**
 *
 * @author jbaruch
 * @since 31/07/12
 */
class LocalRepositoryBuilderImpl extends NonVirtualRepositoryBuilderBase<LocalRepositoryBuilder, LocalRepository> implements LocalRepositoryBuilder {

    private LocalRepositoryBuilderImpl() {
        super([bower, cocoapods, debian, docker, gems, generic, gitlfs, gradle, ivy, maven, npm, nuget, opkg, pypi,
               sbt, vagrant, yum, rpm, composer, conan, chef, puppet] as Set)
    }

    LocalRepository build() {
        validate()
        setRepoLayout()

        return new LocalRepositoryImpl(key, settings, xraySettings, description, excludesPattern,
            includesPattern, notes, blackedOut,
            propertySets,
            repoLayoutRef,
            archiveBrowsingEnabled,
            customProperties)
    }

    @Override
    RepositoryType getRepositoryType() {
        return RepositoryTypeImpl.LOCAL
    }

}
