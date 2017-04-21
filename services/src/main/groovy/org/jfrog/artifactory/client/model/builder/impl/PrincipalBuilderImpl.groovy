package org.jfrog.artifactory.client.model.builder.impl

import org.jfrog.artifactory.client.model.Principal
import org.jfrog.artifactory.client.model.Privilege
import org.jfrog.artifactory.client.model.builder.PrincipalBuilder
import org.jfrog.artifactory.client.model.impl.PrincipalImpl

/**
 * @author Alix Lourme
 * @since > v2.1
 */
class PrincipalBuilderImpl implements PrincipalBuilder {

    private String name
    private Set<Privilege> privileges = new LinkedHashSet<>();

    public PrincipalBuilderImpl() {
    }

    PrincipalBuilderImpl name(String name) {
        this.name = name
        this
    }

    PrincipalBuilder privileges(Privilege... privileges) {
        this.privileges = Arrays.asList(privileges)
        this
    }

    Principal build() {
        return new PrincipalImpl(name, privileges);
    }
}