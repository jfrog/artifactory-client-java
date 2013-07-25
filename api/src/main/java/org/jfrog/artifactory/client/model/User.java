package org.jfrog.artifactory.client.model;

import java.util.Collection;
import java.util.Date;

/**
 * Date: 10/18/12
 * Time: 9:34 AM
 *
 * @author freds
 */
public interface User extends Subject {

    String getEmail();

    boolean isAdmin();

    boolean isProfileUpdatable();

    boolean isInternalPasswordDisabled();

    Date getLastLoggedIn();

    Collection<String> getGroups();
}
