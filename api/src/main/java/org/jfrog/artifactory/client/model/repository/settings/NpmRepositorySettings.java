package org.jfrog.artifactory.client.model.repository.settings;

import java.util.Collection;

/**
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
public interface NpmRepositorySettings extends RepositorySettings {

    // ** remote ** //

    Boolean getListRemoteFolderItems();

    // ** virtual ** //

    Boolean getExternalDependenciesEnabled();

    Collection<String> getExternalDependenciesPatterns();

    String getExternalDependenciesRemoteRepo();

}
