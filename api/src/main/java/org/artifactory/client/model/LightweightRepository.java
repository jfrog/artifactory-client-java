package org.artifactory.client.model;

/**
 * @author jbaruch
 * @since 30/07/12
 */
public interface LightweightRepository {
    String getDescription();

    String getKey();

    RepositoryType getType();

    String getUrl();

    String getConfiguration();
}
