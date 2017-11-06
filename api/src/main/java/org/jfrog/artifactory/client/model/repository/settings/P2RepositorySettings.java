package org.jfrog.artifactory.client.model.repository.settings;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface P2RepositorySettings extends MavenRepositorySettings {

    // ** virtual ** //

    // TODO: add property support
    // Collection<P2Repo> P2Repos

}
