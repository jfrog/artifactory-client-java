package org.jfrog.artifactory.client.v2.model.permissions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * @author Alix Lourme
 * @since 2.1.1
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface Actions {

    List<Action> getUsers();

    Action getUser(String name);

    List<Action> getGroups();

    Action getGroup(String name);
}