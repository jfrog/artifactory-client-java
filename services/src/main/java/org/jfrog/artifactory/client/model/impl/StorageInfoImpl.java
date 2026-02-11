package org.jfrog.artifactory.client.model.impl;

import org.jfrog.artifactory.client.model.BinariesSummary;
import org.jfrog.artifactory.client.model.FileStorageSummary;
import org.jfrog.artifactory.client.model.RepositorySummary;
import org.jfrog.artifactory.client.model.StorageInfo;
import org.jfrog.artifactory.client.model.impl.storageinfo.BinariesSummaryImpl;
import org.jfrog.artifactory.client.model.impl.storageinfo.FileStorageSummaryImpl;
import tools.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

/**
 * @author Aviad Shikloshi
 */
public class StorageInfoImpl implements StorageInfo {

    private BinariesSummary binariesSummary;
    private FileStorageSummary fileStoreSummary;
    private List<RepositorySummary> repositoriesSummaryList;

    public BinariesSummary getBinariesSummary() {
        return binariesSummary;
    }

    @com.fasterxml.jackson.databind.annotation.JsonDeserialize(as = BinariesSummaryImpl.class)
    @JsonDeserialize(as = BinariesSummaryImpl.class)
    public void setBinariesSummary(BinariesSummary binariesSummary) {
        this.binariesSummary = binariesSummary;
    }

    public FileStorageSummary getFileStoreSummary() {
        return fileStoreSummary;
    }

    @com.fasterxml.jackson.databind.annotation.JsonDeserialize(as = FileStorageSummaryImpl.class)
    @JsonDeserialize(as = FileStorageSummaryImpl.class)
    public void setFileStoreSummary(FileStorageSummary fileStoreSummary) {
        this.fileStoreSummary = fileStoreSummary;
    }

    public List<RepositorySummary> getRepositoriesSummaryList() {
        return repositoriesSummaryList;
    }

    public void setRepositoriesSummaryList(List<RepositorySummary> repositoriesSummaryList) {
        this.repositoriesSummaryList = repositoriesSummaryList;
    }


}
