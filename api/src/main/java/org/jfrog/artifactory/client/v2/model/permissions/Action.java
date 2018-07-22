package org.jfrog.artifactory.client.v2.model.permissions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Set;

/**
 * @author jbaruch
 * @since 22/11/12
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface Action {

    String getName();

    Set<ActionType> getActionTypes();

    boolean isAllowedTo(ActionType... actions);
}
