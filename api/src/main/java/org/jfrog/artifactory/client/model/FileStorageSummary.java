package org.jfrog.artifactory.client.model;

/**
 * @author Aviad Shikloshi
 */
public interface FileStorageSummary {

    String getStorageType();

    String getStorageDirectory();

    String getTotalSpace();

    String getUsedSpace();

    String getFreeSpace();


}
