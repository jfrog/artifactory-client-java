package org.artifactory.client.model.builder;

import org.artifactory.client.model.Plugin;

import java.util.List;
import java.util.Map;

/**
 * @author jbaruch
 * @since 11/12/12
 */
public interface PluginBuilder {
    PluginBuilder name(String name);

    PluginBuilder version(String version);

    PluginBuilder description(String description);

    PluginBuilder users(List<String> users);

    PluginBuilder groups(List<String> groups);

    PluginBuilder params(Map<String, String> params);

    Plugin build();
}
