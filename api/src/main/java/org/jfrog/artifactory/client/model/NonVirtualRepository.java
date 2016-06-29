package org.jfrog.artifactory.client.model;

import java.util.List;

/**
 * @author jbaruch
 * @since 30/07/12
 */
public interface NonVirtualRepository extends Repository {

    boolean isBlackedOut();

    List<String> getPropertySets();

    boolean isArchiveBrowsingEnabled();

}
