package org.jfrog.artifactory.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * A message returned from a build promotion operation
 * 
 * @author rnc
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface PromotionMessage {
    /**
     * The level of the message (error, warning, info)
     * @return the message level
     */
    String getLevel();

    /**
     * The message text
     * @return the message
     */
    String getMessage();
}

// Made with Bob
