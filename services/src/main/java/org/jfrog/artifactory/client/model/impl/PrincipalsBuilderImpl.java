package org.jfrog.artifactory.client.model.impl;

import org.jfrog.artifactory.client.model.Principal;
import org.jfrog.artifactory.client.model.Principals;
import org.jfrog.artifactory.client.model.builder.PrincipalsBuilder;

import java.util.Arrays;
import java.util.List;

/**
 * @author Alix Lourme
 * @since v2.1
 */
public class PrincipalsBuilderImpl implements PrincipalsBuilder {
    private List<Principal> users;
    private List<Principal> groups;

    protected PrincipalsBuilderImpl() {
    }

    public PrincipalsBuilder users(Principal... users) {
        this.users = Arrays.asList(users);
        return this;
    }

    public PrincipalsBuilder groups(Principal... groups) {
        this.groups = Arrays.asList(groups);
        return this;
    }

    public Principals build() {
        PrincipalsImpl ps = new PrincipalsImpl();
        ps.setPrincipals(users, groups);
        return ps;
    }
}
