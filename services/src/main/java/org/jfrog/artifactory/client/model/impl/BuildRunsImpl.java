package org.jfrog.artifactory.client.model.impl;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.jfrog.artifactory.client.model.BuildNumber;
import org.jfrog.artifactory.client.model.BuildRuns;

import java.util.List;

/**
 * @author yahavi
 */
public class BuildRunsImpl implements BuildRuns {
    private List<BuildNumber> buildsNumbers;
    private String uri;

    @Override
    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public List<BuildNumber> getBuildsNumbers() {
        return buildsNumbers;
    }

    @JsonDeserialize(contentAs = BuildNumberImpl.class)
    public void setBuildsNumbers(List<BuildNumber> buildsNumbers) {
        this.buildsNumbers = buildsNumbers;
    }

    @Override
    public String toString() {
        return "BuildRunsImpl{" +
                "buildsNumbers=" + buildsNumbers +
                ", uri='" + uri + '\'' +
                '}';
    }
}
