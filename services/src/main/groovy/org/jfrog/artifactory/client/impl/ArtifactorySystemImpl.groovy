package org.jfrog.artifactory.client.impl

import com.fasterxml.jackson.core.type.TypeReference
import groovyx.net.http.ContentType

import static groovyx.net.http.ContentType.*
import org.jfrog.artifactory.client.ArtifactorySystem
import org.jfrog.artifactory.client.model.Version
import org.jfrog.artifactory.client.model.impl.VersionImpl

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
        def reader = artifactory.get(SYSTEM_CONFIGURATION_API, [:], ContentType.XML, String)
        return reader
    }

    @Override
    void configuration(String xml) {
        artifactory.post(SYSTEM_CONFIGURATION_API, [:], ContentType.TEXT, null, ContentType.XML, xml)
    }

    @Override
    Version version() {
        artifactory.get(SYSTEM_VERSION_API, JSON, new TypeReference<VersionImpl>() {})
    }
}
