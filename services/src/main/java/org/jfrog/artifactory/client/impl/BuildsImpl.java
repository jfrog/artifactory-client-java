package org.jfrog.artifactory.client.impl;

import org.jfrog.artifactory.client.Artifactory;
import org.jfrog.artifactory.client.Builds;
import org.jfrog.artifactory.client.model.AllBuilds;
import org.jfrog.artifactory.client.model.BuildRuns;
import org.jfrog.artifactory.client.model.impl.AllBuildsImpl;
import org.jfrog.artifactory.client.model.impl.BuildRunsImpl;

import java.io.IOException;

/**
 * @author yahavi
 **/
public class BuildsImpl implements Builds {
    private final Artifactory artifactory;
    private final String baseApiPath;

    public BuildsImpl(Artifactory artifactory, String baseApiPath) {
        this.artifactory = artifactory;
        this.baseApiPath = baseApiPath;
    }

    @Override
    public AllBuilds getAllBuilds() throws IOException {
        return artifactory.get(getBuilderApi(), AllBuildsImpl.class, AllBuilds.class);
    }

    @Override
    public BuildRuns getBuildRuns(String buildName) throws IOException {
        return artifactory.get(getBuilderApi() + buildName, BuildRunsImpl.class, BuildRuns.class);
    }

    public String getBuilderApi() {
        return baseApiPath + "/build/";
    }
}
