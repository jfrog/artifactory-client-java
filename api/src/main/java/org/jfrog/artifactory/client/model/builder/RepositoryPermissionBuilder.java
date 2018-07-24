package org.jfrog.artifactory.client.model.builder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.jfrog.artifactory.client.model.Actions;
import org.jfrog.artifactory.client.model.RepositoryPermission;

/**
 * @author matank
 * @since 21/07/2018
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface RepositoryPermissionBuilder {

    RepositoryPermissionBuilder includePatterns(String... includePatterns);

    RepositoryPermissionBuilder excludePatterns(String... excludePatterns);

    RepositoryPermissionBuilder repositories(String... repositories);

    RepositoryPermissionBuilder actions(Actions actions);

    RepositoryPermission build();
}
