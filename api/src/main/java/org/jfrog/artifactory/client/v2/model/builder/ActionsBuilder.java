package org.jfrog.artifactory.client.v2.model.builder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.jfrog.artifactory.client.v2.model.permissions.Action;
import org.jfrog.artifactory.client.v2.model.permissions.Actions;

/**
 * @author matank
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface ActionsBuilder {

    ActionsBuilder users(Action... users);

    ActionsBuilder groups(Action... groups);

    Actions build();
}