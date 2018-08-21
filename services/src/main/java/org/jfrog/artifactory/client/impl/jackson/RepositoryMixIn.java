package org.jfrog.artifactory.client.impl.jackson;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.util.Map;

import org.jfrog.artifactory.client.model.Repository;
import org.jfrog.artifactory.client.model.RepositoryType;
import org.jfrog.artifactory.client.model.impl.LocalRepositoryImpl;
import org.jfrog.artifactory.client.model.impl.RemoteRepositoryImpl;
import org.jfrog.artifactory.client.model.impl.VirtualRepositoryImpl;
import org.jfrog.artifactory.client.model.repository.settings.RepositorySettings;

/**
 * special serialization / deserialization handling for {@link Repository}
 * 
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "rclass")
@JsonSubTypes({
        @JsonSubTypes.Type(value = LocalRepositoryImpl.class, name = "local"),
        @JsonSubTypes.Type(value = RemoteRepositoryImpl.class, name = "remote"),
        @JsonSubTypes.Type(value = VirtualRepositoryImpl.class, name = "virtual") })
public interface RepositoryMixIn {

    @JsonUnwrapped
    RepositorySettings getRepositorySettings();

    @JsonIgnore
    RepositoryType getRclass();

    @JsonAnyGetter
    Map<String, Object> getCustomProperties();
}
