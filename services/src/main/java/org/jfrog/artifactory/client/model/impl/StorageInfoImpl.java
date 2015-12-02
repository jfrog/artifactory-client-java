package org.jfrog.artifactory.client.model.impl;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.jfrog.artifactory.client.model.BinariesSummary;
import org.jfrog.artifactory.client.model.FileStorageSummary;
import org.jfrog.artifactory.client.model.RepositorySummary;
import org.jfrog.artifactory.client.model.Storageinfo;
import org.jfrog.artifactory.client.model.impl.storageinfo.BinariesSummaryImpl;
import org.jfrog.artifactory.client.model.impl.storageinfo.FileStorageSummaryImpl;
import org.jfrog.artifactory.client.model.impl.storageinfo.RepositorySummaryDeserialize;

import java.util.List;

/**
 * @author Aviad Shikloshi
 */
public class StorageInfoImpl implements Storageinfo {

    private BinariesSummary binariesSummary;
    private FileStorageSummary fileStorageSummary;
    private List<RepositorySummary> repositoriesSummaryList;

    public BinariesSummary getBinariesSummary() {
        return binariesSummary;
    }

    @JsonDeserialize(as = BinariesSummaryImpl.class)
    public void setBinariesSummary(BinariesSummary binariesSummary) {
        this.binariesSummary = binariesSummary;
    }

    public FileStorageSummary getFileStorageSummary() {
        return fileStorageSummary;
    }

    @JsonDeserialize(as = FileStorageSummaryImpl.class)
    public void setFileStorageSummary(FileStorageSummary fileStorageSummary) {
        this.fileStorageSummary = fileStorageSummary;
    }

    public List<RepositorySummary> getRepositoriesSummaryList() {
        return repositoriesSummaryList;
    }

    @JsonDeserialize(using= RepositorySummaryDeserialize.class)
    public void setRepositoriesSummaryList(List<RepositorySummary> repositoriesSummaryList) {
        this.repositoriesSummaryList = repositoriesSummaryList;
    }


}
