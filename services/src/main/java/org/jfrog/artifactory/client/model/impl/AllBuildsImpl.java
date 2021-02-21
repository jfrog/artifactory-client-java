package org.jfrog.artifactory.client.model.impl;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.jfrog.artifactory.client.model.AllBuilds;
import org.jfrog.artifactory.client.model.Build;

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

    @JsonDeserialize(contentAs = BuildImpl.class)
    public void setBuilds(List<Build> builds) {
        this.builds = builds;
    }
}
