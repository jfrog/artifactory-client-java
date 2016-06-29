package org.jfrog.artifactory.client.model.repository.settings;

/**
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
public interface NugetRepositorySettings extends RepositorySettings {

    // ** local ** //

    Integer getMaxUniqueSnapshots();

    // ** remote ** //

    String getFeedContextPath();

    String getDownloadContextPath();
    
    Boolean getListRemoteFolderItems();

    // ** local + remote + virtual ** //

    Boolean getForceNugetAuthentication();

}
