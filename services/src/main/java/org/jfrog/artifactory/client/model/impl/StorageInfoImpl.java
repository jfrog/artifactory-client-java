package org.jfrog.artifactory.client.model.impl;

import org.jfrog.artifactory.client.model.Storageinfo;
import org.jfrog.artifactory.client.model.BinariesSummary;
import org.jfrog.artifactory.client.model.RepositorySummary;
import org.jfrog.artifactory.client.model.impl.storageinfo.FileStorageSummaryImpl;

import java.util.List;

/**
 * @author Aviad Shikloshi
 */
public class StorageInfoImpl implements Storageinfo {

    private BinariesSummary binariesSummary;
    private FileStorageSummaryImpl fileStorageSummary;
    private List<RepositorySummary> repositoriesSummaryList;

    public BinariesSummary getBinariesSummary() {
        return binariesSummary;
    }

    public void setBinariesSummary(BinariesSummary binariesSummary) {
        this.binariesSummary = binariesSummary;
    }

    public FileStorageSummaryImpl getFileStorageSummary() {
        return fileStorageSummary;
    }

    public void setFileStorageSummary(FileStorageSummaryImpl fileStorageSummary) {
        this.fileStorageSummary = fileStorageSummary;
    }

    public List<RepositorySummary> getRepositoriesSummaryList() {
        return repositoriesSummaryList;
    }

    public void setRepositoriesSummaryList(List<RepositorySummary> repositoriesSummaryList) {
        this.repositoriesSummaryList = repositoriesSummaryList;
    }
}
