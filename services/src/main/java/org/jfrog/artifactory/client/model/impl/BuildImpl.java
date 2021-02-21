package org.jfrog.artifactory.client.model.impl;

import org.jfrog.artifactory.client.model.Build;

/**
 * @author yahavi
 **/
public class BuildImpl implements Build {
    private String lastStarted;
    private String uri;

    @Override
    public String getUri() {
        return uri;
    }

    @Override
    public String getLastStarted() {
        return lastStarted;
    }

    @Override
    public String toString() {
        return "BuildImpl{" +
                "lastStarted='" + lastStarted + '\'' +
                ", uri='" + uri + '\'' +
                '}';
    }
}
