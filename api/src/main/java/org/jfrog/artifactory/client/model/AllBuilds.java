package org.jfrog.artifactory.client.model;

import java.util.List;

public interface AllBuilds {
    String getUri();

    List<Build> getBuilds();
}
