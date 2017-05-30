package org.jfrog.artifactory.client.model.builder;

import org.jfrog.artifactory.client.model.NonVirtualRepository;

import java.util.List;

/**
 * @author jbaruch
 * @since 13/08/12
 */
public interface NonVirtualRepositoryBuilder<B extends NonVirtualRepositoryBuilder, R extends NonVirtualRepository> extends RepositoryBuilder<B, R> {

    B blackedOut(boolean blackedOut);

    boolean isBlackedOut();

    B propertySets(List<String> propertySets);

    List<String> getPropertySets();

    B archiveBrowsingEnabled(boolean archiveBrowsingEnabled);

    boolean isArchiveBrowsingEnabled();

}