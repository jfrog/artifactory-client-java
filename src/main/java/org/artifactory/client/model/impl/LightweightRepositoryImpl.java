package org.artifactory.client.model.impl;

import org.artifactory.client.model.LightweightRepository;
import org.artifactory.client.model.RepositoryType;

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

    private LightweightRepositoryImpl() {
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
