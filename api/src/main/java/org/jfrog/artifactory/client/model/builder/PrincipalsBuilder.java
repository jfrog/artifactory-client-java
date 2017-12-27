package org.jfrog.artifactory.client.model.builder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.jfrog.artifactory.client.model.Principal;
import org.jfrog.artifactory.client.model.Principals;

/**
 * @author Alix Lourme
 * @since 2.1.1
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface PrincipalsBuilder {

    PrincipalsBuilder users(Principal... users);

    PrincipalsBuilder groups(Principal... groups);

    Principals build();
}