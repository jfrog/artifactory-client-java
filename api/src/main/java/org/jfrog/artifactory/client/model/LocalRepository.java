package org.jfrog.artifactory.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author jbaruch
 * @since 30/07/12
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface LocalRepository extends Repository, NonVirtualRepository {

}
