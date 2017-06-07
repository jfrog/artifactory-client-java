package org.jfrog.artifactory.client.model;

public interface Replication {

    boolean isEnabled();

    String getCronExp();

    boolean isSyncDeletes();

    boolean isSyncProperties();

    String getRepoKey();
}
