
package org.jfrog.artifactory.client.model;

import java.util.List;

/**
 * @author Alix Lourme
 * @since 2.1.1
 */
public interface Principals {

    List<Principal> getUsers();

    Principal getUser(String name);

    List<Principal> getGroups();

    Principal getGroup(String name);
}