package org.jfrog.artifactory.client.model.repository.settings;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface RpmRepositorySettings extends RepositorySettings {

    // ** local ** //

    Integer getYumRootDepth();

    String getGroupFileNames();

    Boolean getCalculateYumMetadata();

    Boolean getEnableFileListsIndexing();

    // ** remote ** //

    Boolean getListRemoteFolderItems();

}
