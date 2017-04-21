package org.jfrog.artifactory.client;

import org.jfrog.artifactory.client.model.SystemInfo;
import org.jfrog.artifactory.client.model.Version;

/**
 * System Level Configuration.
 *
 * (Class can not be named System because of conflict with java.lang.System.)
 *
 * @author quidryan
 */
public interface ArtifactorySystem {

    boolean ping();

    String configuration();

    void configuration(String xml);

    Version version();

    SystemInfo info();

}
