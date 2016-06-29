package org.jfrog.artifactory.client.model.repository.settings;

/**
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
public interface DebianRepositorySettings extends RepositorySettings {

    // ** local ** //

    Boolean getDebianTrivialLayout();

    // ** remote ** //

    Boolean getListRemoteFolderItems();

}
