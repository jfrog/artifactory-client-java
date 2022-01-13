package org.jfrog.artifactory.client.model.impl;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.jfrog.artifactory.client.model.LightweightRepository;
import org.jfrog.artifactory.client.model.RepositoryType;

/**
 * @author jbaruch
 * @since 30/07/12
 */
public class LightweightRepositoryImpl implements LightweightRepository {

    private String key;
    private String description;
    private RepositoryType type;
    private String url;
    private String configuration;
    private String packageType;

    private LightweightRepositoryImpl() {
    }

    @Override
    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    @Override
    public String getDescription() {
        return description;
    }

    private void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getKey() {
        return key;
    }

    private void setKey(String key) {
        this.key = key;
    }

    @Override
    public RepositoryType getType() {
        return type;
    }

    @JsonDeserialize(as = RepositoryTypeImpl.class)
    private void setType(RepositoryType type) {
        this.type = type;
    }

    @Override
    public String getUrl() {
        return url;
    }

    private void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String getConfiguration() {
        return configuration;
    }

    private void setConfiguration(String configuration) {
        this.configuration = configuration;
    }
}
