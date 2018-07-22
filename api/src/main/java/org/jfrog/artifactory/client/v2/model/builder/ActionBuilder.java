package org.jfrog.artifactory.client.v2.model.builder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.jfrog.artifactory.client.v2.model.permissions.Action;
import org.jfrog.artifactory.client.v2.model.permissions.ActionType;

/**
 * @author matank
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface ActionBuilder {

    ActionBuilder name(String name);

    ActionBuilder actions(ActionType... actions);

    Action build();
}