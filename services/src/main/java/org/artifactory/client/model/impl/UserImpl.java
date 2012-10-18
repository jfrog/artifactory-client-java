package org.artifactory.client.model.impl;

import org.artifactory.client.model.User;

import java.util.Collection;
import java.util.Date;

/**
 * Date: 10/18/12
 * Time: 11:49 AM
 *
 * @author freds
 */
public class UserImpl implements User {
    private String name;
    private String email;
    private String password;
    private boolean admin;
    private boolean profileUpdatable;
    private boolean internalPasswordDisabled;
    private Date lastLoggedIn;
    private String realm;
    private Collection<String> groups;

    public UserImpl(String name, String email, String password, boolean admin, boolean profileUpdatable, boolean internalPasswordDisabled, Date lastLoggedIn, String realm, Collection<String> groups) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.admin = admin;
        this.profileUpdatable = profileUpdatable;
        this.internalPasswordDisabled = internalPasswordDisabled;
        this.lastLoggedIn = lastLoggedIn;
        this.realm = realm;
        this.groups = groups;
    }

    public UserImpl() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    public Collection<String> getGroups() {
        return groups;
    }

    public void setGroups(Collection<String> groups) {
        this.groups = groups;
    }
}
