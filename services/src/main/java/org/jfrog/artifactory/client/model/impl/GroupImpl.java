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

    protected GroupImpl(String name) {
        super(name);
    }

    private GroupImpl() {
        super(null);
    }

    private GroupImpl(String name, boolean autoJoin, String description) {
        this(name, autoJoin, description, false);
    }

    private GroupImpl(String name, boolean autoJoin, String description, boolean adminPrivileges) {
        super(name);
        this.autoJoin = autoJoin;
        this.description = description;
        this.adminPrivileges = adminPrivileges;
    }

    private GroupImpl(String name, boolean autoJoin, String description, String realm, String realmAttributes) {
        this(name, autoJoin, description, realm,realmAttributes, false);
    }

    private GroupImpl(String name, boolean autoJoin, String description, String realm, String realmAttributes, boolean adminPrivileges) {
        this(name, autoJoin, description, adminPrivileges);
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

    @Override
    public  boolean isAdminPrivileges() { return adminPrivileges; }

    public void setAdminPrivileges(boolean adminPrivileges) {
        this.adminPrivileges = adminPrivileges;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
