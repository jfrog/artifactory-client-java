package org.jfrog.artifactory.client.model;

import java.util.List;

/**
 * @author yahavi
 */
public interface BuildRuns {
    String getUri();

    List<BuildNumber> getBuildsNumbers();
}
