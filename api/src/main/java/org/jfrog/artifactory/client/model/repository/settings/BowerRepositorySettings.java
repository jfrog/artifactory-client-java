package org.jfrog.artifactory.client.model.repository.settings;

import java.util.Collection;

/**
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
public interface BowerRepositorySettings extends VcsRepositorySettings {

    // ** remote ** //

    String getBowerRegistryUrl();

    // ** virtual ** //

    Boolean getExternalDependenciesEnabled();

    Collection<String> getExternalDependenciesPatterns();

    String getExternalDependenciesRemoteRepo();

}
