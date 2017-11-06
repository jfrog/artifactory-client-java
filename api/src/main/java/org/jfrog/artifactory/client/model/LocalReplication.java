package org.jfrog.artifactory.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public interface LocalReplication extends Replication {

    String getUrl();

    long getSocketTimeoutMillis();

    String getUsername();

    String getPassword();

    boolean isEnableEventReplication();

    boolean isSyncStatistics();

    String getPathPrefix();
}
