package org.artifactory.client.impl

import com.fasterxml.jackson.core.type.TypeReference
import org.apache.http.entity.ContentType
import org.artifactory.client.ArtifactorySystem
import org.artifactory.client.model.Version
import org.artifactory.client.model.impl.VersionImpl

/**
 * User: jryan
 * Date: 7/9/13
 */
class ArtifactorySystemImpl implements ArtifactorySystem {

    private ArtifactoryImpl artifactory

    ArtifactorySystemImpl(ArtifactoryImpl artifactory) {
        this.artifactory = artifactory
    }

    @Override
    boolean ping() {
        try {
            artifactory.getText(SYSTEM_PING_API)
            return true // No Exception thrown
        } catch(IOException ioe) {
            return false
        }
    }

    @Override
    String configuration() {
        artifactory.get(SYSTEM_CONFIGURATION_API, [:], ContentType.APPLICATION_XML)
    }

    @Override
    void configuration(String xml) {
        artifactory.put(SYSTEM_CONFIGURATION_API, [:], xml, String, ContentType.APPLICATION_XML)
    }

    @Override
    Version version() {
        artifactory.getJson(SYSTEM_VERSION_API, new TypeReference<VersionImpl>() {})
    }
}
