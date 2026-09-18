package org.jfrog.artifactory.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Response from promoting a build in Artifactory
 * 
 * @author rnc
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface BuildPromotionResponse {
    /**
     * Get the list of messages from the promotion operation
     * @return the messages
     */
    List<PromotionMessage> getMessages();
}

// Made with Bob
