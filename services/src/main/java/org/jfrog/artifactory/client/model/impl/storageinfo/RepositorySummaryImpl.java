package org.jfrog.artifactory.client.model.impl.storageinfo;

import org.jfrog.artifactory.client.model.RepositorySummary;

/**
 * @author Aviad Shikloshi
 */
public class RepositorySummaryImpl implements RepositorySummary {

    private String repoKey;
    private String repoType;
    private Integer foldersCount;
    private Integer filesCount;
    private String usedSpace;
    private Integer itemsCount;
    private String packageType;
    private String percentage;

    public String getRepoKey() {
        return repoKey;
    }

    public void setRepoKey(String repoKey) {
        this.repoKey = repoKey;
    }

    public String getRepoType() {
        return repoType;
    }

    public void setRepoType(String repoType) {
        this.repoType = repoType;
    }

    public Integer getFoldersCount() {
        return foldersCount;
    }

    public void setFoldersCount(Integer foldersCount) {
        this.foldersCount = foldersCount;
    }

    public Integer getFilesCount() {
        return filesCount;
    }

    public void setFilesCount(Integer filesCount) {
        this.filesCount = filesCount;
    }

    public String getUsedSpace() {
        return usedSpace;
    }

    public void setUsedSpace(String usedSpace) {
        this.usedSpace = usedSpace;
    }

    public Integer getItemsCount() {
        return itemsCount;
    }

    public void setItemsCount(Integer itemsCount) {
        this.itemsCount = itemsCount;
    }

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }
}
