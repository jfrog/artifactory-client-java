package org.artifactory.client.model;

import java.util.Collection;
import java.util.Date;

/**
 * Date: 10/18/12
 * Time: 9:34 AM
 *
 * @author freds
 */
public interface User {
    String getName();

    String getEmail();

    boolean isAdmin();

    boolean isUpdatableProfile();

    boolean isInternalPasswordDisabled();

    Date getLastLoggedIn();

    String getRealm();

    Collection<String> getGroups();
}
