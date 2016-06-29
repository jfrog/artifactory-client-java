package org.jfrog.artifactory.client.model.repository.settings;

/**
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
public interface YumRepositorySettings extends RepositorySettings {

    // ** local ** //

    Integer getYumRootDepth();

    String getGroupFileNames();

    Boolean getCalculateYumMetadata();

    // ** remote ** //

    Boolean getListRemoteFolderItems();

}
