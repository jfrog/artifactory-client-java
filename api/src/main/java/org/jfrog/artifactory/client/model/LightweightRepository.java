package org.jfrog.artifactory.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Siva Singaravelan
 * @since 01/07/2022
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface LightweightRepository {
    String getDescription();

    String getKey();

    RepositoryType getType();

    String getUrl();

    String getPackageType();

    String getConfiguration();
}
