package org.jfrog.artifactory.client.model.repository.settings;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * @author Glen Lockhart (glen@openet.com)
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface HelmRepositorySettings extends RepositorySettings {

}
