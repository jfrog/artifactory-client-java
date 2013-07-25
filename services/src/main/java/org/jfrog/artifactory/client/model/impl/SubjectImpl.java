package org.jfrog.artifactory.client.model.impl;

import org.jfrog.artifactory.client.model.Subject;

/**
 * @author jbaruch
 * @since 26/11/12
 */
public abstract class SubjectImpl implements Subject {

    private final String name;
    private String realm;

    protected SubjectImpl(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    @Override
    public String getRealm() {
        return realm;
    }

    @Override
    public abstract boolean isGroup();
}
