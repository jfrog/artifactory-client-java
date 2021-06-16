package org.jfrog.artifactory.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum AqlItemType {
    @JsonProperty("file")
    FILE,
    @JsonProperty("folder")
    FOLDER,
    @JsonProperty("any")
    ANY
}
