package org.artifactory.client.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

/**
 * @author jbaruch
 * @since 30/07/12
 */
public enum RepositoryType {
    LOCAL(LocalRepositoryImpl.class, new TypeReference<List<LocalRepositoryImpl>>() {
    }),
    REMOTE(RemoteRepositoryImpl.class, new TypeReference<List<RemoteRepositoryImpl>>() {
    }),
    VIRTUAL(VirtualRepositoryImpl.class, new TypeReference<List<VirtualRepositoryImpl>>() {
    });

    RepositoryType(Class<? extends RepositoryBase> typeClass, TypeReference typeReference) {
        this.typeClass = typeClass;
        this.typeReference = typeReference;
    }

    private final TypeReference typeReference;
    private final Class<? extends RepositoryBase> typeClass;

    public Class<? extends RepositoryBase> getTypeClass() {
        return typeClass;
    }

    //TODO: [by yl] Remove
    public TypeReference getTypeReference() {
        return typeReference;
    }

    @JsonCreator
    public static RepositoryType parseString(String typeName) {
        return valueOf(typeName.toUpperCase());
    }


    @Override
    @JsonValue
    public String toString() {
        return super.toString().toLowerCase();
    }
}
