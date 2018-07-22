package org.jfrog.artifactory.client.v2.model.permissions;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ActionType {

    READ("read"),
    ANNOTATE("annotate"),
    WRITE("write"),
    DELETE("delete"),
    MANAGE("manage"),
    BASIC_VIEW("basic-view"),
    FULL_VIEW("full-view"),
    CREATE("create"),
    PROMOTE("promote");

    private String abbreviation;

    private ActionType(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public static ActionType fromAbbreviation(String abbreviation) {
        for (ActionType action : values()) {
            if (action.abbreviation.equals(abbreviation)) {
                return action;
            }
        }
        throw new IllegalArgumentException("No Action for " + abbreviation + " found.");
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    @JsonValue
    public String toString() {
        return abbreviation;
    }
}
