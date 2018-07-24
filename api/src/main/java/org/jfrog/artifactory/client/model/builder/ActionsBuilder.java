package org.jfrog.artifactory.client.model.builder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.jfrog.artifactory.client.model.Action;
import org.jfrog.artifactory.client.model.Actions;

/**
 * @author matank
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface ActionsBuilder {

    ActionsBuilder users(Action... users);

    ActionsBuilder groups(Action... groups);

    Actions build();
}