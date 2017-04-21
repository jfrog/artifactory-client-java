package org.jfrog.artifactory.client.model.impl.storageinfo;


import org.jfrog.artifactory.client.model.FileStorageSummary;

/**
 * @author Aviad Shikloshi
 */
public class FileStorageSummaryImpl implements FileStorageSummary {

    private String storageType;
    private String storageDirectory;
    private String totalSpace;
    private String usedSpace;
    private String freeSpace;

    public String getStorageType() {
        return storageType;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType;
    }

    public String getStorageDirectory() {
        return storageDirectory;
    }

    public void setStorageDirectory(String storageDirectory) {
        this.storageDirectory = storageDirectory;
    }

    public String getTotalSpace() {
        return totalSpace;
    }

    public void setTotalSpace(String totalSpace) {
        this.totalSpace = totalSpace;
    }

    public String getUsedSpace() {
        return usedSpace;
    }

    public void setUsedSpace(String usedSpace) {
        this.usedSpace = usedSpace;
    }

    public String getFreeSpace() {
        return freeSpace;
    }

    public void setFreeSpace(String freeSpace) {
        this.freeSpace = freeSpace;
    }
}
