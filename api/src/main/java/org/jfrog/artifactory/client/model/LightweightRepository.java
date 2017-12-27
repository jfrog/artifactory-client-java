package org.jfrog.artifactory.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author jbaruch
 * @since 30/07/12
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface LightweightRepository {
    String getDescription();

    String getKey();

    RepositoryType getType();

    String getUrl();

    String getConfiguration();
}
