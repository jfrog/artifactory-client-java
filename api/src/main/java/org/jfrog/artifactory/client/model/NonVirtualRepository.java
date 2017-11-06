package org.jfrog.artifactory.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 * @author jbaruch
 * @since 30/07/12
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface NonVirtualRepository extends Repository {

    boolean isBlackedOut();

    List<String> getPropertySets();

    boolean isArchiveBrowsingEnabled();

}
