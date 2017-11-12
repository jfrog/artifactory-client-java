package org.jfrog.artifactory.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 * @author Alix Lourme
 * @since 2.1.1
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface Principals {

    List<Principal> getUsers();

    Principal getUser(String name);

    List<Principal> getGroups();

    Principal getGroup(String name);
}