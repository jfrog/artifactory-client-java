package org.jfrog.artifactory.client.model.builder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.jfrog.artifactory.client.model.Principal;
import org.jfrog.artifactory.client.model.Privilege;

/**
 * @author Alix Lourme
 * @since 2.1.1
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface PrincipalBuilder {

    PrincipalBuilder name(String name);

    PrincipalBuilder privileges(Privilege... privileges);

    Principal build();
}