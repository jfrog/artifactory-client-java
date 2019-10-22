package org.jfrog.artifactory.client.model.repository.settings;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author David Csakvari
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface GoRepositorySettings extends RepositorySettings {

	Boolean getExternalDependenciesEnabled();

	Collection<String> getExternalDependenciesPatterns();

}
