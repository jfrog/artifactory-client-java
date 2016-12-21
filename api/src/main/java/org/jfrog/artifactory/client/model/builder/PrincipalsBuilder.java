package org.jfrog.artifactory.client.model.builder;

import org.jfrog.artifactory.client.model.Principal;
import org.jfrog.artifactory.client.model.Principals;

/**
 * @author Alix Lourme
 * @since 2.1.1
 */
public interface PrincipalsBuilder {

    PrincipalsBuilder users(Principal... users);

    PrincipalsBuilder groups(Principal... groups);

    Principals build();
}