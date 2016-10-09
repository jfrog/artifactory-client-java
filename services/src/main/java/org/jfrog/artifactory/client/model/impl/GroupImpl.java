package org.jfrog.artifactory.client.model.impl;

import org.jfrog.artifactory.client.model.Group;

/**
 * @author jbaruch
 * @since 26/11/12
 */
public class GroupImpl extends SubjectImpl implements Group {

    private boolean autoJoin;
    private String description;

    protected GroupImpl(String name) {
        super(name);
    }

    private GroupImpl() {
        super(null);
    }

    private GroupImpl(String name, boolean autoJoin, String description) {
        super(name);
        this.autoJoin = autoJoin;
        this.description = description;
    }

    private GroupImpl(String name, boolean autoJoin, String description, String realm, String realmAttributes) {
        this(name, autoJoin, description);
        setRealm(realm);
        setRealmAttributes(realmAttributes);
    }

    @Override
    public boolean isGroup() {
        return true;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public boolean isAutoJoin() {
        return autoJoin;
    }

    public void setAutoJoin(boolean autoJoin) {
        this.autoJoin = autoJoin;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
