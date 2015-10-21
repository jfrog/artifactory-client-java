package org.jfrog.artifactory.client.model.builder;

import org.jfrog.artifactory.client.model.Repository;

/**
 * @author jbaruch
 * @since 13/08/12
 */
public interface RepositoryBuilder<B extends RepositoryBuilder, R extends Repository> {

    B enableDebianSupport(boolean enableDebianSupport);

    B description(String description);

    B excludesPattern(String excludesPattern);

    B includesPattern(String includesPattern);

    B key(String key);

    B notes(String notes);

    B repoLayoutRef(String repoLayoutRef);

    R build();

    void validate();

    B packageType(String packageType);

    B enableNuGetSupport(boolean enableNuGetSupport);

    B enableGemsSupport(boolean enableGemsSupport);

    B enablePypiSupport(boolean enablePypiSupport);

    B enableDockerSupport(boolean enableDockerSupport);

    B enableGitLfsSupport(boolean enableGitLfsSupport);

    B enableBowerSupport(boolean enableBowerSupport);

    B enableVagrantSupport(boolean enableVagrantSupport);

    B enableNpmSupport(boolean enableNpmSupport);

    B debianTrivialLayout(boolean debianTrivialLayout);
}