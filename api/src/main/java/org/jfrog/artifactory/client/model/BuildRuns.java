package org.jfrog.artifactory.client.model;

import java.util.List;

public interface BuildRuns {
    String getUri();

    List<BuildNumber> getBuildsNumbers();
}
