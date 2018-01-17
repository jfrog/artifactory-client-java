package org.jfrog.artifactory.client.model.impl;

import org.jfrog.artifactory.client.model.Group;

/**
 * @author jbaruch
 * @since 26/11/12
 */
public class GroupImpl extends SubjectImpl implements Group {
    private boolean autoJoin;
    private boolean adminPrivileges;
    private String description;

    private GroupImpl(String name) {
        super(name);
    }

    private GroupImpl() {
        super(null);
    }

    protected GroupImpl(String name, boolean autoJoin, String description, String realm, String realmAttributes, boolean adminPrivileges) {
        super(name);
        this.autoJoin = autoJoin;
        this.description = description;
        this.adminPrivileges = adminPrivileges;
        setRealm(realm);
        setRealmAttributes(realmAttributes);
    }

    public boolean isGroup() {
        return true;
    }

    public String getDescription() {
        return description;
    }

    public boolean isAutoJoin() {
        return autoJoin;
    }

    public void setAutoJoin(boolean autoJoin) {
        this.autoJoin = autoJoin;
    }

    public  boolean isAdminPrivileges() { return adminPrivileges; }

    public void setAdminPrivileges(boolean adminPrivileges) {
        this.adminPrivileges = adminPrivileges;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
