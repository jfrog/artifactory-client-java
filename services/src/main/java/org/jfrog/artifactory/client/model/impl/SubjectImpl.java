package org.jfrog.artifactory.client.model.impl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.jfrog.artifactory.client.model.Subject;

/**
 * @author jbaruch
 * @since 26/11/12
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class SubjectImpl implements Subject {

    private final String name;
    private String realm;
    private String realmAttributes;

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

    public void setRealmAttributes(String realmAttributes) {
        this.realmAttributes = realmAttributes;
    }

    @Override
    public String getRealm() {
        return realm;
    }

    @Override
    public String getRealmAttributes() {
        return realmAttributes;
    }

    @Override
    public abstract boolean isGroup();
}
