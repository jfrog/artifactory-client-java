package org.jfrog.artifactory.client.model.builder.impl

import org.jfrog.artifactory.client.model.Principal
import org.jfrog.artifactory.client.model.Principals
import org.jfrog.artifactory.client.model.builder.PrincipalsBuilder
import org.jfrog.artifactory.client.model.impl.PrincipalsImpl

/**
 * @author Alix Lourme
 * @since > v2.1
 */
class PrincipalsBuilderImpl implements PrincipalsBuilder {

    private List<Principal> users;
    private List<Principal> groups;


    private PrincipalsBuilderImpl() {
    }

    @Override
    public PrincipalsBuilder users(Principal... users) {
        this.users = Arrays.asList(users);
        this
    }

    @Override
    public PrincipalsBuilder groups(Principal... groups) {
        this.groups = Arrays.asList(groups);
        this
    }

    Principals build() {
        PrincipalsImpl ps = new PrincipalsImpl()
        ps.setPrincipals(users, groups)
        return ps
    }
}
