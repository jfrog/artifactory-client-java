package org.jfrog.artifactory.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Jeka on 05/11/13.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CopyMoveResultMessage {
    private String message;
    private String level;

    public String getMessage() {
        return message;
    }

    public String getLevel() {
        return level;
    }
}
