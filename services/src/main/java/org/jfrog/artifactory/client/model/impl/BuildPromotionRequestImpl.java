package org.jfrog.artifactory.client.model.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jfrog.artifactory.client.model.BuildPromotionRequest;

import java.util.List;
import java.util.Map;

/**
 * Implementation of BuildPromotionRequest
 * 
 * @author rnc
 */
public class BuildPromotionRequestImpl implements BuildPromotionRequest {
    private String status;
    private String comment;
    @JsonProperty("ciUser")
    private String ciUser;
    private String timestamp;
    @JsonProperty("dryRun")
    private Boolean dryRun;
    @JsonProperty("sourceRepo")
    private String sourceRepo;
    @JsonProperty("targetRepo")
    private String targetRepo;
    private Boolean copy;
    private Boolean artifacts;
    private Boolean dependencies;
    private List<String> scopes;
    private Map<String, Object> properties;
    @JsonProperty("failFast")
    private Boolean failFast;

    @Override
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String getCiUser() {
        return ciUser;
    }

    public void setCiUser(String ciUser) {
        this.ciUser = ciUser;
    }

    @Override
    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public Boolean getDryRun() {
        return dryRun;
    }

    public void setDryRun(Boolean dryRun) {
        this.dryRun = dryRun;
    }

    @Override
    public String getSourceRepo() {
        return sourceRepo;
    }

    public void setSourceRepo(String sourceRepo) {
        this.sourceRepo = sourceRepo;
    }

    @Override
    public String getTargetRepo() {
        return targetRepo;
    }

    public void setTargetRepo(String targetRepo) {
        this.targetRepo = targetRepo;
    }

    @Override
    public Boolean getCopy() {
        return copy;
    }

    public void setCopy(Boolean copy) {
        this.copy = copy;
    }

    @Override
    public Boolean getArtifacts() {
        return artifacts;
    }

    public void setArtifacts(Boolean artifacts) {
        this.artifacts = artifacts;
    }

    @Override
    public Boolean getDependencies() {
        return dependencies;
    }

    public void setDependencies(Boolean dependencies) {
        this.dependencies = dependencies;
    }

    @Override
    public List<String> getScopes() {
        return scopes;
    }

    public void setScopes(List<String> scopes) {
        this.scopes = scopes;
    }

    @Override
    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    @Override
    public Boolean getFailFast() {
        return failFast;
    }

    public void setFailFast(Boolean failFast) {
        this.failFast = failFast;
    }
}

// Made with Bob
