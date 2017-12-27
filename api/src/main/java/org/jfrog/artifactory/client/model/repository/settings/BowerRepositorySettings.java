package org.jfrog.artifactory.client.model.repository.settings;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Collection;

/**
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface BowerRepositorySettings extends VcsRepositorySettings {

    // ** remote ** //

    String getBowerRegistryUrl();

    // ** virtual ** //

    Boolean getExternalDependenciesEnabled();

    Collection<String> getExternalDependenciesPatterns();

    String getExternalDependenciesRemoteRepo();

}
