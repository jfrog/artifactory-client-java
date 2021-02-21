package org.jfrog.artifactory.client.model.impl;

import org.jfrog.artifactory.client.model.BuildNumber;

/**
 * @author yahavi
 **/
public class BuildNumberImpl implements BuildNumber {
    private String started;
    private String uri;

    @Override
    public String getUri() {
        return uri;
    }

    @Override
    public String getStarted() {
        return started;
    }

    @Override
    public String toString() {
        return "BuildNumberImpl{" +
                "started='" + started + '\'' +
                ", uri='" + uri + '\'' +
                '}';
    }
}
