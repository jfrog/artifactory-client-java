package org.jfrog.artifactory.client;

import org.jfrog.artifactory.client.model.Plugin;
import org.jfrog.artifactory.client.model.PluginType;

import java.util.List;
import java.util.Map;

/**
 * @author jbaruch
 * @since 11/12/12
 */
public interface Plugins {

    Map<PluginType, List<Plugin>> list();

    List<Plugin> list(PluginType type);

    PluginHandle execute(String name);
}
