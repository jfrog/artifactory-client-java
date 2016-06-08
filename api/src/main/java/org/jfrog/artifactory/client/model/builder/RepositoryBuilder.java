package org.jfrog.artifactory.client.model.builder;

import org.jfrog.artifactory.client.model.PackageType;
import org.jfrog.artifactory.client.model.Repository;

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

    B packageType(PackageType packageType);

    B debianTrivialLayout(boolean debianTrivialLayout);
}