package org.jfrog.artifactory.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.util.List;

/**
 * Created by Eyal BM on 30/11/2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_ARRAY, property = "storageSummary", defaultImpl = StorageInfo.class)
public interface StorageInfo {

    FileStorageSummary getFileStoreSummary();

    BinariesSummary getBinariesSummary();

    List<RepositorySummary> getRepositoriesSummaryList();

}
