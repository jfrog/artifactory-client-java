package org.jfrog.artifactory.client.impl

import com.fasterxml.jackson.core.type.TypeReference
import groovyx.net.http.ContentType
import org.jfrog.artifactory.client.Storage
import org.jfrog.artifactory.client.model.Storageinfo
import org.jfrog.artifactory.client.model.impl.StorageInfoImpl

/**
 * Created by user on 30/11/2015.
 */
class StorageImpl implements Storage {
    private ArtifactoryImpl artifactory

    StorageImpl(ArtifactoryImpl artifactory) {
        this.artifactory = artifactory
    }

    @Override
    public Storageinfo getStorageinfo() {
        return artifactory.get("/api/storageinfo",
            ContentType.JSON, new TypeReference<StorageInfoImpl>() {})
    }
}
