package org.jfrog.artifactory.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/**
 * @author Aviad Shikloshi
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface FileStorageSummary {

    String getStorageType();

    String getStorageDirectory();

    String getTotalSpace();

    String getUsedSpace();

    String getFreeSpace();


}
