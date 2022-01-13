package org.jfrog.artifactory.client.impl.jackson;

import com.fasterxml.jackson.annotation.*;
import org.jfrog.artifactory.client.model.Repository;
import org.jfrog.artifactory.client.model.RepositoryType;
import org.jfrog.artifactory.client.model.impl.FederatedRepositoryImpl;
import org.jfrog.artifactory.client.model.impl.LocalRepositoryImpl;
import org.jfrog.artifactory.client.model.impl.RemoteRepositoryImpl;
import org.jfrog.artifactory.client.model.impl.VirtualRepositoryImpl;
import org.jfrog.artifactory.client.model.repository.settings.RepositorySettings;

import java.util.Map;

/**
 * special serialization / deserialization handling for {@link Repository}
 * 
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "rclass")
@JsonSubTypes({
        @JsonSubTypes.Type(value = LocalRepositoryImpl.class, name = "local"),
        @JsonSubTypes.Type(value = FederatedRepositoryImpl.class, name = "federated"),
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
