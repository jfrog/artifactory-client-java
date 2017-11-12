package org.jfrog.artifactory.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author jbaruch
 * @since 13/08/12
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface SnapshotVersionBehavior {
    @Override
    String toString();
}
