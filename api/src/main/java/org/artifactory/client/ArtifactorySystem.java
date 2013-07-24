package org.artifactory.client;

import org.artifactory.client.model.User;
import org.artifactory.client.model.Version;
import org.artifactory.client.model.builder.SecurityBuilders;

import java.util.Collection;

/**
 * System Level Configuration.
 *
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
