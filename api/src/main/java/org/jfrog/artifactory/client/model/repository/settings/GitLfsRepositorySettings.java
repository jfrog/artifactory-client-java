package org.jfrog.artifactory.client.model.repository.settings;

/**
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
public interface GitLfsRepositorySettings extends RepositorySettings {

    // ** remote ** //

    Boolean getListRemoteFolderItems();

}
