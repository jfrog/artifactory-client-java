package org.jfrog.artifactory.client.model.impl;

import org.jfrog.artifactory.client.model.NonVirtualRepository;
import org.jfrog.artifactory.client.model.PackageType;
import org.jfrog.artifactory.client.model.builder.NonVirtualRepositoryBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author jbaruch
 * @since 31/07/12
 */
public abstract class NonVirtualRepositoryBuilderBase<B extends NonVirtualRepositoryBuilder, R extends NonVirtualRepository> extends RepositoryBuilderBase<B, R> implements NonVirtualRepositoryBuilder<B, R> {
    protected boolean blackedOut = false;
    protected List<String> propertySets = new ArrayList<String>();
    protected boolean archiveBrowsingEnabled = false;

    protected NonVirtualRepositoryBuilderBase(Set<PackageType> supportedTypes) {
        super(supportedTypes);
    }

    public B blackedOut(boolean blackedOut) {
        this.blackedOut = blackedOut;
        return (B) this;
    }

    public boolean isBlackedOut() {
        return blackedOut;
    }

    public B propertySets(List<String> propertySets) {
        this.propertySets = propertySets;
        return (B) this;
    }

    public List<String> getPropertySets() {
        return propertySets;
    }

    public B archiveBrowsingEnabled(boolean archiveBrowsingEnabled) {
        this.archiveBrowsingEnabled = archiveBrowsingEnabled;
        return (B) this;
    }

    public boolean isArchiveBrowsingEnabled() {
        return archiveBrowsingEnabled;
    }
}
