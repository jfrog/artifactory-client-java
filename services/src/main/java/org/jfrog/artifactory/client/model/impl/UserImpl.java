package org.jfrog.artifactory.client.model.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jfrog.artifactory.client.model.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Date: 10/18/12
 * Time: 11:49 AM
 *
 * @author freds
 */
public class UserImpl extends SubjectImpl implements User {
    private String email;
    private String password;
    private boolean admin;
    private boolean profileUpdatable;
    private boolean internalPasswordDisabled;
    private Date lastLoggedIn;
    private boolean groupAdmin;
    @JsonProperty("groups")
    private Collection<String> groups = new ArrayList<>();

    private UserImpl(String name, String email, String password, boolean admin, boolean profileUpdatable, boolean internalPasswordDisabled, Date lastLoggedIn, Collection<String> groups) {
        super(name);
        this.email = email;
        this.password = password;
        this.admin = admin;
        this.profileUpdatable = profileUpdatable;
        this.internalPasswordDisabled = internalPasswordDisabled;
        this.lastLoggedIn = lastLoggedIn;
        this.groups = groups;
    }

    private UserImpl(String name) {
        super(name);
    }

    private UserImpl() {
        super(null);
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return admin;
    }

    @Override
    public boolean isGroupAdmin() {
        return groupAdmin;
    }

    public void setGroupAdmin(boolean groupAdmin) {
        this.groupAdmin = groupAdmin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isProfileUpdatable() {
        return profileUpdatable;
    }

    public void setProfileUpdatable(boolean profileUpdatable) {
        this.profileUpdatable = profileUpdatable;
    }

    public boolean isInternalPasswordDisabled() {
        return internalPasswordDisabled;
    }

    public void setInternalPasswordDisabled(boolean internalPasswordDisabled) {
        this.internalPasswordDisabled = internalPasswordDisabled;
    }

    public Date getLastLoggedIn() {
        return lastLoggedIn;
    }

    public void setLastLoggedIn(Date lastLoggedIn) {
        this.lastLoggedIn = lastLoggedIn;
    }

    @Override
    public boolean isGroup() {
        return false;
    }

    public Collection<String> getGroups() {
        return groups;
    }

    public void setGroups(Collection<String> groups) {
        this.groups = groups;
    }
}
