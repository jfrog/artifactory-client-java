package org.jfrog.artifactory.client.model;

public interface LocalReplication extends Replication {

    String getUrl();

    long getSocketTimeoutMillis();

    String getUsername();

    String getPassword();

    boolean isEnableEventReplication();

    boolean isSyncStatistics();

    String getPathPrefix();
}
