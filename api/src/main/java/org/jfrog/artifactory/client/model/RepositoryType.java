package org.jfrog.artifactory.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author jbaruch
 * @since 13/08/12
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface RepositoryType {

    public Class<? extends Repository> getTypeClass();

    @Override
    public String toString();
}