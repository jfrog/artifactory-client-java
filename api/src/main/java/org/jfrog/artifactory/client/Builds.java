package org.jfrog.artifactory.client;

import org.jfrog.artifactory.client.model.AllBuilds;
import org.jfrog.artifactory.client.model.BuildRuns;

import java.io.IOException;

/**
 * @author yahavi
 */
public interface Builds {
    AllBuilds getAllBuilds() throws IOException;

    BuildRuns getBuildRuns(String buildName) throws IOException;
}
