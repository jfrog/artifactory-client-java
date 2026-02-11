package org.jfrog.artifactory.client.model.impl;

import org.jfrog.artifactory.client.model.AllBuilds;
import org.jfrog.artifactory.client.model.Build;
import tools.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

/**
 * @author yahavi
 */
public class AllBuildsImpl implements AllBuilds {
    private String uri;
    private List<Build> builds;

    @Override
    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public List<Build> getBuilds() {
        return builds;
    }

    @com.fasterxml.jackson.databind.annotation.JsonDeserialize(contentAs = BuildImpl.class)
    @JsonDeserialize(contentAs = BuildImpl.class)
    public void setBuilds(List<Build> builds) {
        this.builds = builds;
    }
}
