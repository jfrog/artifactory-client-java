package org.jfrog.artifactory.client.model.builder;

import org.jfrog.artifactory.client.model.Repository;
import org.jfrog.artifactory.client.model.repository.settings.RepositorySettings;
import org.jfrog.artifactory.client.model.repository.settings.XraySettings;

/**
 * @author jbaruch
 * @since 13/08/12
 */
public interface RepositoryBuilder<B extends RepositoryBuilder, R extends Repository> {

    B description(String description);

    B excludesPattern(String excludesPattern);

    B includesPattern(String includesPattern);

    B key(String key);

    B notes(String notes);

    B repoLayoutRef(String repoLayoutRef);

    R build();

    void validate();

    B repositorySettings(RepositorySettings settings);

    B xraySettings(XraySettings xraySettings);

}
