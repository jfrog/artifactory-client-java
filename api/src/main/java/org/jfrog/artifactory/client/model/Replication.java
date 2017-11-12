package org.jfrog.artifactory.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public interface Replication {

    boolean isEnabled();

    String getCronExp();

    boolean isSyncDeletes();

    boolean isSyncProperties();

    String getRepoKey();
}
