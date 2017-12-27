package org.jfrog.artifactory.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Aviad Shikloshi
 */
@JsonIgnoreProperties(ignoreUnknown = true)
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
