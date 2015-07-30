package org.jfrog.artifactory.client.impl

import groovyx.net.http.ContentType;
import org.jfrog.artifactory.client.model.MissionControl;

/**
 * Created by user on 30/07/2015.
 */
public class MissionControlImpl implements MissionControl {

    private ArtifactoryImpl artifactory

    MissionControlImpl(ArtifactoryImpl artifactory) {
        this.artifactory = artifactory
    }

    @Override
    public void createConnection(String token) {
        createOrUpdateConnection(null, token)
    }

    @Override
    public void updateConnection(String oldToken, String newToken) {
        createOrUpdateConnection(oldToken, newToken)
    }

    private void createOrUpdateConnection(String oldToken, String newToken) {
        Map headers = [:]
        if (oldToken) {
            headers = ['Authorization-token':oldToken]
        }

        String url = "${artifactory.getUri()}/${artifactory.getContextName()}"
        Map body = ["url": url, "token": newToken]

        artifactory.post("${MISSION_CONTROL_API}setupmc",
                [:], ContentType.JSON, null, ContentType.JSON, body, headers)
    }
}
