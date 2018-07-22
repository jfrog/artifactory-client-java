package org.jfrog.artifactory.client.v2.impl;

import org.jfrog.artifactory.client.impl.ArtifactoryImpl;
import org.jfrog.artifactory.client.v2.Security;
import org.jfrog.artifactory.client.v2.V2;

public class V2Impl implements V2 {

    private String baseApiPath;

    private ArtifactoryImpl artifactory;

    public V2Impl(ArtifactoryImpl artifactory, String baseApiPath) {
        this.baseApiPath = baseApiPath;
        this.artifactory = artifactory;
    }

    @Override
    public Security security() {
        return new SecurityImpl(artifactory, baseApiPath);
    }

    @Override
    public String getV2Api() {
        return null;
    }
}
