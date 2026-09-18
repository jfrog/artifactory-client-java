package org.jfrog.artifactory.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

/**
 * Request for promoting a build in Artifactory
 * 
 * @author rnc
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface BuildPromotionRequest {
    /**
     * The new status of the build
     * @return the status
     */
    String getStatus();

    /**
     * An optional comment describing the reason for the promotion
     * @return the comment
     */
    String getComment();

    /**
     * The user that invoked promotion from the CI server
     * @return the CI user
     */
    @JsonProperty("ciUser")
    String getCiUser();

    /**
     * The time when the promotion command was received by Artifactory (ISO8601 format)
     * @return the timestamp
     */
    String getTimestamp();

    /**
     * When set to true, performs a dry run of the promotion without executing any operation
     * @return true for dry run
     */
    @JsonProperty("dryRun")
    Boolean getDryRun();

    /**
     * The repository from which the build contents will be copied or moved
     * @return the source repository
     */
    @JsonProperty("sourceRepo")
    String getSourceRepo();

    /**
     * The target repository to which the build contents will be copied or moved
     * @return the target repository
     */
    @JsonProperty("targetRepo")
    String getTargetRepo();

    /**
     * Determines how to perform the build promotion. true = copy, false = move
     * @return true to copy, false to move
     */
    Boolean getCopy();

    /**
     * Determines whether to move/copy the build's artifacts
     * @return true to include artifacts
     */
    Boolean getArtifacts();

    /**
     * Determines whether to move/copy the build's dependencies
     * @return true to include dependencies
     */
    Boolean getDependencies();

    /**
     * An array of dependency scopes
     * @return the scopes
     */
    List<String> getScopes();

    /**
     * A list of properties to attach to the build's artifacts
     * @return the properties
     */
    Map<String, Object> getProperties();

    /**
     * When set to true, fails and aborts the promotion operation upon receiving an error
     * @return true to fail fast
     */
    @JsonProperty("failFast")
    Boolean getFailFast();
}

// Made with Bob
