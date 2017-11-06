package org.jfrog.artifactory.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Collection;
import java.util.Date;

/**
 * Date: 10/18/12
 * Time: 9:34 AM
 *
 * @author freds
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface User extends Subject {

    String getEmail();

    boolean isAdmin();

    boolean isGroupAdmin();

    boolean isProfileUpdatable();

    boolean isInternalPasswordDisabled();

    Date getLastLoggedIn();

    Collection<String> getGroups();
}
