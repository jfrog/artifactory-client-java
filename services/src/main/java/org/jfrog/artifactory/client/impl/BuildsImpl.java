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

    public static final String BUILDS_API = "/api/build";

    private final Artifactory artifactory;

    public BuildsImpl(Artifactory artifactory) {
        this.artifactory = artifactory;
    }

    @Override
    public AllBuilds getAllBuilds() throws IOException {
        return artifactory.get(BUILDS_API, AllBuildsImpl.class, AllBuilds.class);
    }

    @Override
    public BuildRuns getBuildRuns(String buildName) throws IOException {
        return artifactory.get(BUILDS_API + "/" + buildName, BuildRunsImpl.class, BuildRuns.class);
    }
}
