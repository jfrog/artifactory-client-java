package org.jfrog.artifactory.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * @author jbaruch
 * @since 26/11/12
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface PermissionTargetV1 {
    String getName();
    String getIncludesPattern();
    String getExcludesPattern();
    List<String> getRepositories();
    Principals getPrincipals();
}
