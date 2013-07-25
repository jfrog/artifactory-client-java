package org.jfrog.artifactory.client;

import org.jfrog.artifactory.client.model.Version;

/**
 * System Level Configuration.
 * <p/>
 * (Class can not be named System because of conflict with java.lang.System.)
 *
 * @author quidryan
 */
public interface ArtifactorySystem {

    final static String SYSTEM_API = "/api/system/";
    final static String SYSTEM_PING_API = SYSTEM_API + "ping";
    final static String SYSTEM_CONFIGURATION_API = SYSTEM_API + "configuration";
    final static String SYSTEM_VERSION_API = SYSTEM_API + "version";

    boolean ping();

    String configuration();

    void configuration(String xml);

    Version version();

}
