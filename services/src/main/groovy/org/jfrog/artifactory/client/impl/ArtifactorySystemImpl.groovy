package org.jfrog.artifactory.client.impl

import com.fasterxml.jackson.core.type.TypeReference
import groovyx.net.http.ContentType
import org.jfrog.artifactory.client.model.SystemInfo
import org.jfrog.artifactory.client.model.impl.SystemInfoImpl

import static groovyx.net.http.ContentType.*
import org.jfrog.artifactory.client.ArtifactorySystem
import org.jfrog.artifactory.client.model.Version
import org.jfrog.artifactory.client.model.impl.VersionImpl

/**
 * User: jryan
 * Date: 7/9/13
 */
class ArtifactorySystemImpl implements ArtifactorySystem {

    private String baseApiPath;

    private ArtifactoryImpl artifactory

    ArtifactorySystemImpl(ArtifactoryImpl artifactory, String baseApiPath) {
        this.artifactory = artifactory
        this.baseApiPath = baseApiPath
    }

    @Override
    boolean ping() {
        try {
            artifactory.get(getSystemPingApi(), [:], TEXT)
            return true // No Exception thrown
        } catch(IOException ioe) {
            return false
        }
    }

    @Override
    String configuration() {
        def reader = artifactory.get(getSystemConfirmationApi(), [:], ContentType.XML, String)
        return reader
    }

    @Override
    void configuration(String xml) {
        artifactory.post(getSystemConfirmationApi(), [:], ContentType.TEXT, null, ContentType.XML, xml)
    }

    @Override
    Version version() {
        artifactory.get(getSystemVersionApi(), JSON, new TypeReference<VersionImpl>() {})
    }

    @Override
    SystemInfo info() {
        artifactory.get(getSystemInfoApi(), JSON, new TypeReference<SystemInfoImpl>() {})
    }

    private String getSystemVersionApi() {
        return getSystemApi() + "version";
    }

    private String getSystemConfirmationApi() {
        return getSystemApi() + "configuration";
    }

    private String getSystemPingApi() {
        return getSystemApi() + "ping";
    }

    private String getSystemApi() {
        return baseApiPath + "/system/";
    }

    private String getSystemInfoApi() {
        return getSystemApi() + "info";
    }
}
