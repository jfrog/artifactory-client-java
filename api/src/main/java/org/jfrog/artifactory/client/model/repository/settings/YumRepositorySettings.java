package org.jfrog.artifactory.client.model.repository.settings;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 * @deprecated since Artifactory 5.0.0. Replaced by {@link RpmRepositorySettings}.
 */
@Deprecated
@JsonIgnoreProperties(ignoreUnknown = true)
public interface YumRepositorySettings extends RepositorySettings {

    // ** local ** //

    Integer getYumRootDepth();

    String getGroupFileNames();

    Boolean getCalculateYumMetadata();

    // ** remote ** //

    Boolean getListRemoteFolderItems();

}
