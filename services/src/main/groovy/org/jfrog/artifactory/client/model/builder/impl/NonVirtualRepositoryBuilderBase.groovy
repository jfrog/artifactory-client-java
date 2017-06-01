package org.jfrog.artifactory.client.model.builder.impl

import org.jfrog.artifactory.client.model.NonVirtualRepository
import org.jfrog.artifactory.client.model.PackageType
import org.jfrog.artifactory.client.model.builder.NonVirtualRepositoryBuilder

/**
 *
 * @author jbaruch
 * @since 31/07/12
 */
abstract class NonVirtualRepositoryBuilderBase<B extends NonVirtualRepositoryBuilder, R extends NonVirtualRepository> extends RepositoryBuilderBase<B, R> implements NonVirtualRepositoryBuilder<B, R> {
    protected boolean blackedOut = false
    protected List<String> propertySets = new ArrayList<String>()
    protected boolean archiveBrowsingEnabled = false

    NonVirtualRepositoryBuilderBase(Set<PackageType> supportedTypes) {
        super(supportedTypes)
    }

    @Override
    B blackedOut(boolean blackedOut) {
        this.blackedOut = blackedOut
        this as B
    }

    @Override
    boolean isBlackedOut() {
        blackedOut
    }

    @Override
    B propertySets(List<String> propertySets) {
        this.propertySets = propertySets
        this as B
    }

    @Override
    List<String> getPropertySets() {
        propertySets
    }

    B archiveBrowsingEnabled(boolean archiveBrowsingEnabled) {
        this.archiveBrowsingEnabled = archiveBrowsingEnabled
        this as B
    }

    @Override
    boolean isArchiveBrowsingEnabled() {
        archiveBrowsingEnabled
    }
}
