package org.jfrog.artifactory.client.model;

import java.util.List;

/**
 * @author yahavi
 */
public interface AllBuilds {
    String getUri();

    List<Build> getBuilds();
}
