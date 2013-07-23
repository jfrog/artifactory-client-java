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

    public GroupImpl(String name, boolean autoJoin, String description) {
        super(name);
        this.autoJoin = autoJoin;
        this.description = description;
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
