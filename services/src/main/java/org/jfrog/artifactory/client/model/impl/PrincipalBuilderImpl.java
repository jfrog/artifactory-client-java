package org.jfrog.artifactory.client.model.impl;

import org.jfrog.artifactory.client.model.Principal;
import org.jfrog.artifactory.client.model.Privilege;
import org.jfrog.artifactory.client.model.builder.PrincipalBuilder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Alix Lourme
 * @since v2.1
 */
public class PrincipalBuilderImpl implements PrincipalBuilder {
    private String name;
    private Set<Privilege> privileges = new HashSet<>();

    protected PrincipalBuilderImpl() {
    }

    public PrincipalBuilderImpl name(String name) {
        this.name = name;
        return this;
    }

    public PrincipalBuilder privileges(Privilege... privileges) {
        if ( !this.privileges.isEmpty() ){
            this.privileges.clear();
        }

        this.privileges.addAll(Arrays.asList(privileges));
        return this;
    }

    public Principal build() {
        return new PrincipalImpl(name, privileges);
    }
}
