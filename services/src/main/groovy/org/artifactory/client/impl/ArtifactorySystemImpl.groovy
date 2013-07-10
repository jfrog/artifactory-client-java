package org.artifactory.client.impl

import com.fasterxml.jackson.core.type.TypeReference
import static groovyx.net.http.ContentType.*
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
            artifactory.get(SYSTEM_PING_API, [:], TEXT)
            return true // No Exception thrown
        } catch(IOException ioe) {
            return false
        }
    }

    @Override
    String configuration() {
        def reader = artifactory.get(SYSTEM_CONFIGURATION_API, [:], XML, TEXT)
        return reader.text
    }

    @Override
    void configuration(String xml) {
        artifactory.post(SYSTEM_CONFIGURATION_API, [:], xml, [:], String, XML)
    }

    @Override
    Version version() {
        artifactory.getJson(SYSTEM_VERSION_API, new TypeReference<VersionImpl>() {})
    }
}
