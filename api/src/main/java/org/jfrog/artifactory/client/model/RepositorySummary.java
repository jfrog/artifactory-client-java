package org.jfrog.artifactory.client.model;

/**
 * @author Aviad Shikloshi
 */
public interface RepositorySummary {

    String getRepoKey();

    String getRepoType();

    Integer getFoldersCount();

    Integer getFilesCount();

    String getUsedSpace();

    Integer getItemsCount();

    String getPackageType();

    String getPercentage();

}
