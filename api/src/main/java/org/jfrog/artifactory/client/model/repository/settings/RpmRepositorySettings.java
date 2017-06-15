package org.jfrog.artifactory.client.model.repository.settings;

/**
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
public interface RpmRepositorySettings extends RepositorySettings {

    // ** local ** //

    Integer getYumRootDepth();

    String getGroupFileNames();

    Boolean getCalculateYumMetadata();

    Boolean getMaintainFilelistsMetadata();

    // ** remote ** //

    Boolean getListRemoteFolderItems();

}
