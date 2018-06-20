package org.jfrog.artifactory.client.impl

import org.apache.http.entity.ContentType
import org.jfrog.artifactory.client.ArtifactorySystem
import org.jfrog.artifactory.client.model.SystemInfo
import org.jfrog.artifactory.client.model.Version
import org.jfrog.artifactory.client.model.impl.SystemInfoImpl
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
            artifactory.get(getSystemPingApi(), String, null);
            return true // No Exception thrown
        } catch (IOException ioe) {
            return false
        }
    }

    @Override
    String configuration() {
        return artifactory.get(getSystemConfigurationApi(), String, null);
    }

    @Override
    void configuration(String xml) {
        artifactory.post(getSystemConfigurationApi(), ContentType.APPLICATION_XML, xml, null, String, null)
    }

    @Override
    void patchConfiguration(String yml) {
        ContentType mimeType = ContentType.create("application/x-yaml");
        artifactory.patch(getSystemConfigurationApi(), mimeType, yml, null, String, null)
    }

    @Override
    Version version() {
        return artifactory.get(getSystemVersionApi(), VersionImpl, Version);
    }

    @Override
    SystemInfo info() {
        return artifactory.get(getSystemInfoApi(), SystemInfoImpl, SystemInfo);
    }

    private String getSystemVersionApi() {
        return getSystemApi() + "version";
    }

    private String getSystemConfigurationApi() {
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
