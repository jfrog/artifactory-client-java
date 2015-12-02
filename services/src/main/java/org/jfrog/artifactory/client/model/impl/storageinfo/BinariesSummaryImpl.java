package org.jfrog.artifactory.client.model.impl.storageinfo;

import org.jfrog.artifactory.client.model.BinariesSummary;

/**
 * @author Aviad Shikloshi
 */
public class BinariesSummaryImpl implements BinariesSummary {

    private String binariesCount;
    private String binariesSize;
    private String artifactsSize;
    private String optimization;
    private String itemsCount;

    public String getBinariesCount() {
        return binariesCount;
    }

    public void setBinariesCount(String binariesCount) {
        this.binariesCount = binariesCount;
    }

    public String getBinariesSize() {
        return binariesSize;
    }

    public void setBinariesSize(String binariesSize) {
        this.binariesSize = binariesSize;
    }

    public String getArtifactsSize() {
        return artifactsSize;
    }

    public void setArtifactsSize(String artifactsSize) {
        this.artifactsSize = artifactsSize;
    }

    public String getOptimization() {
        return optimization;
    }

    public void setOptimization(String optimization) {
        this.optimization = optimization;
    }

    public String getItemsCount() {
        return itemsCount;
    }

    public void setItemsCount(String itemsCount) {
        this.itemsCount = itemsCount;
    }
}
