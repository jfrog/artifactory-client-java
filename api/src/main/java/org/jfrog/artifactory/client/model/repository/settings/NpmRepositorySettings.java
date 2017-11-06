package org.jfrog.artifactory.client.model.repository.settings;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Collection;

/**
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface NpmRepositorySettings extends RepositorySettings {

    // ** remote ** //

    Boolean getListRemoteFolderItems();

    // ** virtual ** //

    Boolean getExternalDependenciesEnabled();

    Collection<String> getExternalDependenciesPatterns();

    String getExternalDependenciesRemoteRepo();

}
