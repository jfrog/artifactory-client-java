package org.jfrog.artifactory.client.impl

import org.jfrog.artifactory.client.Storage
import org.jfrog.artifactory.client.model.StorageInfo
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
    public StorageInfo getStorageInfo() {
        return artifactory.get("/api/storageinfo",StorageInfoImpl, StorageInfo)
    }
}
