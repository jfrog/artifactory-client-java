package org.jfrog.artifactory.client.model;

import java.util.Map;

import org.jfrog.artifactory.client.model.repository.settings.RepositorySettings;
import org.jfrog.artifactory.client.model.repository.settings.XraySettings;

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

    XraySettings getXraySettings();

    Map getOtherProperties();
}
