package org.jfrog.artifactory.client.model.impl;

import org.jfrog.artifactory.client.model.PromotionMessage;

/**
 * Implementation of PromotionMessage
 * 
 * @author rnc
 */
public class PromotionMessageImpl implements PromotionMessage {
    private String level;
    private String message;

    @Override
    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

// Made with Bob
