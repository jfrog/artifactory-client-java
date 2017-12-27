package org.jfrog.artifactory.client.model.repository.settings;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
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
