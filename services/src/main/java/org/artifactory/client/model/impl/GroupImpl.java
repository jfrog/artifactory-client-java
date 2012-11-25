package org.artifactory.client.model.impl;

import org.artifactory.client.model.Group;

/**
 * @author jbaruch
 * @since 26/11/12
 */
public class GroupImpl extends SubjectImpl implements Group {
    protected GroupImpl(String name) {
        super(name);
    }

    @Override
    public boolean isGroup() {
        return true;
    }
}
