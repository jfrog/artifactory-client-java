package org.jfrog.artifactory.client.model.builder;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.jfrog.artifactory.client.model.Repository;
import org.jfrog.artifactory.client.model.repository.settings.RepositorySettings;
import org.jfrog.artifactory.client.model.repository.settings.XraySettings;

/**
 * @author jbaruch
 * @since 13/08/12
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface RepositoryBuilder<B extends RepositoryBuilder, R extends Repository> {

    B description(String description);

    String getDescription();

    B excludesPattern(String excludesPattern);

    String getExcludesPattern();

    B includesPattern(String includesPattern);

    String getIncludesPattern();

    B key(String key);

    String getKey();

    B notes(String notes);

    String getNotes();

    R build();

    void validate();

    B repoLayoutRef(String repoLayoutRef);

    void setRepoLayoutFromSettings();

    B repositorySettings(RepositorySettings settings);

    RepositorySettings getRepositorySettings();

    B xraySettings(XraySettings xraySettings);

    XraySettings getXraySettings();

    B customProperties(Map<String, Object> customProperties);
}
