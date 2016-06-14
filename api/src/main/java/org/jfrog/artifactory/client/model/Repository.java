package org.jfrog.artifactory.client.model;

import org.jfrog.artifactory.client.model.repository.settings.RepositorySettings;

/**
 * @author jbaruch
 * @since 30/07/12
 */
public interface Repository {
    String MAVEN_2_REPO_LAYOUT = "maven-2-default";

    String getKey();

    RepositoryType getRclass();

    String getDescription();

    String getNotes();

    String getIncludesPattern();

    String getExcludesPattern();

    String getRepoLayoutRef();

    RepositorySettings getRepositorySettings();

}
