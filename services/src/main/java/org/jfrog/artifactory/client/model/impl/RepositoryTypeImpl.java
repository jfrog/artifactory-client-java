package org.jfrog.artifactory.client.model.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.type.TypeReference;
import org.jfrog.artifactory.client.model.RepositoryType;

import java.util.List;

/**
 * @author jbaruch
 * @since 30/07/12
 */
public enum RepositoryTypeImpl implements RepositoryType {
    LOCAL(LocalRepositoryImpl.class, new TypeReference<List<LocalRepositoryImpl>>() {
    }),
    REMOTE(RemoteRepositoryImpl.class, new TypeReference<List<RemoteRepositoryImpl>>() {
    }),
    VIRTUAL(VirtualRepositoryImpl.class, new TypeReference<List<VirtualRepositoryImpl>>() {
    });

    RepositoryTypeImpl(Class<? extends RepositoryBase> typeClass, TypeReference typeReference) {
        this.typeClass = typeClass;
        this.typeReference = typeReference;
    }

    private final TypeReference typeReference;
    private final Class<? extends RepositoryBase> typeClass;

    public Class<? extends RepositoryBase> getTypeClass() {
        return typeClass;
    }

    private TypeReference getTypeReference() {
        return typeReference;
    }

    @JsonCreator // used by LightweightRepository
    public static RepositoryTypeImpl parseString(String typeName) {
        return valueOf(typeName.toUpperCase());
    }

    @Override
    @JsonValue
    public String toString() {
        return super.toString().toLowerCase();
    }
}
