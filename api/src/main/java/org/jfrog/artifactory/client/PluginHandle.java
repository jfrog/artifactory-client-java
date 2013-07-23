package org.jfrog.artifactory.client;

import java.util.List;
import java.util.Map;

/**
 * @author jbaruch
 * @since 11/12/12
 */
public interface PluginHandle {

    PluginHandle withParameter(String name, String... values);

    PluginHandle withParameters(Map<String, List<String>> parameters);

    String sync();

    String aSync();

}
