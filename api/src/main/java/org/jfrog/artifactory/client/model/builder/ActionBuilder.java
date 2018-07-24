package org.jfrog.artifactory.client.model.builder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.jfrog.artifactory.client.model.Action;
import org.jfrog.artifactory.client.model.ActionType;

/**
 * @author matank
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface ActionBuilder {

    ActionBuilder name(String name);

    ActionBuilder actions(ActionType... actions);

    Action build();
}